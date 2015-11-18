(ns todo-clj.view.layout
  (:require [hiccup.page :refer [html5]]))

(defn main-layout [req & body]
  (html5
   [:head
    [:title "Simple TODO app"]]
   [:body
    body]))
