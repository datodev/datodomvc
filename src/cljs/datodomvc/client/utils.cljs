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

(def initial-query-map
  {})

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
