====
 型
====

Clojure の主な型は次のようになります。

.. sourcecode:: clojure

  user> (type nil)
  nil
  user> (type true)
  java.lang.Boolean
  user> (type false)
  java.lang.Boolean

  user> ;; numbers
  user> (type 0)
  java.lang.Long
  user> (type 1234567890)
  java.lang.Long
  user> (type 12345678901234567890)
  clojure.lang.BigInt
  user> (type 0N)
  clojure.lang.BigInt
  user> (type 3.14)
  java.lang.Double
  user> (type 3.14M)
  java.math.BigDecimal
  user> (type 4/3)
  clojure.lang.Ratio

  user> (type :foo) ;; keyword
  clojure.lang.Keyword
  user> (type 'foo) ;; symbol
  clojure.lang.Symbol
  user> (type "foo") ;; string
  java.lang.String
  user> (type \f) ;; character
  java.lang.Character

  user> ;; data structures
  user> (type [])
  clojure.lang.PersistentVector
  user> (type '())
  clojure.lang.PersistentList$EmptyList
  user> (type '(1))
  clojure.lang.PersistentList
  user> (type {})
  clojure.lang.PersistentArrayMap
  user> (type #{})
  clojure.lang.PersistentHashSet

  user> ;; other types
  user> (type Boolean)
  java.lang.Class
  user> (type + )
  clojure.core$_PLUS_
  user> (type /)
  clojure.core$_SLASH_
  user> (type (fn []))
  user$eval17475$fn__17476
  user> (type #"[\d]+")
  java.util.regex.Pattern

比較と同じもの
==============

.. sourcecode:: clojure

  user> (> 1 0.9M) ;; 型が違っても比較することが出来る
  true
  user> (< 1 (* 999 0.1))
  true
  user> (< 1N 5/2)
  true

  user> (< "A" "C") ;; 比較演算子で文字列を比較することは出来ない。代わりに compare 関数を使う
  ClassCastException java.lang.String cannot be cast to java.lang.Number  clojure.lang.Numbers.lt (Numbers.java:221)

  user> (= 4/3 8/6) ;; Ratio 型の場合、約分した結果が同じになるなら同じものという扱い
  true
  user> (= 1 1.0) ;; 型が違うので同じ値とは認められない
  false
  user> (= (fn []) (fn [])) ;; 同じ構造の関数だけど等しくはない
  false
