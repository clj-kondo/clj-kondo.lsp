#!/usr/bin/env bb

(require '[clojure.string :as str])

(def version (first *command-line-args*))
(assert version "Must provide version")

(defn normalize-version [version]
  (str/join "." (map #(str/replace % #"^0*" "")
                     (str/split version #"\."))))

(spit "vscode-extension/package.json"
      (str/replace (slurp "vscode-extension/package.template.json")
                   "{{version}}"
                   (normalize-version version)))
