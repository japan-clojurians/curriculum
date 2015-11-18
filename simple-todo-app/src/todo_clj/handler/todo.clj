(ns todo-clj.handler.todo
  (:require [todo-clj.model.todo :as model]
            [todo-clj.util.handler :refer [defhandler]]
            [todo-clj.util.response :as res]
            [todo-clj.view.todo :as view]))

(defhandler todo-index :todo-index [req]
  (let [todo-list (model/find-todo)]
    (-> (view/todo-index req todo-list)
        res/ok
        res/html)))
