==============
 繰り返し処理
==============

Clojure では色々なデータ構造がシーケンスとして扱えますが、そのシーケンスを上手く処理する関数も沢山あります。

``loop``, ``recur``
===================

原始的な繰り返し処理です。 ``(loop [束縛*] 式*)`` と書きますが、式*の中で ``recur`` を使うと再帰的にループさせることが出来ます。

.. sourcecode:: clojure

  user> (loop [l (range 10) res 0]
          (if (seq l) ;; リストの空チェック
            (recur (rest l) (+ res (first l))) ;; リストが空でない場合、リストの先頭を除いたリストと、リストの先頭をこれまでの加算結果に足したものを recur に渡す
            res)) ;; リストが空なら加算結果を返却する/繰り返し処理を抜ける
  45

``loop`` の第一引数は ``let`` と同じような束縛を書きますが、ここではループの初期状態を書きます。そして ``recur`` には ``loop`` で束縛している変数と同じだけの引数を渡してあげます。
正確ではないですが、上の例は次のように書いているのと同じような意味になります。

.. sourcecode:: clojure

  user> (let [l (range 10)
              res 0]
          (defn f [l' res']
            (if (seq l')
              (f (rest l') (+ res' (first l')))
              res'))
          (f l res))
  45

多くの場合、 ``loop``, ``recur`` は直接使う必要はなく、下に書いてあるようにもう少し高度な関数を使います。

``for``
=======

``for`` はコレクションの先頭から順に同じ処理を実行し結果のシーケンスを返却します。後述する ``map`` 関数に似ているところがありますが、複数のコレクションを渡したときにそれらを全ての組み合わせで実行したり、 ``:when`` で処理を制御出来たりする点が異なります。

.. sourcecode:: clojure

  user> (for [n (range 10)]
          n)
  (0 1 2 3 4 5 6 7 8 9)
  user> (for [n (range 10)]
          (inc n))
  (1 2 3 4 5 6 7 8 9 10)
  user> (for [n (range 10)
              :when (even? n)]
          n)
  (0 2 4 6 8)
  user> (for [n (range 10)
              :let [n' (inc n)]]
          [n n'])
  ([0 1] [1 2] [2 3] [3 4] [4 5] [5 6] [6 7] [7 8] [8 9] [9 10])
  user> (for [n (range 1 6) ;; 九九の計算
              m (range 1 6)]
          (* n m))
  (1 2 3 4 5 2 4 6 8 10 3 6 9 12 15 4 8 12 16 20 5 10 15 20 25)

``map``
=======

最近の言語では ``map`` という名前で同じような関数が用意されていることも多いと思いますが、 Clojure でも大凡同じです。関数とコレクションを受け取り関数をコレクションの全ての要素に適用します(ただし、遅延シーケンスとして返すので評価するタイミングは任意)。
また複数のコレクションを渡すと ``for`` とは違い、全てのコレクションが並行して関数を適用されていきます。ただし、最も要素の少ないコレクションが尽きたところで関数の適用は終わり、それ以降の要素が残っているコレクションは無視されます(なので無限のシーケンスと一緒に渡してもどちらかが有限であれば問題ありません/とはいえ遅延シーケンスを返すので適切に処理していれば止まらないなんてことは起こりませんが)。

.. sourcecode:: clojure

  user> (map + (range 10))
  (0 1 2 3 4 5 6 7 8 9)
  user> (map + (range 10) (range 20 Double/POSITIVE_INFINITY))
  (20 22 24 26 28 30 32 34 36 38)
  user> (map + (range 10) (range 3))
  (0 2 4)

``filter``, ``remove``
======================

``filter`` と ``remove`` どちらもコレクションから条件に合致するものを収集/除外するもので同じような使い方をします。

.. sourcecode:: clojure

  user> (filter even? (range 10))
  (0 2 4 6 8)
  user> (filter odd? (range 10))
  (1 3 5 7 9)
  user> (remove even? (range 10))
  (1 3 5 7 9)
  user> (remove odd? (range 10))
  (0 2 4 6 8)
  user> (filter (comp #{:foo} key) {:foo 10 :bar 20})
  ([:foo 10])

``reduce``
==========

``reduce`` は、二引数の関数を第一引数として受け取りコレクションを収束させるように働きます。

.. sourcecode:: clojure

  user> (reduce + (range 11)) ;; = (reduce + 0 (range 11))
  55

  user> (reduce (fn [x m] (+ x (:score m)))
                0
                [{:name "foo" :score 100} {:name "bar" :score 90}])
  190

  user> (reduce #(conj %1 (:name %2))
                #{}
                [{:name "foo"} {:name "bar"} {:name "baz"} {:name "bar"}])
  #{"foo" "bar" "baz"}
