(defproject clj-kondo/lsp-server "{{version}}"
  :description "Language server for clj-kondo."
  :url "https://github.com/borkdude/clj-kondo.lsp"
  :scm {:name "git"
        :url "https://github.com/borkdude/clj-kondo.lsp"}
  :license {:name "EPL-1.0"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [clj-kondo "{{version}}"]
                 [org.eclipse.lsp4j/org.eclipse.lsp4j "0.8.1"]]
  ;; Oldest version JVM to support.
  :javac-options ["--release" "8" "-9"]
  :main clj-kondo.lsp-server.main
  :profiles {:uberjar {:aot :all
                       :global-vars {*assert* false}
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"
                                  "-Dclojure.spec.skip-macros=true"]}}
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass
                                    :sign-releases false}]])
