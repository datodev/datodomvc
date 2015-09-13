(ns datodomvc.client.utils
  (:require [clojure.string :as string]
            [goog.dom.DomHelper :as gdomh]
            [goog.Uri])
  (:import [goog.Uri]))

(defn map-vals
  "Create a new map from m by calling function f on each value to get a new value."
  [m f & args]
  (when m
    (into {}
          (for [[k v] m]
            [k (apply f v args)]))))

(defn map-keys
  "Create a new map from m by calling function f on each key to get a new key."
  [m f & args]
  (when m
    (into {}
          (for [[k v] m]
            [(apply f k args) v]))))

(def parsed-uri
  (goog.Uri. (-> (.-location js/window) (.-href))))

(defn uri-param [parsed-uri param-name & [not-found]]
  (let [v (.getParameterValue parsed-uri param-name)]
    (cond
      (= v "")                          [(keyword param-name) not-found]
      (undefined? v)                    [(keyword param-name) not-found]
      (= v "true")                      [(keyword (str param-name "?")) true]
      (= v "false")                     [(keyword (str param-name "?")) false]
      (= (.toString (js/parseInt v)) v) [(keyword param-name) (js/parseInt v)]
      (re-matches #"^\d+\.\d*" v)       [(keyword param-name) (js/parseFloat v)]
      :else                             [(keyword param-name) v])))

(def initial-query-map
  (let [parsed-uri (goog.Uri. (.. js/window -location -href))
        ks         (.. parsed-uri getQueryData getKeys)
        defaults   {}
        initial    (reduce merge {} (map (partial uri-param parsed-uri) (clj->js ks)))
        ;; Use this if you need to do any fn-based changes, e.g. split on a uri param
        special    {}]
    (merge defaults initial special)))

(defn log [& msg]
  (.apply (.-log js/console) js/console (clj->js msg)))

(defn sel1
  ([query]
   (sel1 js/document query))
  ([node query]
   (.querySelector node (name query))))

(defn destroy-sel!
  ([selector]
   (destroy-sel! js/document selector))
  ([node selector]
   (gdomh/removeNode (sel1 node selector))))

(defn map->query-params [m]
  (reduce (fn [run [k v]]
            (let [lead (if run "&" "")]
              (str run lead (js/encodeURIComponent (name k)) "=" (js/encodeURIComponent v)))) nil
              m))
