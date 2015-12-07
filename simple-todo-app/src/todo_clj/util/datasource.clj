(ns todo-clj.util.datasource
  (:require [clojure.java.jdbc :as jdbc]))

(defrecord PlainDataSource [db-spec]
  javax.sql.DataSource
  (getConnection [self]
    (jdbc/get-connection db-spec)))

(defn make-datasource [db-spec]
  (PlainDataSource. db-spec))
