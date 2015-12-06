==========
 コメント
==========

Clojure のコメントの書き方は以下の 3 通りです。

* ``comment`` マクロ
* ``;`` リーダーマクロ
* ``#_`` リーダーマクロ

``comment`` マクロ
==================

通常の関数のように使えます。 ``(comment & body)``
このコメントの書き方は複数行に渡るコードをコメントアウトするのに使えます。

.. sourcecode:: clojure

  (comment
    ;; 全てコメントになるので実行されない
    (println "(\\( ⁰⊖⁰)/)")
    (println "(\\( ⁰⊖⁰)/)")
    (println "(\\( ⁰⊖⁰)/)"))
  ;; => nil

  ;; これは実行される
  (println "(\\( ⁰⊖⁰)/)")
  ;; (\\( ⁰⊖⁰)/)
  ;; => nil


``;`` (セミコロン)リーダーマクロ
================================

これは 1 行だけコメントアウトすることができます。 Clojure はこの ``;`` から行の終わりまでを無視します。ただし、このコメントの仕方は Clojure のフォームを破壊することがあるので気をつけて使う必要があります。

.. sourcecode:: clojure

  (println "(\\( ⁰⊖⁰)/)") ;; Clojure はこのコメントを無視する
  ;; (\\( ⁰⊖⁰)/)
  ;; => nil

  (println ;; "Hello, world"
   "Good night") ;; Hello, world ではなく Good night と出力される
  ;; Good night
  ;; => nil

``#_`` リーダーマクロ
=====================

直後のフォームをひとつだけ無視することができます。

.. sourcecode:: clojure

  (map *
       (range 10)
       #_(range 10 20))
  ;; => (0 1 2 3 4 5 6 7 8 9)

** 注意 **
===========

``comment`` マクロは他のふたつと違い ``nil`` を返すことに気をつけてください。

.. sourcecode:: clojure

  ;; 次のフォームは正しく評価することができます
  (map *
       (range 10)
       #_(range 10 20))
  ;; => (0 1 2 3 4 5 6 7 8 9)

  ;; comment マクロを使った場合、正しく評価することができません
  (map *
       (range 10)
       (comment (range 10 20)))
  ;; => ()

参考
====

comment / ClojureDocs

* http://clojuredocs.org/clojure.core/comment

Comments / clojure style guide

* https://github.com/totakke/clojure-style-guide/blob/ja/README.md#comments

reader / clojure.org

* http://clojure.org/reader
