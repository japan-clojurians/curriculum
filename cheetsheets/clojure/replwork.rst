===============
 REPL の使い方
===============

Clojure で REPL を使って開発しないというのは両手を縛ってボクシングをするようなものです。ここではどのように REPL を使うか簡単に説明します。
またここで説明した関数の幾つかはエディタのプラグインなどでショートカットが定義してあったりするので、使っているプラグインの README を読むなどしてみるといいでしょう。

ドキュメントを引く
==================

最もよく使うのはドキュメントを引く機能です。例えば他の言語で開発しているときに、「あれ、この関数どうやって使うんだっけ」と悩んだ経験ありませんか?
そういうときに普通ならインターネットで「言語名 関数名」というように検索してドキュメントを探したりするでしょう。もしくは他のドキュメント管理ツールアプリからドキュメントを引くかもしれません。
Clojure なら REPL が教えてくれます。 Leiningen の REPL を使っているならデフォルトで ``clojure.repl/doc`` 関数が ``doc`` だけで呼び出せるようになっているのでそれを使います。

.. sourcecode:: clojure

  user> (doc juxt)
  -------------------------
  clojure.core/juxt
  ([f] [f g] [f g h] [f g h & fs])
    Takes a set of functions and returns a fn that is the juxtaposition
    of those fns.  The returned fn takes a variable number of args, and
    returns a vector containing the result of applying each fn to the
    args (left-to-right).
    ((juxt a b c) x) => [(a x) (b x) (c x)]
  nil
  user> (doc map)
  -------------------------
  clojure.core/map
  ([f] [f coll] [f c1 c2] [f c1 c2 c3] [f c1 c2 c3 & colls])
    Returns a lazy sequence consisting of the result of applying f to
    the set of first items of each coll, followed by applying f to the
    set of second items in each coll, until any one of the colls is
    exhausted.  Any remaining items in other colls are ignored. Function
    f should accept number-of-colls arguments. Returns a transducer when
    no collection is provided.
  nil

関数を搜す
==========

補完機能が使えればあまり必要無いかもしれませんが、例えば「 xx-find っていう名前だったと思うけどなんだっけ」というようなときに補完機能はあまり意味がありません。
そういうときに Clojure の REPL を使えば簡単に関数を検索することが出来て便利です。 ``clojure.repl/apropos`` という関数を使います。

.. sourcecode:: clojure

  user> (apropos "jux")
  (clojure.core/jux)t
  user> (apropos #"^re\-") ;; 正規表現が使える
  (clojure.core/re-find clojure.core/re-groups clojure.core/re-matcher clojure.core/re-matches clojure.core/re-pattern clojure.core/re-seq clojure.string/re-quote-replacemen)t
  user> (apropos #"r.*kv)"
  (clojure.core/reduce-kv)

関数の定義そのものをみる
========================

この関数どういう風に定義されているんだろう?と気になったときに弱いエディタを使っていると定義ジャンプもままならないときがあります。ですが、 Clojure なら定義ジャンプしなくても定義そのものを出力させることが出来ます。

.. sourcecode:: clojure

  user> (source symbol)
  (defn symbol
    "Returns a Symbol with the given namespace and name."
    {:tag clojure.lang.Symbol
     :added "1.0"
     :static true}
    ([name] (if (symbol? name) name (clojure.lang.Symbol/intern name)))
    ([ns name] (clojure.lang.Symbol/intern ns name)))
  nil
  user> (source clojure.string/blank?)
  (defn blank?
    "True if s is nil, empty, or contains only whitespace."
    {:added "1.2"}
    [^CharSequence s]
    (if s
      (loop [index (int 0)]
        (if (= (.length s) index)
          true
          (if (Character/isWhitespace (.charAt s index))
            (recur (inc index))
            false)))
      true))
  nil

ネームスペースに何が定義されているのかを知る
============================================

「このネームスペース他にどんな関数が定義されているんだろう」というときに ``clojure.repl/dir`` は便利です。

.. sourcecode:: clojure

  user> (dir clojure.string)
  blank?
  capitalize
  escape
  join
  lower-case
  re-quote-replacement
  replace
  replace-first
  reverse
  split
  split-lines
  trim
  trim-newline
  triml
  trimr
  upper-case
  nil

無限の長さのシーケンスをうっかり評価してしまっても大丈夫
========================================================

``*print-length*`` に適当な値を設定しておくとうっかりしてても止まるようになります。

.. sourcecode:: clojure

  user> (set! *print-length* 1)
  1
  user> (range)
  (0 ...)
  user> (set! *print-length* 10)
  10
  user> (range)
  (0 1 2 3 4 5 6 7 8 9 ...)
