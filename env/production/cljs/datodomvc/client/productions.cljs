(ns datodomvc.client.productions
  (:require [datodomvc.client.core :as datodomvc-core]
            [datodomvc.client.utils :as utils]))

(js/console.log "Loading DatodoMVC via production..." (pr-str utils/initial-query-map))

(defn -main []
  (datodomvc-core/-main (js/document.getElementById "datodomvc-app") datodomvc-core/app-state))

(-main)
