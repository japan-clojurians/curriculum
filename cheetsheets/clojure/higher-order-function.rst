==========
 高階関数
==========

簡単な高階関数の例
==================

Clojure では関数を他の文字列や数値と同様に関数に渡したり、関数から返してもらうことが出来ます。

.. sourcecode:: clojure

  user> (defn adder-generator [x] ;; 関数を返す関数の例
          (fn [y] (+ y x)))
  #'user/adder-generator
  user> (def plus3 (adder-generator 3)) ;; 与えられた数字に 3 を加算するような関数を作っている
  #'user/plus3
  user> (plus3 8)
  11

  user> (defn wrap-str [f] ;; 関数を受け取って新しい関数を返す関数の例
          (fn [x] (str (f x))))
  #'user/wrap-str
  user> (def inc-str (wrap-str inc)) ;; inc 関数を渡して新しい関数を作っている
  #'user/inc-str
  user> (inc-str 41)
  "42"
