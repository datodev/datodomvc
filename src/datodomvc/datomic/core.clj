(ns datodomvc.datomic.core
  (:require [clojure.core.async :as async]
            [clojure.tools.logging :refer (infof)]
            [clojure.walk :as walk]
            [datomic.api :refer [q] :as d]
            [datodomvc.config :as config]
            [dato.db.utils :as dsu])
  (:import java.util.UUID))


(def remote-uri (config/datomic-uri))

(def local-uri (config/datomic-uri))

(def default-uri
  (if (config/dev?)
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
