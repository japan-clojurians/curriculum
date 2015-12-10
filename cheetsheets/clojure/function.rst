============
 関数の定義
============


``fn`` と ``defn`` を使った関数の定義
=====================================

Clojure で関数を作るには ``fn`` 関数(厳密にはスペシャルフォーム)を使います。 ``(fn [引数*] 関数ボディ)`` というように使えます。

.. sourcecode:: clojure

  user> ((fn [x] (+ x 1)) 0) ;; 名前のない関数として定義して即時実行
  1
  user> (def my-inc (fn [x] (+ x 1))) ;; 作成した関数に名前を付ける
  #'user/my-inc
  user> (my-inc 1)
  2

また、このように ``def`` と ``fn`` を組み合わせて使うのは一般的なので、 ``defn`` という関数(厳密にはマクロ)が用意されています。

.. sourcecode:: clojure

  user> (defn my-dec [x]
          (- x 1))
  #'user/my-dec
  user> (my-dec 1)
  0

``let`` と ``letfn`` での関数定義
=================================

``let`` では変数束縛が出来ますが、関数というのもひとつの値なので同様にして変数を束縛することが出来ます。

.. sourcecode:: clojure

  user> (let [myeven? (fn [x] (zero? (rem x 2)))]
          (myeven? 10))
  true

  user> (let [f1 (fn [x] (+ x 10))
              f2 (fn [y] (+ (f1 y) 20))]
          (println (f1 0) (f2 0)))
  10 30
  nil

  user> (let [myeven? (fn [x] (if (zero? x) true (myodd? (dec x))))
              myodd? (fn [x] (if (zero? x) false (myeven? (dec x))))]
          (myeven? 10))
  CompilerException java.lang.RuntimeException: Unable to resolve symbol: myodd? in this context, compiling:(*cider-repl demo*:252:47)

最後の例のように ``let`` 内で循環参照するような定義は出来ません。この場合は ``letfn`` を使います。

.. sourcecode:: clojure

  user> (letfn [(myeven? [x] (if (zero? x) true (myodd? (dec x))))
                (myodd? [x] (if (zero? x) false (myeven? (dec x))))]
          (myeven? 10))
  true

匿名関数リテラル ``#()`` での関数定義
=====================================

``fn`` のショートハンドとして匿名関数用のリテラルが用意されています。

.. sourcecode:: clojure

  user> (map #(str % ", world") ["Hello"])
  ("Hello, world")

  user> (defn gen-binary [] (clojure.string/join (take (inc (rand-int 10)) (repeatedly #(rand-nth [0 1])))))
  #'user/gen-binary
  user> (map #(Long/parseLong %1 %2) ;; 引数を複数受け取る場合、第一引数を %1, 第二引数を %2 …というようにする
             (repeatedly 10 gen-binary)
             (repeat 2))
  (537 1 4 10 0 835 0 0 124 6)

匿名関数リテラルの注意点は暗黙的な ``do`` がないことと、匿名関数リテラルのネストは出来ないということ。
