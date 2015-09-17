(ns datodomvc.client.config)

(defn config []
  (aget js/window "DatodoMVC" "config"))

(defn dato-port []
  (aget (config) "dato-port"))
