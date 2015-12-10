==========================
 シーケンスの操作について
==========================

シーケンス(遅延シーケンス含む)を操作するための関数が Clojure には沢山あるのでそれらを紹介します。

シーケンスの生成
================

.. sourcecode:: clojure

  user> (take 10 (range))
  (0 1 2 3 4 5 6 7 8 9)
  user> (range 10)
  (0 1 2 3 4 5 6 7 8 9)
  user> (range 1 11)
  (1 2 3 4 5 6 7 8 9 10)
  user> (range 0 20 5)
  (0 5 10 15)
  user> (take 10 (range 1 Double/POSITIVE_INFINITY 100))
  (1 101 201 301 401 501 601 701 801 901)

  user> (take 10 (iterate inc 1))
  (1 2 3 4 5 6 7 8 9 10)
  user> (take 10 (iterate dec 1))
  (1 0 -1 -2 -3 -4 -5 -6 -7 -8)
  user> (take 10 (iterate (partial + 10) 1))
  (1 11 21 31 41 51 61 71 81 91)
  user> (take 10 (iterate #(* % 2) 1))
  (1 2 4 8 16 32 64 128 256 512)

  user> (take 10 (cycle [1 2 3]))
  (1 2 3 1 2 3 1 2 3 1)
  user> (take 10 (cycle [:foo :bar]))
  (:foo :bar :foo :bar :foo :bar :foo :bar :foo :bar)
  user> (take 10 (cycle "Hello"))
  (\H \e \l \l \o \H \e \l \l \o)

シーケンスから値を取り出す
==========================

.. sourcecode:: clojure

  user> (take 10 (range))
  (0 1 2 3 4 5 6 7 8 9)
  user> (take-while #(< % 20) (range))
  (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19)
  user> (take-while even? (range))
  (0)
  user> (nth (range) 10)
  10
  user> (take-nth 2 (range 10))
  (0 2 4 6 8)

シーケンスから値を落とす
========================

.. sourcecode:: clojure

  user> (drop 2 (range 10))
  (2 3 4 5 6 7 8 9)
  user> (drop-while #(< % 5) (range 10))
  (5 6 7 8 9)
  user> (drop-last (range 10))
  (0 1 2 3 4 5 6 7 8)

シーケンスへ変換する
====================

.. sourcecode:: clojure

  user> (seq [1 2 3])
  (1 2 3)
  user> (seq {:foo 1 :bar 2 :baz 3})
  ([:foo 1] [:bar 2] [:baz 3])
  user> (seq #{1 2 3})
  (1 3 2)
