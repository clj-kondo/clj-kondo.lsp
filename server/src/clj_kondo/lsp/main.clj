(ns clj-kondo.lsp.main
  {:no-doc true}
  (:gen-class)
  (:require
   [clj-kondo.lsp.impl.server :as server]))

(set! *warn-on-reflection* true)

;;;; parse command line options

(defn -main [& options]
  (server/run-server!))

;;;; Scratch

(comment
  )
