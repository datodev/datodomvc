(ns datodomvc.client.debug
  (:require [datascript :as d]
            [dato.lib.core :as dato]
            [dato.db.utils :as dsu]))

(def watched-expressions
  [{:title "Local session"
    :fn (fn [db] (let [local-session-ptrs (dsu/qes-by db :local/current-session)
                      sessions           (map (comp dsu/t :local/current-session) local-session-ptrs)]
                  {:local-session-ptrs local-session-ptrs
                   :sessions           sessions
                   :dato-session       (when-let [s (dato/local-session db)]
                                         (dsu/t s))}))}])
