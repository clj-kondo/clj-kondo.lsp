(ns clj-kondo-lsp.release-artifact
  (:require [borkdude.gh-release-artifact :as ghr]
            [clojure.java.shell :refer [sh]]
            [clojure.string :as str]))

(defn current-branch []
  (or (System/getenv "APPVEYOR_PULL_REQUEST_HEAD_REPO_BRANCH")
      (System/getenv "APPVEYOR_REPO_BRANCH")
      (System/getenv "CIRCLE_BRANCH")
      (-> (sh "git" "rev-parse" "--abbrev-ref" "HEAD")
          :out
          str/trim)))

(defn release [clj-kondo-version file]
  (let [ght (System/getenv "GITHUB_TOKEN")
        branch (current-branch)
        release-branch? (contains? #{"master" "main"} branch)]
    (when-not ght
      (println "Skipping: not GITHUB_TOKEN"))
    (when-not release-branch?
      (println "Skipping: not on release branch"))
    (if (and ght release-branch?)
      (do (assert file "File name must be provided")
          (println "Uploading" file "to release" clj-kondo-version)
          (ghr/overwrite-asset {:org "clj-kondo"
                                :repo "clj-kondo"
                                :file file
                                :tag (str "v" clj-kondo-version)
                                :sha256 true}))
      (println "Skipping release artifact (no GITHUB_TOKEN or not on main branch)"))
    nil))
