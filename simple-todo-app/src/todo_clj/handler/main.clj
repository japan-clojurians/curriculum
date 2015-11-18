(ns todo-clj.handler.main
  (:require [todo-clj.util.handler :refer [defhandler]]
            [todo-clj.util.response :as res]
            [todo-clj.view.main :as view]))

(defhandler home :home [req]
  (-> (view/home req)
      res/ok
      res/html))

(defhandler not-found :not-found [req]
  (res/not-found! "<h1>Not Found</h1>"))
