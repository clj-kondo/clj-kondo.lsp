(ns clj-kondo.lsp-server.impl.version
  {:no-doc true}
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(defn -main [& args]
  (let [version (str/trim (slurp (io/resource "CLJ_KONDO_VERSION")))
        out-file (io/file ".." "vscode-extension" "CLJ_KONDO_VERSION")]
    (println "Generation version file:" version ">" (.getPath out-file))
    (io/copy version
             out-file)))
