;;; Copyright (c) 2015 Yoshinori Kohyama.
;;; Distributed under the BSD 3-Clause License.
;;; You must not remove this notice, or any other, from this software.
(ns animation-toh-clj.terminal-test
  (:require [animation-toh-clj.terminal :as t]
            [clojure.test :refer :all]))

(deftest represent-fn-test
  (are [n state _ r] (= ((#'t/represent-fn n) state) r)
    2 {1 [ 3 2] 2 [ 3 3]} -> (str "                   \n"
                                  "   |     |     |   \n"
                                  "  -|-    |     |   \n"
                                  " --|--   |     |   \n"
                                  "===================\n")
    2 {1 [ 3 0] 2 [ 3 3]} -> (str "  - -              \n"
                                  "   |     |     |   \n"
                                  "   |     |     |   \n"
                                  " --|--   |     |   \n"
                                  "===================\n")
    2 {1 [ 9 1] 2 [15 3]} -> (str "                   \n"
                                  "   |    -|-    |   \n"
                                  "   |     |     |   \n"
                                  "   |     |   --|-- \n"
                                  "===================\n")
    2 {1 [15 2] 2 [15 3]} -> (str "                   \n"
                                  "   |     |     |   \n"
                                  "   |     |    -|-  \n"
                                  "   |     |   --|-- \n"
                                  "===================\n")))
