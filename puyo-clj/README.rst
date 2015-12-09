=================================
 国民的落ちゲーぷよxx in Clojure
=================================

あの有名な落ちゲーの連鎖を Clojure でシミュレート。

使い方
======

.. sourcecode:: clojure

  user> (require '[puyo-clj.core :as c])
  nil
  user> (c/from-file "resources/seq19.dat")
   YGYRR
  R YGYG
  GYGYRR
  RYGYRG
  YGYRYG
  GYRYRG
  YGYRYR
  YGYRYR
  YRRGRG
  RYGYGG
  GRYGYR
  GRYGYR
  GRYGYR
  ;; 以下略

課題
====

このプログラムに自分なりに手を加えて修正してみましょう。

例)

* 文字を記号に変えてみる
* 状態のシーケンスだけを取り出せるように修正してみる
* グラフィカルユーザーインターフェースで表示する
* etc

License
=======

Copyright (c) 2015 Ayato Nishimura. Distributed under the BSD 3-Clause License.

Original source code
--------------------

https://gist.github.com/kohyama/5828936

Copyright (c) 2013 Yoshinori Kohyama. Distributed under the BSD 3-Clause License.
