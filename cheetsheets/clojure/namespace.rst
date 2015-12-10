========================
 ネームスペースについて
========================



ネームスペースを作成する
========================

幾つか方法はありますが主に ``ns`` マクロを使います。

.. sourcecode:: clojure

  user> (ns example.core)
  nil
  example.core> (def a ::a)
  #'example.core/a
  example.core> a
  :example.core/a

このように REPL の中でも使うことは勿論出来るんですが、多くの場合ファイルの先頭に書くことが多いものです(なので、 1 ファイルにつき 1 ネームスペースという風に対応させて書くのが普通です)。

現在のネームスペースで他のネームスペースの関数を使えるようにする
================================================================

``require`` を使います。

.. sourcecode:: clojure

  other-ns> (defn other-fn [] "this function from other-ns namespace") ;; other-ns というネームスペースで関数を定義
  #'other-ns/other-fn
  other-ns> (in-ns 'user) ;; ネームスペースを移動
  #namespace[user]
  user> (require '[other-ns :as o]) ;; other-ns を o という別名で参照出来るようにする
  nil
  user> (o/other-fn) ;; / で区切って関数を呼びだす
  "this function from other-ns namespace"
  user> ;; or
  user> (require '[other-ns :refer [other-fn]]) ;; other-fn そのものを user ネームスペースから直接参照出来るようにする
  nil
  user> (other-fn) ;; 通常通り呼び出し
  "this function from other-ns namespace"

また、ネームスペースを作成する際に ``ns`` マクロを使いますが、 ``ns`` マクロの中で ``require`` を指定することも出来ます。

.. sourcecode:: clojure

  (ns example.core
    (:require [example.other-ns :as o]))

こういう風にファイルに書くことで、 ``example.core`` ネームスペースで ``example.other-ns`` の関数を使うことが出来ます。
(似た機能を持つものに ``use`` がありますが、最近ではあまり積極的に使う理由がないので使う必要はありません)
