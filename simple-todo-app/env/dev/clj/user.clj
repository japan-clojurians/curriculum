(ns user
  (:require [clojure.java.io :as io]
            [clojure.java.jdbc :as jdbc]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer :all]
            [clojure.string :as str]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [todo-clj.core :as core]
            [todo-clj.db :as db]
            [todo-clj.migration :refer [migrate]]
            [todo-clj.model.todo :as t]))

(defn seed []
  (t/save-todo "じゃがいもを買う")
  (t/save-todo "にんじんを買う")
  (t/save-todo "玉ねぎを買う"))

(defn rebuild-db! []
  (jdbc/execute! db/db-spec ["DROP ALL OBJECTS"])
  (migrate)
  (seed))

(defn go []
  (migrate)
  (core/start))
