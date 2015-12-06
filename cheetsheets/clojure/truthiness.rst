==============
 真らしいもの
==============

Clojure における真偽値は ``true`` と ``false`` ですが、 ``if`` や ``when`` におけるテスト節は ``true`` と ``false`` の 2 値だけで判断しているわけではありません。

以下の例を見てみましょう。

.. sourcecode:: clojure

  user> (if true :truthy :falsy)
  :truthy
  user> (if false :truthy :falsy)
  :falsy
  user> (if 0 :truthy :falsy)
  :truthy
  user> (if 1 :truthy :falsy)
  :truthy
  user> (if -1 :truthy :falsy)
  :truthy
  user> (if nil :truthy :falsy)
  :falsy
  user> (if '(1) :truthy :falsy)
  :truthy
  user> (if '() :truthy :falsy)
  :truthy
  user> (if (> 1 10) :truthy :falsy)
  :falsy

このように ``if`` は ``true`` か ``false`` という値以外にも真や偽として扱うケースがあることが分かります。簡単な覚え方は ``false`` それ自身と ``nil`` だけが偽で、それ以外は真であると覚えることです。 ``0`` や ``'()`` というのも Clojure においては真として扱うということですね。
