(defproject datodomvc "0.1.0-SNAPSHOT"
  ;; See profiles.clj for profiles
  :description "FIXME: write description"
  :url ""

  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :repositories {"my.datomic.com" {:url   "https://my.datomic.com/repo"
                                   :creds :gpg}}
  :plugins [[lein-cljsbuild "1.0.6" :exclusions [org.clojure/clojurescript]]
            [lein-environ "1.0.0"]
            [lein-less "1.7.5"]
            [lein-figwheel "0.3.9"]]

  :less {:source-paths ["src/main/less"]
         :target-path "target/public/css"}

  :exclusions [[org.clojure/clojure]
               [org.clojure/clojurescript]
               org.clojure/clojurescript org.clojure/clojure]

  :dependencies [[bidi "1.21.0" :exclusions [ring/ring-core]]
                 ;; Chrome extension for cljs dev
                 [binaryage/devtools "0.3.0"]
                 [bk/ring-gzip "0.1.1"]
                 [cheshire "5.5.0" :exclusions [org.clojure/clojure org.clojure/clojurescript com.fasterxml.jackson.core/jackson-core]]
                 [cljsjs/codemirror "5.6.0-0"]
                 [cljsjs/react "0.12.2-8"]
                 [clojurewerkz/scrypt "1.2.0"]
                 [com.cognitect/transit-clj "0.8.275" :exclusions [com.fasterxml.jackson.core/jackson-core]]
                 [com.cognitect/transit-cljs "0.8.225" :exclusions [org.clojure/clojurescript]]
                 [com.datomic/datomic-pro "0.9.5206" :exclusions [org.slf4j/slf4j-nop joda-time org.slf4j/slf4j-api org.clojure/clojurescript]]
                 [com.fasterxml.jackson.core/jackson-annotations "2.3.1"]
                 [com.fasterxml.jackson.core/jackson-core "2.3.1"]
                 [compojure "1.3.1"]
                 ;;[datascript "0.11.6"]
                 [environ "1.0.0"]
                 [garden "1.3.0-SNAPSHOT"]
                 [hiccup "1.0.5"]
                 [instaparse "1.4.0"]
                 [javax.servlet/servlet-api "2.5"]
                 [kibu/pushy "0.3.3"]
                 [org.bouncycastle/bcmail-jdk15on "1.52"]
                 [org.bouncycastle/bcprov-jdk15on "1.52"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122" :exclusions [org.clojure/clojure org.clojure/tools.reader org.clojure/clojurescript
                                                                   org.clojure/google-closure-library-third-party]]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/data.json "0.2.6" :classifier "aot"]
                 [org.clojure/google-closure-library "0.0-20150805-acd8b553" :exclusions [org.clojure/google-closure-library-third-party org.clojure/clojure org.clojure/clojurescript]]
                 [org.clojure/google-closure-library-third-party "0.0-20150505-021ed5b3"]
                 [org.clojure/test.check "0.7.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.clojure/tools.reader "0.10.0-alpha3"]
                 [org.eclipse.jetty/jetty-util "9.2.12.v20150709"]
                 [org.immutant/immutant "2.0.1" :exclusions
                  [org.hornetq/hornetq-native org.jboss.logging/jboss-logging org.jgroups/jgroups org.clojure/clojure org.hornetq/hornetq-commons org.clojure/tools.reader org.hornetq/hornetq-server org.slf4j/slf4j-api org.hornetq/hornetq-core-client org.clojure/clojurescript org.hornetq/hornetq-journal]]
                 [org.immutant/immutant-transit "0.2.2" :exclusions [org.clojure/clojure org.clojure/clojurescript com.fasterxml.jackson.core/jackson-core org.clojure/clojure org.clojure/tools.reader org.slf4j/slf4j-api org.clojure/clojurescript org.hornetq/hornetq-server]]
                 [org.omcljs/om "0.8.8"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [org.slf4j/slf4j-api "1.6.2"]
                 [org.slf4j/slf4j-log4j12 "1.6.2" :exclusions [log4j/log4j]]
                 [precursor/om-i "0.1.7"]
                 [prismatic/om-tools "0.3.11" :exclusions [om]]
                 [prismatic/plumbing "0.4.4"]
                 [prismatic/schema "0.4.4" :exclusions [prismatic/plumbing]]
                 [racehub/om-bootstrap "0.5.1"]
                 [ring "1.4.0" :exclusions [org.clojure/clojure commons-fileupload org.clojure/clojurescript]]
                 [ring-basic-authentication "1.0.5"]
                 [ring/ring-defaults "0.1.5"]
                 [sablono "0.3.4"]
                 [talaria "0.1.3"]]

  :cljsbuild {:test-commands {"test" ["phantomjs" "env/test/js/unit-test.js" "env/test/unit-test.html"]}
              :builds        [{:id           "dev"
                               :source-paths ["env/dev/cljs/"
                                              "src/cljs" "src/shared/"
                                              "src/shared/"
                                              "vendor/datascript/src" "vendor/datascript/bench/src"
                                              "vendor/dato/src/cljs" "vendor/dato/src/shared"
                                              "vendor/garden/src"]
                               :figwheel     true
                               :compiler     {:asset-path    "/js/bin-debug"
                                              :main          datodomvc.client.dev
                                              :output-to     "resources/public/js/bin-debug/main.js"
                                              :output-dir    "resources/public/js/bin-debug/"
                                              :optimizations :none
                                              :pretty-print  true
                                              :preamble      ["react/react.js"]
                                              :externs       ["react/externs/react.js"]
                                              :source-map    true
                                              :verbose       true
                                              :warnings      {:single-segment-namespace false}}}
                              {:id           "pseudo"
                               :source-paths ["src/cljs" "src/shared/"
                                              "vendor/datascript/src" "vendor/datascript/bench/src"
                                              "vendor/dato/src/cljs" "vendor/dato/src/shared"
                                              "vendor/garden/src"]
                               :compiler     {:asset-path    "/js/bin-pseudo"
                                              :main          datodomvc.client.production
                                              :output-to     "resources/public/js/bin-pseudo/main.js"
                                              :output-dir    "resources/public/js/bin-pseudo/"
                                              :optimizations :advanced
                                              :pseudo-names  true
                                              :pretty-print  true
                                              :preamble      ["react/react.js"]
                                              :externs       ["react/externs/react.js"]
                                              :source-map    "resources/public/js/bin-pseudo/main.src"
                                              :verbose       true
                                              :warnings      {:single-segment-namespace false}}}
                              {:id           "test"
                               :source-paths ["env/production/cljs"
                                              "src/cljs" "test/cljs"
                                              "vendor/datascript/src" "vendor/datascript/bench/src"
                                              "vendor/dato/src/cljs" "vendor/dato/src/shared"
                                              "vendor/garden/src"]
                               :compiler     {:pretty-print  true
                                              :output-to     "resources/public/cljs/test/frontend-dev.js"
                                              :output-dir    "resources/public/cljs/test"
                                              :optimizations :advanced
                                              :externs       ["datascript/externs.js"]
                                              :source-map    "resources/public/cljs/test/sourcemap-dev.js"
                                              :warnings      {:single-segment-namespace false}
                                              :verbose       true}}
                              {:id           "production"
                               :source-paths ["env/production/cljs"
                                              "src/cljs" "src/shared/"
                                              "vendor/datascript/src" "vendor/datascript/bench/src"
                                              "vendor/dato/src/cljs" "vendor/dato/src/shared"
                                              "vendor/garden/src"]
                               :compiler     {:asset-path    "/js/bin"
                                              :main          datodomvc.client.production
                                              :output-to     "resources/public/js/bin/main.js"
                                              :output-dir    "resources/public/js/bin/"
                                              :optimizations :advanced
                                              :pretty-print  false
                                              :preamble      ["react/react.js"]
                                              :externs       ["react/externs/react.js"]
                                              :warnings      {:single-segment-namespace false}
                                              :verbose       true}}]}

  :figwheel {:http-server-root  "public"
             :server-port       3449
             :server-ip         "0.0.0.0"
             :css-dirs          ["resources/public/css"]
             :open-file-command "emacsclient"}

  :main ^:skip-aot datodomvc.init

  :source-paths ["src/" "src/shared/"
                 "vendor/datascript/src" "vendor/datascript/bench/src"
                 "vendor/dato/src"       "vendor/dato/src/shared"
                 "vendor/dato/src/cljs"
                 "vendor/garden/src"]

  :target-path "target/%s"

  :resource-paths ["resources" "resources/public"]

  :jvm-opts ["-server"
             "-Xss1m"
             "-Xmx4g"])
