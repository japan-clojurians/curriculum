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

(defhandler todo-show :todo-show [req]
  (let [id (Long/parseLong (get-in req [:params :id]))
        todo (first (model/find-todo [:= :id id]))]
    (-> (view/todo-show req todo)
        res/ok
        res/html)))
