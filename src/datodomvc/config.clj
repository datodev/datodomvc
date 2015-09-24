(ns datodomvc.config
  (:require [environ.core :as env]))

(defn datomic-uri []
  (or (env/env :datomic-uri)
      "datomic:sql://datodomvc?jdbc:postgresql://my-remote-server:5432/datodomvc?user=datomic&password=datomic"))

(defn dev? []
  (env/env :is-dev))

(defn nrepl-port []
  (when-let [port (env/env :nrepl-port)]
    (Integer/parseInt port)))

(defn dato-port []
  (if-let [port (env/env :dato-port)]
    (Integer/parseInt port)
    8080))

(defn server-port []
  (if-let [port (env/env :server-port)]
    (Integer/parseInt port)
    10555))
