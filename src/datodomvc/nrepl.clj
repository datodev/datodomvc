(ns datodomvc.nrepl
  (:require [clojure.tools.nrepl.server :refer (start-server)]
            [cider.nrepl]
            [datodomvc.config :as config]))

(defn init []
  (if-let [port (config/nrepl-port)]
    (do
      (println "Starting nrepl on port" port)
      (start-server :port port :handler cider.nrepl/cider-nrepl-handler))
    (println "Not starting nrepl, export NREPL_PORT to start an embedded nrepl server")))
