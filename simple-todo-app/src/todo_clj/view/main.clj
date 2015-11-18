(ns todo-clj.view.main
  (:require [todo-clj.routing :as r]
            [todo-clj.view.layout :as layout]))

(defn home [req]
  (->> (list
        [:h1 "Welcome to Clojure!!"]
        [:ul
         [:li [:a {:href (r/href :todo-index)} "TODO 一覧"]]])
       (layout/main-layout req)))
