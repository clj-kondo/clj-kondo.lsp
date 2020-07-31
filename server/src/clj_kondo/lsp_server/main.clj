(ns clj-kondo.lsp-server.main
  {:no-doc true}
  (:gen-class)
  (:require
   [clj-kondo.lsp-server.impl.server :as server]))

(set! *warn-on-reflection* true)

;;;; parse command line options

(defn -main [& options]
  (server/run-server! options))

;;;; Scratch

(comment
  )
