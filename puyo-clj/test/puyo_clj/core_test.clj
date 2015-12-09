;;; Copyright (c) 2013 Yoshinori Kohyama.
;;; Distributed under the BSD 3-Clause License.
;;; You must not remove this notice, or any other, from this software.
(ns puyo-clj.core-test
  (:require [clojure.test :refer :all]
            [puyo-clj.core :as p]))

(deftest fall-one-test
  (are [b s a] (= (#'p/fall-one b s) a)
    [[:s :s :s :A]
     [:A :s :s :s]
     [:A :B :s :D]
     [:s :s :C :s]]
    :s
    [[:s :s :s :s]
     [:s :s :s :A]
     [:A :s :s :s]
     [:A :B :C :D]]))

(deftest connect-test
  (are [ev ss e nss] (= (#'p/connect ev ss e) nss)
    [:a]    #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b :g} #{:c} #{:d :e :f}}
    [:c]    #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c :g} #{:d :e :f}}
    [:d]    #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c} #{:d :e :f :g}}
    [:e]    #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c} #{:d :e :f :g}}
    [:a :b] #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b :g} #{:c} #{:d :e :f}}
    [:a :c] #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b :c :g} #{:d :e :f}}
    [:a :d] #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b :d :e :f :g} #{:c}}
    [:c :d] #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c :d :e :f :g}}
    [:d :e] #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c} #{:d :e :f :g}}
    []      #{#{:a :b} #{:c} #{:d :e :f}} :g #{#{:a :b} #{:c} #{:d :e :f} #{:g}}
    []      #{}                           :g #{#{:g}}))

(deftest grouped-indices-test
  (are [b g] (= (#'p/grouped-indices b) g)
    [[:A :s :A :A] [:A :A :C :C] [:C :B :B :B]]
    #{#{[0 0] [1 0] [1 1]}  ; :A
      #{[0 1]}              ; :s
      #{[0 2] [0 3]}        ; :A
      #{[1 2] [1 3]}        ; :C
      #{[2 0]}              ; :C
      #{[2 1] [2 2] [2 3]}} ; :B
    [[:A :A :s :D] [:s :A :A :D] [:C :C :C :C] [:F :F :F :D]]
    #{#{[0 0] [0 1] [1 1] [1 2]} ; :A
      #{[0 2]}                   ; :s
      #{[0 3] [1 3]}             ; :D
      #{[1 0]}                   ; :s
      #{[2 0] [2 1] [2 2] [2 3]} ; :C
      #{[3 0] [3 1] [3 2]}       ; :F
      #{[3 3]}}))                ; :D

(deftest erase-test
  (are [b s n a] (= (#'p/erase b s n) a)
    [[:A :s :s :s] [:A :s :A :A] [:A :A :C :B] [:C :B :B :B]] :s 3
    [[:s :s :s :s] [:s :s :A :A] [:s :s :C :s] [:C :s :s :s]]
    [[:A :A :s :D] [:s :A :A :D] [:C :C :C :C] [:F :F :F :D]] :s 3
    [[:s :s :s :D] [:s :s :s :D] [:s :s :s :s] [:F :F :F :D]]))

(deftest step-test
  (are [b s n r] (= (#'p/step b s n) r)
    [[:A :s :s :D] [:A :B :s :s] [:s :s :C :s]] :s 3
    [[:s :s :s :s] [:A :s :s :D] [:A :B :C :s]]
    [[:A :s :C :s] [:s :D :A :D] [:B :C :s :s]] :s 3
    [[:s :s :s :s] [:A :D :C :s] [:B :C :A :D]]
    [[:A :s :s :s] [:A :s :A :A] [:A :A :C :B] [:C :B :B :B]] :s 3
    [[:s :s :s :s] [:s :s :A :A] [:s :s :C :s] [:C :s :s :s]]
    [[:A :A :s :D] [:s :A :A :D] [:C :C :C :C] [:F :F :F :D]] :s 3
    [[:s :A :s :D] [:A :A :A :D] [:C :C :C :C] [:F :F :F :D]]))
