========
 文字列
========

Clojure では文字列は ``"`` (ダブルクォート)で囲うことで表現します。他の言語の中には ``'`` (シングルクォート)を使うことが出来るものも存在しますが Clojure ではそうではありません。ダブルクォートを文字列の中に表現したい場合は、 ``\`` (バックスラッシュ)でエスケープします。

.. sourcecode:: clojure

  user> (def message "welcome to clojure")
  #'user/message
  user> message
  "welcome to clojure"
  user> (println message)
  welcome to clojure
  nil
  user>
  user> (def quoted "He said, \"Welcome to Clojure\"")
  #'user/quoted
  user> quoted
  "He said, \"Welcome to Clojure\""
  user> (println quoted)
  He said, "Welcome to Clojure"
  nil

文字列を操作するための関数は ``clojure.core`` 名前空間に少しと ``clojure.string`` 名前空間にまとめられています。

.. sourcecode:: clojure

  user> (compare "welcome" "Welcome")
  32
  user> (str "Welcome" " to " "Clojure")
  "Welcome to Clojure"
  user> (count "Hello, world")
  12
  user> (clojure.string/reverse "Scott")
  "ttocS"
  user> (clojure.string/replace-first "scott" #"s" "S")
  "Scott"
  user> (clojure.string/reverse "Scott")
  "ttocS"
  user> (clojure.string/replace-first "scott" #"s" "S")
  "Scott"
  user> (clojure.string/capitalize "scott")
  "Scott"
  user> (clojure.string/upper-case "scott")
  "SCOTT"
  ;; 他にも多くの関数が clojure.string 名前空間には存在します。


また文字列を文字型のシーケンスとして扱うこともできます。

.. sourcecode:: clojure

  user> (drop 1 "Hello")
  (\e \l \l \o)
  user> (reverse "Hello")
  (\o \l \l \e \H)
  user> (clojure.string/join (reverse "Hello")) ;; 文字型のシーケンスを文字列へと戻している
  "olleH"
