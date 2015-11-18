(ns todo-clj.middleware
  (:require [environ.core :refer [env]]
            [immutant.internal.util :as iu]
            [ring.middleware.defaults :as defaults]
            [ring.middleware.http-response :as http-response]
            [todo-clj.middleware.resopnse :as response]))

(defn wrap-dev
  "開発環境のみしか使わないミドルウェアをココで動的に `require` するようにしている"
  [handler]
  (let [wrap-reload (iu/try-resolve 'ring.middleware.reload/wrap-reload)
        wrap-exceptions (iu/try-resolve 'prone.middleware/wrap-exceptions)]
    (if (and wrap-reload wrap-exceptions)
      (-> handler
          wrap-reload
          wrap-exceptions)
      (throw (RuntimeException. "ring/ring-devel と prone が依存関係に含まれていることを確認してください")))))

(def ^:private wrap #'defaults/wrap)

(defn middleware-set
  "ハンドラーへ適用するミドルウェアのセット"
  [handler]
  (-> handler
      http-response/wrap-http-response
      (defaults/wrap-defaults #'defaults/site-defaults)
      (wrap wrap-dev (:dev env))))
