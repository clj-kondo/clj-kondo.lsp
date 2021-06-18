;; GENERATED by script/update-project.clj, DO NOT EDIT
;; To change dependencies, update deps.edn and run script/update-project.clj.
;; To change other things, edit project.template.clj and run script/update-project.clj.

(defproject clj-kondo/lsp-server "2021.06.18"
  :description "Language server for clj-kondo."
  :url "https://github.com/borkdude/clj-kondo.lsp"
  :scm {:name "git"
        :url "https://github.com/borkdude/clj-kondo.lsp"}
  :license {:name "EPL-1.0"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-kondo "2021.06.18"]
                 [org.eclipse.lsp4j/org.eclipse.lsp4j "0.8.1"]]
  ;; Oldest version JVM to support.
  ;; We don't compile Java classes in this project, but adding this just in case.
  :javac-options ["--release" "8"]
  :main clj-kondo.lsp-server.main
  :profiles {:uberjar {:aot :all
                       :global-vars {*assert* false}
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"
                                  "-Dclojure.spec.skip-macros=true"]}}
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass
                                    :sign-releases false}]])
