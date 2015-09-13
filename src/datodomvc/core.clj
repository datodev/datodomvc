(ns datodomvc.core
  (:require [datodomvc.server :as server])
  (:gen-class))

(defn -main
  [& port]
  (server/run port))
