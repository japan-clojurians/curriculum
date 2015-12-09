;;; Copyright (c) 2015 Yoshinori Kohyama.
;;; Distributed under the BSD 3-Clause License.
;;; You must not remove this notice, or any other, from this software.
(ns animation-toh-clj.core)

(defn operations [src tmp dst n]
  (if (pos? n)
    (concat (operations src dst tmp (dec n))
            [[src dst n]]
            (operations tmp src dst (dec n)))
    []))

(defn peg-states [n]
  (reductions (fn [state [src dst i]]
                (assoc state src (rest (state src))
                       dst (cons i (state dst))))
              [(range 1 (inc n)) '() '()]
              (operations 0 1 2 n)))

;;               111111111
;; y\x 0123456789012345678
;; 0
;; 1      |     |     |
;; 2     -|-    |     |
;; 3    --|-- --|-- --|--
;; 4   =================== n+2
;;        |     |     |
;;        |     |     +- 2*(n+1)*2 + (n + 1)
;;        |     +------- 2*(n+1)*1 + (n + 1)
;;        +------------- 2*(n+1)*0 + (n + 1)
;;
(defn disk-states [n]
  (let [get-x (comp first second)
        get-y (comp second second)
        disks (fn [state x] (filter #(= (get-x %) x) state))
        top-disk #(first (sort-by get-y %))]
    (apply concat
           (reductions
            (fn [transients [src dst i]]
              (let [state (last transients)
                    [src-x src-y] (state i)
                    dst-x (* (inc (* 2 dst)) (inc n))
                    dst-y (->> (disks state dst-x)
                               (cons [n [dst-x (+ 2 n)]]) ; sentinel
                               top-disk get-y dec)]
                (mapv #(assoc state i %)
                      (concat (for [y (range (dec src-y) -1 -1)] [src-x y])
                              (for [x (if (< src-x dst-x)
                                        (range (inc src-x) dst-x)
                                        (range (dec src-x) dst-x -1))]
                                [x 0])
                              (for [y (range (inc dst-y))] [dst-x y])))))
            [(into {} (map #(vector % [(inc n) (inc %)]) (range 1 (inc n))))]
            (operations 0 1 2 n)))))
