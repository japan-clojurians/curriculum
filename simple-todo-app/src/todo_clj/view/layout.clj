(ns todo-clj.view.layout
  (:require [hiccup.page :refer [html5 include-js include-css]]))

(defn main-layout [req & body]
  (html5
   [:head
    [:title "Simple TODO app"]
    (include-css "/css/style.css")
    (include-js "/js/main.js")]
   [:body
    body]))
