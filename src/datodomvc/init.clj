(ns datodomvc.init
  (:require [datodomvc.datomic.core]
            [datodomvc.datomic.migrations]
            [datodomvc.datomic.schema]
            [datodomvc.nrepl]
            [datodomvc.server])
  (:import [java.util Date]))

(defn init []
  (datodomvc.nrepl/init)
  (datodomvc.datomic.core/init)
  (datodomvc.datomic.schema/init)
  (datodomvc.datomic.migrations/init)
  (datodomvc.server/init))

(defn -main []
  (println "Initializing dato at" (Date.))
  (init)
  (println "Finished initializing dato at" (Date.)))
