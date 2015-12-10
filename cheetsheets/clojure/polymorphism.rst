==================
 ポリモーフィズム
==================

Clojure ではプロトコルとマルチメソッドという機能で他の言語にあるような多態を表現することが出来ます。

マルチメソッドによる多重ディスパッチ
====================================

.. sourcecode:: clojure

  user> (defmulti description (fn [m] (:score m)))
  #'user/description
  user> (defmethod description :default
          [m]
          (str (:name m) "はよく分かりません"))
  #multifn[description 0x4f3cf1bd]
  user> (defmethod description :good
          [m]
          (str (:name m) "はとても優秀です"))
  #multifn[description 0x4f3cf1bd]
  user> (defmethod description :bad
          [m]
          (str (:name m) "は優秀ではありません"))
  #multifn[description 0x4f3cf1bd]

  user> (description {:name "Alex" :score :good})
  "Alexはとても優秀です"
  user> (description {:name "Kevin" :score :bad})
  "Kevinは優秀ではありません"
  user> (description {:name "Bob" :score nil})
  "Bobはよく分かりません"

プロトコルによるポリモーフィズム
================================

.. sourcecode:: clojure

  user> (defprotocol CoerceLong
          (->long [x] "Long に強制変換するプロトコル"))
  CoerceLong
  user> (extend-protocol CoerceLong ;; 既存の Java のクラスをプロトコルで拡張してみる
          java.lang.String
          (->long [s] (Long/parseLong s 10)))
  nil
  user> (->long "10") ;; 文字列を強制的に Long へと変換出来るようになった
  10
  user> (extend-protocol CoerceLong ;; nil が渡されたら 0 になって欲しいので nil を拡張
          nil
          (->long [x] 0))
  nil
  user> (->long nil)
  0
  user> (defrecord Money [value currency] ;; 自分で作った Money というレコード型にも適用出来る
          CoerceLong
          (->long [self] value))
  user.Money
  user> (->long (Money. 10 "jpy"))
  10
