(ns puyo-clj.core
  (:require [clojure.set :refer [union]]
            [clojure.string :as string]))

(defn- fall-one [b s]
  (->> (reverse b)
       (apply map vector)
       (map #(reduce
              (fn [[h & r] x]
                (if h
                  (if (and (= h s) (not= x s))
                    (cons h (cons x r))
                    (cons x (cons h r)))
                  (list x)))
              ()
              %))
       (apply map vector)
       vec))

(defn- connect
  "Separate sets of sets 'ss' into two group,
  by if it has one or more elements equals to one of 'ev' or not.
  And take the union of the former and conj 'e' to it,
  and conj it to the latter."
  [ev ss e]
  (let [hn (group-by (fn [s] (some (fn [e] (some #(= e %) s)) ev)) ss)
        h (hn true)
        n (set (hn nil))]
    (conj n (conj (apply union h) e))))

(defn- grouped-indices [b]
  (let [h (count b)
        w (count (first b))]
    (reduce
     (fn [a [y x :as yx]]
       (let [c (get-in b yx)
             uyx [(dec y) x]
             lyx [y (dec x)]]
         (connect (filter #(= (get-in b %) c) [uyx lyx]) a yx)))
     #{}
     (for [y (range h) x (range w)] [y x]))))



(defn- erase [b s n]
  (->> (grouped-indices b)
       (remove #(= (get-in b (first %)) s))
       (filter #(< n (count %)))
       (apply union)
       (reduce #(assoc-in %1 %2 s) (mapv vec b))))



(defn- step
  "fall or erase"
  [b s n]
  (let [c (fall-one b s)]
    (if (not= c b) c (erase b s n))))

(defn- bprint [b]
  (print "\033[0;0H") ; move (0,0)
  (dorun
   (map (fn [l]
          (println
           (apply str
                  (map #({\R "\033[0;31mR\033[0m"
                          \G "\033[0;32mG\033[0m"
                          \B "\033[0;34mB\033[0m"
                          \Y "\033[0;33mY\033[0m"}
                         % %)
                       l))))
        b)))

(defn- stage [b w]
  (print "\033[2J") ; clear
  (loop [b b]
    (bprint b)
    (Thread/sleep w)
    (let [a (mapv #(apply str %) (step (mapv vec b) \space 3))]
      (if (not= a b)
        (recur a)))))

(defn from-file
  ([f w] (stage (string/split (slurp f) #"\n") w))
  ([f] (from-file f 500)))
