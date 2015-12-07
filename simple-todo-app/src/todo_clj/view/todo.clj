(ns todo-clj.view.todo
  (:require [todo-clj.routing :as r]
            [todo-clj.view.layout :as layout]))

(defn todo-index [req todo-list]
  (->> `([:h1 "TODO 一覧"]
         [:ul
          ~@(for [{:as t :keys [id title]} todo-list]
              [:li [:a {:href (r/href :todo-show {:id (str id)})} title]])])
       (layout/main-layout req)))

(defn todo-show [req todo]
  (->> [:h1 (:title todo)]
       (layout/main-layout req)))
