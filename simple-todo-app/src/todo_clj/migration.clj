(ns todo-clj.migration
  (:require [clj-liquibase.cli :as cli]
            [clj-liquibase.core :refer [defparser]]
            [todo-clj.db :as db]
            [todo-clj.util.datasource :as ds]))

(defparser app-changelog "changelog.edn")

(defn migrate []
  (cli/update {:datasource (ds/make-datasource db/db-spec) :changelog app-changelog}))
