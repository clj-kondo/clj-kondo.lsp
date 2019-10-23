(ns clj-kondo.lsp.impl.server
  {:no-doc true}
  (:import [org.eclipse.lsp4j.services LanguageServer TextDocumentService WorkspaceService LanguageClient]
           [org.eclipse.lsp4j
            Diagnostic
            DiagnosticSeverity
            DidChangeConfigurationParams
            DidChangeTextDocumentParams
            DidChangeWatchedFilesParams
            DidCloseTextDocumentParams
            DidOpenTextDocumentParams
            DidSaveTextDocumentParams
            ExecuteCommandParams
            InitializeParams
            InitializeResult
            InitializedParams
            MessageParams
            MessageType
            Position
            PublishDiagnosticsParams
            Range
            ServerCapabilities
            TextDocumentIdentifier
            TextDocumentContentChangeEvent
            TextDocumentPositionParams
            TextDocumentSyncKind
            TextDocumentSyncOptions]
           [org.eclipse.lsp4j.launch LSPLauncher]
           [java.util.concurrent CompletableFuture])
  (:require [clojure.string :as str]
            [clj-kondo.core :as clj-kondo]))

(set! *warn-on-reflection* true)

(defonce proxy-state (atom nil))
(defonce exit-state (promise))

(defn log! [level & msg]
  (when-let [client @proxy-state]
    (let [msg (str/join " " msg)]
      (.logMessage ^LanguageClient client
                   (MessageParams. (case level
                                     :error MessageType/Error
                                     :warning MessageType/Warning
                                     :info MessageType/Info
                                     :debug MessageType/Log
                                     MessageType/Log) msg)))))

(defn error [& msgs]
  (apply log! :error msgs))

(defn warn [& msgs]
  (apply log! :warn msgs))

(defn info [& msgs]
  (apply log! :info msgs))

(defn debug [& msgs]
  (apply log! :debug msgs))

(defmacro do! [& body]
  `(try ~@body
        (catch Throwable e#
          (let [sw# (java.io.StringWriter.)
                pw# (java.io.PrintWriter. sw#)
                _# (.printStackTrace e# pw#)
                err# (str pw#)]
            (error err#)))))

(defn finding->Diagnostic [{:keys [:row :col :message :level]}]
  (let [row (max 0 (dec row))
        col (max 0 (dec col))]
    (Diagnostic. (Range. (Position. row col)
                         (Position. row col))
                 message
                 (case level
                   :info DiagnosticSeverity/Information
                   :warning DiagnosticSeverity/Warning
                   :error DiagnosticSeverity/Error)
                 "clj-kondo")))

(defn uri->lang [uri]
  (when-let [dot-idx (str/last-index-of uri ".")]
    (let [ext (subs uri (inc dot-idx))
          lang (keyword ext)]
      lang)))

(defn lint! [text uri]
  (let [lang (uri->lang uri)
        {:keys [:findings]} (with-in-str text (clj-kondo/run! {:lint ["-"]
                                                               :lang lang}))]
    (.publishDiagnostics ^LanguageClient @proxy-state
                         (PublishDiagnosticsParams.
                          uri
                          (map finding->Diagnostic findings)))))

(deftype LSPTextDocumentService []
  TextDocumentService
  (^void didOpen [_ ^DidOpenTextDocumentParams params]
   (do! (let [td (.getTextDocument params)
              text (.getText td)
              uri (.getUri td)]
          (lint! text uri))))

  (^void didChange [_ ^DidChangeTextDocumentParams params]
   (do! (let [td ^TextDocumentIdentifier (.getTextDocument params)
              changes (.getContentChanges params)
              change (first changes)
              text (.getText ^TextDocumentContentChangeEvent change)
              uri (.getUri td)]
          (lint! text uri))))

  (^void didSave [_ ^DidSaveTextDocumentParams _params])

  (^void didClose [_ ^DidCloseTextDocumentParams params]))

(deftype LSPWorkspaceService []
  WorkspaceService
  (^CompletableFuture executeCommand [_ ^ExecuteCommandParams params])
  (^void didChangeConfiguration [_ ^DidChangeConfigurationParams params])
  (^void didChangeWatchedFiles [_ ^DidChangeWatchedFilesParams _params]))

(def server
  (proxy [LanguageServer] []
    (^CompletableFuture initialize [^InitializeParams params]
     (CompletableFuture/completedFuture
      (InitializeResult. (doto (ServerCapabilities.)
                           (.setTextDocumentSync (doto (TextDocumentSyncOptions.)
                                                   (.setOpenClose true)
                                                   (.setChange TextDocumentSyncKind/Full)))))))
    (^CompletableFuture initialized [^InitializedParams params]
     (info "Clj-kondo language server loaded. Please report any issues to https://github.com/borkdude/clj-kondo."))
    (^CompletableFuture shutdown []
     (CompletableFuture/completedFuture @exit-state))

    (^void exit []
     (deliver exit-state 0))

    (getTextDocumentService []
      (LSPTextDocumentService.))

    (getWorkspaceService []
      (LSPWorkspaceService.))))

(defn run-server! []
  (let [launcher (LSPLauncher/createServerLauncher server System/in System/out)
        proxy ^LanguageClient (.getRemoteProxy launcher)]
    (.startListening launcher)
    (reset! proxy-state proxy)
    @exit-state))
