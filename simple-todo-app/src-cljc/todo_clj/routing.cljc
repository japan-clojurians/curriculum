(ns todo-clj.routing
  (:require [bidi.bidi :as bidi]
            [todo-clj.routes :as routes]))

(defn href
  ([route-id]
   (href route-id {}))
  ([route-id params-map]
   (apply bidi/path-for routes/main route-id
          (apply concat params-map))))
