;;; Copyright (c) 2015 Yoshinori Kohyama.
;;; Distributed under the BSD 3-Clause License.
;;; You must not remove this notice, or any other, from this software.
(ns animation-toh-clj.terminal
  (:require [animation-toh-clj.core :refer [disk-states]]))

(defn- represent-fn [n]
  (let [w (+ (* 6 (inc n)) 2)
        h (+ n 3)
        w-1 (dec w)
        h-1 (dec h)
        base-and-pegs (persistent!
                       (reduce
                        (fn [a [x y c]] (assoc! a (+ (* y w) x) c))
                        (transient (vec (repeat (* h w) \space)))
                        (concat (for [y (range 0 h)]   [w-1 y \newline])
                                (for [x (range 0 w-1)] [x h-1 \=])
                                (for [pi (range 0 3) y (range 1 h-1)]
                                  [(* (inc (* 2 pi)) (inc n)) y \|]))))]
    (fn [state]
      (apply str
             (persistent!
              (reduce
               (fn [a [x y]] (assoc! a (+ (* y w) x) \-))
               (transient base-and-pegs)
               (for [[i [x y]] state
                     xx (concat
                         (take i (iterate dec (dec x)))
                         (take i (iterate inc (inc x))))]
                 [xx y])))))))

(defn display [n w]
  (print "\033[2J")
  (doseq [rep (map (represent-fn n) (disk-states n))]
    (print "\033[2;1H")
    (print rep)
    (flush)
    (Thread/sleep w)))
