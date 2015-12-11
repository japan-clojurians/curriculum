================
 動くハノイの塔
================

プロジェクト構成
================

.. sourcecode:: shell

  ├── project.clj ;; プロジェクト構成を管理しているファイル
  ├── src
  │   └── animation_toh_clj
  │       ├── core.clj ;; ハノイの塔プログラムのコア
  │       └── terminal.clj ;; ハノイの塔をターミナルで動かすためのネームスペース
  └── test ;; テスト
      └── animation_toh_clj
          ├── core_test.clj
          └── terminal_test.clj

使い方
======

.. sourcecode:: clojure

  user> (require '[animation-toh-clj.terminal :as t])
  nil
  user> (t/display 3 1)

      |       |       |
     -|-      |       |
    --|--     |       |
   ---|---    |       |
  =========================

     -|-      |       |
      |       |       |
    --|--     |       |
   ---|---    |       |
  =========================

  ;; 中略

      |       |      -|-
      |       |       |
      |       |     --|--
      |       |    ---|---
  =========================

      |       |       |
      |       |      -|-
      |       |     --|--
      |       |    ---|---
  =========================
  nil

課題
====

このプログラムに自分なりに手を加えて修正してみましょう。

例)

* 対話出来るようなインターフェイスを作ってみる
* 円盤に色を付けてみる
* 実行するステップを任意で選べるようにする(例えば円盤が 2 枚の場合、総ステップ数は 41 あるのでこのうちの 20 から 30 までを実行するようにする、など)
* このプログラムをベースにグラフィカルなユーザーインターフェイスを作成する
* etc

License
=======

Copyright (c) 2015 JapanClojurians. Distributed under the BSD 3-Clause License.

Clojure Animation Tower of Hanoi (Original Project)
---------------------------------------------------

https://github.com/kohyama/clj-animation-towerofhanoi

Copyright (c) 2015 Yoshinori Kohyama. Distributed under the BSD 3-Clause License.
