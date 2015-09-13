(ns datodomvc.datomic.core
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :refer (infof)]
            [clojure.walk :as walk]
            [datomic.api :refer [q] :as d]
            [environ.core :as config]
            [dato.db.utils :as dsu])
  (:import java.util.UUID))


(def remote-uri
  (or (config/env :datomic-uri)
      "datomic:sql://datodomvc?jdbc:postgresql://my-remote-server:5432/datodomvc?user=datomic&password=datomic"))

(def local-uri
  (or (config/env :datomic-uri)
      "datomic:sql://datodomvc_dev?jdbc:postgresql://127.0.0.1:5432/datodomvc_dev?user=datomic&password=datomic"))

(def default-uri
  (if (config/env :is-dev)
    local-uri
    remote-uri))

(defn make-conn [& [options]]
  (d/connect (or (:uri options) default-uri)))

(defn conn []
  (make-conn))

(defn ddb []
  (d/db (conn)))

(defn init []
  (infof "Creating default database if it doesn't exist: %s"
         (d/create-database default-uri))
  (infof "Ensuring connection to default database")
  (infof "Connected to: %s" (conn)))
