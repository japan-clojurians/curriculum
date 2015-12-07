(ns todo-clj.db
  (:require [environ.core :refer [env]]))

(def db-spec (or (:db env)
                 (:database-url env)))
