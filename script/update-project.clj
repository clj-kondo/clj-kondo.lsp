#!/usr/bin/env bb

(require '[clojure.java.io :as io])
(require '[clojure.string :as str])

(def version (first *command-line-args*))
(def new-content
  (str ";; GENERATED by script/update-project.clj, DO NOT EDIT\n"
       ";; To change dependencies, update deps.edn and run script/update-project.clj.\n"
       ";; To change other things, edit project.template.clj and run script/update-project.clj.\n"
       "\n"
       (str/replace (slurp (io/file "server/project.template.clj"))
                    "{{version}}" version)))
(def old-content (slurp "server/project.clj"))

(when-not (= old-content new-content)
  (spit "server/project.clj"
        new-content))
