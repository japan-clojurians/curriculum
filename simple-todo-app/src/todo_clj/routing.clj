(ns todo-clj.routing
  (:require [bidi.bidi :as bidi]
            [todo-clj.routes :as routes]))

(defn href [route-id]
  (bidi/path-for routes/main route-id))
