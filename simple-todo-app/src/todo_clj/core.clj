(ns todo-clj.core
  (:require [bidi.ring :as br]
            [todo-clj.middleware :refer [middleware-set]]
            [todo-clj.routes :as routes]
            [todo-clj.server :as s]
            [todo-clj.util.handler :as uh]
            [todo-clj.util.namespace :refer [pre-require]]
            [todo-clj.util.response :as res]))

;;; アプリケーションを起動させただけでは依存ツリーに含まれていないネームスペースがあるのでそれらをロードする
(pre-require
 'todo-clj.handler)

(defn match-handler [k]
  (->> (uh/ring-handlers)
       (filter #(= (:ring-handler (meta %))
                   k))
       first))

(extend-protocol br/Ring
  clojure.lang.Keyword
  (request [k req ctx]
    ;; `todo-clj.util.handler/defhandler` 経由で
    ;; 定義したハンドラーをキーワードから探し出して実行する
    (let [f (match-handler k)]
      (f req))))

(def app
  (middleware-set
   (br/make-handler routes/main)))

(defn go []
  (s/start-server #'app :port 3000))

(defn shutdown []
  (s/stop-server :port 3000))

(defn restart []
  (shutdown)
  (go))
