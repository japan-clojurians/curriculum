(ns todo-clj.util.response
  (:require [potemkin :as p]
            [ring.util.http-response]
            [ring.util.response :as res]))

(p/import-vars
 [ring.util.http-response
  ok
  found
  not-found!
  internal-server-error!])

(defn html [res]
  (res/content-type res "text/html; charset=UTF-8"))
