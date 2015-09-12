(ns datodomvc.client.routes
  (:require [bidi.bidi :as bidi]
            [clojure.string :as string]
            [com.cognitect.transit.types :as ty]
            [dato.lib.core :as dato]
            [dato.lib.db :as db]
            [goog.string :as gstring]
            [dato.db.utils :as dsu]
            [pushy.core :as pushy]))

(def routes ["/" {""          :tasks/all
                  "active"    :tasks/active
                  "completed" :tasks/completed}])

(defn parse-url [full-url]
  (let [[url maybe-query-string] (string/split full-url "?")]
    (bidi/match-route routes url)))

(defn make-route-dispatcher [dato]
  (let [cast! (get-in dato [:api :cast!])]
    (fn [matched-route]
      (let [dato-event {:event :ui/item-inspected
                        :data  (merge {:type (:handler matched-route)})}]
        (cast! dato-event)))))

(defn url-for
  ([action]
   (url-for action {}))
  ([action args]
   ;; Fix https://github.com/juxt/bidi/issues/79 here so the interface stays the same
   (let [args (->> args
                   (mapcat (fn [[k v]]
                             [k (cond
                                  (com.cognitect.transit.types/isUUID v)      (cljs.core.UUID. (str v))
                                  (instance? datascript.impl.entity/Entity v) (dsu/touch+ v)
                                  :else                                       v)]))
                   vec)]
     (apply bidi/path-for routes action args))))

(defn navigate-to! [router action params]
  (let [route (url-for action params)]
    (pushy/set-token! router route)))

(defn make-router [dato]
  (pushy/pushy (make-route-dispatcher dato) parse-url))

(defn start! [router]
  (pushy/start! router))
