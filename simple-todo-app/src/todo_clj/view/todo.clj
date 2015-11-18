(ns todo-clj.view.todo
  (:require [todo-clj.view.layout :as layout]))

(defn todo-index [req todo-list]
  (->> `([:h1 "TODO 一覧"]
         [:ul
          ~@(for [{:as t :keys [title]} todo-list]
              [:li title])])
       (layout/main-layout req)))
