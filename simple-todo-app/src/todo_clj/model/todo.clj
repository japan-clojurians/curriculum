(ns todo-clj.model.todo
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :as h]
            [todo-clj.db :as db]))

(defn find-todo
  "TODO を取得する
  `where-clause` がない場合は全件取得する

  ```
  (find-todo)
  (find-todo [:= :id 2])
  ```
  "
  ([] (find-todo [:= 1 1]))
  ([where-clause]
   (->> (-> (h/select :*)
            (h/from :todo)
            (h/where where-clause)
            sql/format)
        (jdbc/query db/db-spec))))

(defn update-todo
  "TODO を更新する
  `where-clause` がない場合は全件更新する

  ```
  (update-todo \"test\")
  (update-todo \"test\" [:= :id 1])
  ```
  "
  ([title] (update-todo title [:= 1 1]))
  ([title where-clause]
   (->> (-> (h/update :todo)
            (h/where where-clause)
            (h/sset {:title title})
            sql/format)
        (jdbc/execute! db/db-spec))))

(defn save-todo [title]
  (jdbc/insert! db/db-spec :todo {:title title}))
