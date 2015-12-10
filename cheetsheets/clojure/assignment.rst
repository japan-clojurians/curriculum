====================
 変数の束縛について
====================

Clojure では Java などの言語のように "代入" という言葉を使わず、代わりに "束縛" という言葉を使います。しかし、ここではその違いには触れません。

``def`` を使った変数の束縛
==========================

``def`` という関数(厳密にはスペシャルフォーム)を使うことで実行したネームスペース( ``*ns*`` )に対して、変数を作ることが出来ます(インターンするとか言います)。

``(def 変数名 値)`` という風に使います。

.. sourcecode:: clojure

  user> (def my-birthday "1991-05-29")
  #'user/my-birthday
  user> (println "My birthday is " my-birthday)
  My birthday is  1991-05-29
  nil

  user> (ns other-ns) ;; ネームスペースを切り替える
  nil
  other-ns> my-birthday ;; other-ns には my-birthday が作られていないので参照することができない
  CompilerException java.lang.RuntimeException: Unable to resolve symbol: my-birthday in this context, compiling:(*cider-repl demo*:1:6354)
  other-ns> user/my-birthday ;; user ネームスペースの my-birthday であることを明示すれば参照することが出来る
  "1991-05-29"

作成したネームスペースの外から参照されたくない場合は ``^:private`` を付けてあげましょう。

.. sourcecode:: clojure

  user> (def nickname "ayato_p")
  #'user/nickname
  user> nickname
  "ayato_p"
  user> (def ^:private fullname "John F. Kennedy")
  #'user/fullname
  user> fullname
  "John F. Kennedy"

  user> (ns other-ns)
  nil
  other-ns> user/nickname
  "ayato_p"
  other-ns> user/fullname ;; private というメタ情報をつけているので簡単には参照出来ない
  CompilerException java.lang.IllegalStateException: var: user/fullname is not public, compiling:(*cider-repl demo*:1:6354)
  other-ns> #'user/fullname ;; Var の参照は出来るのでこれを deref すれば値を得ることが出来る
  #'user/fullname
  other-ns> (deref #'user/fullname)
  "John F. Kennedy"

``let`` を使った変数の束縛
==========================

``def`` を使った場合、ネームスペースに対して変数を作ることになりますが、関数の内部だけで使い捨てにしたい変数などを作りたい場合は ``let`` を使います。

``(let [束縛*] 何かする)`` というように使えます。束縛のところは変数名と値の対を幾つでも並べることが出来ます。

.. sourcecode:: clojure

  user> (let [a 10  ;; 10 に a を束縛
              b 20] ;; 20 に b を束縛
          (+ a b))  ;; a と b を足す
  30
  user> a ;; a は let の中で現れたので参照することは出来ない
  CompilerException java.lang.RuntimeException: Unable to resolve symbol: a in this context, compiling:(*cider-repl demo*:1:6354)

この例では関数を使っていませんが関数でも同じです。

.. sourcecode:: clojure

  user> (defn mapinc [coll]
          (let [res (map inc coll)]
            res))
  #'user/mapinc
  user> mapinc
  #function[user/mapinc]
  user> res
  CompilerException java.lang.RuntimeException: Unable to resolve symbol: res in this context, compiling:(*cider-repl demo*:1:6354)
