遅延シーケンス
==============

Clojure の醍醐味のひとつである遅延シーケンスについて軽く触れます。

もっとも簡単な遅延シーケンスの作り方は適当なシーケンスに変換出来るものを ``lazy-seq`` 関数を使って変換してあげる方法です。

.. sourcecode:: clojure

  user> (lazy-seq [10])
  (10)
  user> (type (lazy-seq [10]))
  clojure.lang.LazySeq

また明示的に ``lazy-seq`` 関数を使わなくても、 Clojure におけるコレクションを操作する関数の多くは遅延シーケンスを返すようになっています。例えば ``map`` や ``filter`` 、 ``for`` なども遅延シーケンスを返却します。なのでそれを利用して以下のようなコードが書けます。

.. sourcecode:: clojure

  user> (defn fizzbuzz [x]
          (cond
            (zero? (rem x 15)) "FizzBuzz"
            (zero? (rem x 3)) "Fizz"
            (zero? (rem x 5)) "Buzz"
            :else x))
  #'user/fizzbuzz
  user> (def fb-lseq (map fizzbuzz (rest (range))))
  #'user/fb-lseq
  user> (take 10 fb-lseq)
  (1 2 "Fizz" 4 "Buzz" "Fizz" 7 8 "Fizz" "Buzz")
  user> (take 10 (drop-while (some-fn string? #(<= % 100)) fb-lseq))
  (101 "Fizz" 103 104 "FizzBuzz" 106 107 "Fizz" 109 "Buzz")

上の例ではまずは値 ``x`` を判定して ``"FizzBuzz"``, ``"Fizz"``, ``"Buzz"`` のいずれかもしくは ``x`` そのものを返却する ``fizzbuzz`` 関数を作っています。次に ``(rest (range))`` で 0 を除いた無限の数列を作っています。 ``map`` 関数で ``fizzbuzz`` 関数を無限の数列の数字それぞれに適用していきます。ですが、 ``map`` 関数は遅延シーケンスを返すので、 ``fizzbuzz`` 関数が適用されるタイミングは ``fb-lseq`` を束縛したときではありません。

これによって ``fb-lseq`` という FizzBuzz の遅延シーケンス(無限のリスト)が手に入ったので、 ``take`` などを使うことでそれらを一定数引き出すことが出来ます。

また副作用を伴う処理が遅延シーケンスを返却することもありますが、完全に副作用を起こしてしまいたいときは ``doall`` 関数などを使います。

.. sourcecode:: clojure

  user> (with-open [r (io/reader "README.md")]
          (line-seq r)) ;; line-seq は遅延シーケンスを生成するが最後に with-open を抜けてしまっているので reader がクローズしてしまっていてエラーになる
  IOException Stream closed  java.io.BufferedReader.ensureOpen (BufferedReader.java:122)
  user> (with-open [r (io/reader "README.md")]
          (doall (line-seq r))) ;; 副作用を起こしてあげることでちゃんと値を返すことが出来るようになる(ただこのとき全行吐き出すので大きいファイルの場合は注意)
  ("# demo" "" "A Clojure library designed to ... well, that part is up to you." "" "## Usage" "" "FIXME" "" "## License" "" "Copyright © 2015 FIXME" "" "Distributed under the Eclipse Public License either version 1.0 or (at" "your option) any later version.")
