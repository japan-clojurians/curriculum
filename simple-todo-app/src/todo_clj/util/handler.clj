(ns todo-clj.util.handler)

(defn ring-handler? [var]
  (contains? (meta var) :ring-handler))

(defn ring-handlers
  "全てのネームスペースからメタ情報に `:ring-handler` と付加されている `Var` を全てを取得する"
  []
  (->> (all-ns)
       (mapcat ns-interns)
       (map second)
       (filter ring-handler?)))

(def ^{:doc "ring-handlers 関数と同じだがメモ化する点が異なる(プロダクション環境で使うもの)"}
  memoized-ring-handlers
  (memoize ring-handlers))

(defmacro defhandler
  "Ring ハンドラーを作成するためのマクロ"
  [name tag args & body]
  `(defn ~(vary-meta name assoc :ring-handler tag) ~args
     ~@body))
