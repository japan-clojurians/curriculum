(ns todo-clj.util.namespace
  (:require [clojure.tools.namespace :as tn]))

(defn- find-namespaces-by-prefixes [prefixes]
  (let [namespaces (map name (tn/find-namespaces-on-classpath))]
    (->> prefixes
         (map #(re-pattern (str "^" %)))
         (mapcat (fn [re] (filter #(re-find re %) namespaces)))
         (map symbol))))

(defn pre-require
  "プレフィックスが一致する全てのネームスペースを `require` する"
  [& prefixes]
  (let [namespaces (find-namespaces-by-prefixes prefixes)]
    (doseq [ns namespaces]
      (require ns))))
