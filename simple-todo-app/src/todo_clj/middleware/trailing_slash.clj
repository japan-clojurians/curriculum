(ns todo-clj.middleware.trailing-slash
  (:require [clojure.string :as str]))

(defn remove-trailing-slash [s]
  (str/replace s #"(?<=.)/$" ""))

(defn wrap-trailing-slash [handler]
  (fn [req]
    (-> req
        (update :path-info remove-trailing-slash)
        (update :uri remove-trailing-slash)
        handler)))
