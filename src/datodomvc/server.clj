(ns datodomvc.server
  (:require [bidi.bidi :as bidi]
            [cheshire.core :as json]
            [clojure.java.io :as io]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [datomic.api :as d]
            [dato.db.utils :as dsu]
            [dato.lib.debug :as dato-debug]
            [dato.lib.incoming :as dato-in]
            [dato.lib.server :as dato]
            [datodomvc.datomic.core :as db-conn]
            [environ.core :as config]
            [hiccup.core :as h]
            [immutant.codecs :as cdc]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.basic-authentication :as basic-auth]
            [ring.middleware.defaults :as ring-defaults]
            [ring.middleware.gzip :as gzip]
            [ring.middleware.multipart-params :as multipart]
            [ring.middleware.reload :as reload]
            [ring.util.response :as resp])
  (:import [java.net URLEncoder]
           [java.util UUID]))

(def is-dev?
  (config/env :is-dev))

(defn build-name->entry-file [build-name]
  (case build-name
    "pseudo"     "/js/bin-pseudo/main.js"
    "production" "/js/bin/main.js"
    "dev"        "/js/bin-debug/main.js"
    (if is-dev?
      "/js/bin-debug/main.js"
      "/js/bin/main.js")))

(defn bootstrap-html [params]
  ;; TODO: Add some authorization here to make sure user is allowed to
  ;; access different version of the js.
  (let [header [:head
                [:link {:href "/css/vendor/bootstrap/css/bootstrap.min.css" :rel "stylesheet" :type "text/css"}]
                [:link {:href "/css/vendor/bootstrap/css/bootstrap-theme.min.css" :rel "stylesheet" :type "text/css"}]
                [:link {:href "/css/style.css" :rel "stylesheet" :type "text/css"}]
                [:link {:href "/css/base.css" :rel "stylesheet" :type "text/css"}]
                [:link {:href "/css/index.css" :rel "stylesheet" :type "text/css"}]
                [:title "Dato • TodoMVC"]]]
    (h/html [:html
             header
             [:body
              [:div#datodomvc-app
               [:input.history {:style "display:none;"}]
               [:div.app-instance "Please wait while the app loads..."]
               [:div.debugger-container]]
              [:script {:src (build-name->entry-file (:build-name params))}]]])))

(defroutes routes
  (GET "/_source" request
    (let [path    (get-in request [:params :path])
          macros? (get-in request [:params :macros])]
      {:body (json/generate-string (dato-debug/source-path->source macros? path))}))
  (route/resources "/")
  (GET "/*" request
    (bootstrap-html (:params request))))

(defn authenticated? [email pass]
  false)

(def http-handler
  (as-> (var routes) routes
    (ring-defaults/wrap-defaults routes ring-defaults/api-defaults)
    (multipart/wrap-multipart-params routes)
    (gzip/wrap-gzip routes)
    (if is-dev?
      (reload/wrap-reload routes)
      (basic-auth/wrap-basic-authentication routes authenticated?))))

(defn run-web-server [& [port]]
  (let [port (Integer. (or port (config/env :port) 10555))]
    (print "Starting web server on port" port ".\n")
    (jetty/run-jetty http-handler {:port port :join? false})))

(defn handler [{c :context}]
  (resp/redirect (str c "/index.html")))

(def routing-table
  {})

(def dato-routes
  (dato/new-routing-table routing-table))

(def dato-server
  (dato/map->DatoServer {:routing-table #'dato-routes
                         :datomic-uri   db-conn/default-uri}))

(defn run [& [port]]
  (dato/start! handler {:server (var dato-server)
                        :port   8080})
  (run-web-server port))

(defn -main [& [port]]
  (run port))

(comment
  (do
    (require '[datodomvc.dev :as dev])
    (datodomvc.dev/browser-repl)))