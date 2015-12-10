==================
 条件分岐について
==================

``if``, ``if-not``
==================

他の言語とだいたい同じように動きますが、 ``unless`` ではなく ``if-not`` が ``if`` の逆です。 else 節は省略可能ですが、もし else 入った場合は ``nil`` を返却します。
``if`` は ``(if テスト then節 else節)`` と書けます。

.. sourcecode:: clojure

  user> (if false
          (println "This is true")
          (println "This is false"))
  This is false
  nil
  user> (if true
          (println "This is true")
          (println "This is false"))
  This is true
  nil
  user> (if-not true
          (println "This is false")
          (println "This is true"))
  This is true
  nil
  user> (if false
          (println "This is true"))
  nil


``when``, ``when-not``
======================

``if`` を書いて ``true`` のときは何かするけど ``false`` のときは特に何もしないというときに ``when``, ``when-not`` は有効です。

.. sourcecode:: clojure

  user> (when true
          (println "1st")
          (println "2nd"))
  1st
  2nd
  nil
  user> (when false
          (println "1st")
          (println "2nd"))
  nil
  user> (when-not false
          (println "1st")
          (println "2nd"))
  1st
  2nd
  nil

``cond``
========

``if`` のように真偽だけでの条件分岐ではなく、複数の分岐をしたいケースはよくあります。そのようなときに使えるのが ``cond`` です。
``cond`` は ``(cond テストA 返り値A テストB 条件B *)`` というように書け、上から順番にテストをしていき、最初に真を返したテストに対応する返り値を ``cond`` の結果として返却します(どのテストにもひっかからない場合は ``nil`` )。

.. sourcecode:: clojure

  user> (cond
          false "false"
          true "true")
  "true"
  user> (cond
          false "false"
          nil   "nil"
          :else "something else") ;; 「どれでもなければ」みたいなものを書きたいときはテスト節に適当な真となる値を入れておくとそれが返却されます
  "something else"
  user> (defn fizzbuzz [x]
          (cond
            (zero? (rem x 15)) "FizzBuzz"
            (zero? (rem x 3)) "Fizz"
            (zero? (rem x 5)) "Buzz"
            :else x))
  #'user/fizzbuzz
  user> (fizzbuzz 1)
  1
  user> (fizzbuzz 15)
  "FizzBuzz"

``case``
========

``cond`` はテストが真のときに返り値を返すという機能でしたが、 ``case`` はそれとはまた異なります。
``case`` は次のように書けます。 ``(case 式 テスト値+返り値* デフォルト値)``

.. sourcecode:: clojure

  user> (let [m {:name "Alex" :gender :man}]
          (case (:gender m)
            :man "男性"
            :woman "女性"))
  "男性"
  user> (let [m {:name "ayato_p"}]
          (case (:gender m)
            :man "男性"
            :woman "女性")) ;; デフォルト値を設定していなくてどのテストも当てはまらなかったら例外を投げる
  IllegalArgumentException No matching clause:   user/eval17703 (form-init7458318143784847467.clj:84)
  user> (let [m {:name "ayato_p"}]
          (case (:gender m)
            :man "男性"
            :woman "女性"
            "不明"))
  "不明"

  user> (let [x 10]
          (case x
            (1 5) :one-or-five          ;; テストにクォートしていないリストを書いておくと x が 1 か 5 だったら、というような意味になる
            (2 4 6) :two-or-four-or-six
            9 :just-nine
            :anything-else))
  :anything-else
  user> (let [x 1]
          (case x
            (1 5) :one-or-five
            (2 4 6) :two-or-four-or-six
            9 :just-nine
            :anything-else))
  :one-or-five
  user> (let [x 9]
          (case x
            (1 5) :one-or-five
            (2 4 6) :two-or-four-or-six
            9 :just-nine
            :anything-else))
  :just-nine


``or``, ``and``
===============

``or`` と ``and`` です。どちらも可変長で引数を取れるので、例えば ``(or a b c)`` は ``a or b or c`` と考えることが出来ます。

.. sourcecode:: clojure

  user> (or :foo :bar)
  :foo
  user> (and :foo :bar)
  :bar
  user> (or nil :foo)
  :foo
  user> (and nil :foo)
  nil
  user> (or :foo false)
  :foo
  user> (and :foo false)
  false

  user> (and :foo)
  :foo
  user> (or :foo)
  :foo

  user> (let [x 2]
          (if (or (> x 100) (even? x))
            "true"))
  "true"
  user> (let [x 2]
          (if (and (> x 100) (even? x))
            "true"
            "false"))
  "false"
  user> (def alex {:name "Alex" :gender :male})
  #'user/alex
  user> (let [gender (or (:sex alex) (:gender alex))]
          (println gender))
  :male
  nil

式として使える
==============

幾つかの例でもありましたが、これらの条件分岐に使う関数は何かしらの値を返します。なので、次のように使えます。

.. sourcecode:: clojure

  user> (defn unifier [m] ;; 性別のカラムが :sex と :gender と混ざっていて男性とかを表す表記もずれているのを統一かする関数
          (let [gender (or (:sex m) (:gender m))]
            (cond
              (or (= gender :man) (= gender :male)) :man
              (or (= gender :woman) (= gender :female)) :woman
              :else :free)))
  #'user/unifier
  user> (unifier {:sex :man})
  :man
  user> (unifier {:sex :male})
  :man
  user> (unifier {:sex :female})
  :woman
  user> (unifier {:sex :woman})
  :woman
  user> (unifier {:sex nil})
  :free
