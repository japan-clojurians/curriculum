========================
 シンプルな TODO アプリ
========================

簡単な TODO アプリを実装しましょう。このプロジェクトでは既に Web アプリケーション開発で必要となるであろう機能が全てではありませんが実装されています。また開発時のみ有効になるような便利な機能も組み合わせてあります。

課題
====

* TODO の新規追加/更新/削除などを実装する
* ユーザーを追加出来るようにする
* ユーザー認証を実装する

開発の進め方
============

1. まずは REPL を起動します
2. 次に REPL で ``(go)`` と評価するとサーバーが起動します
3. ``http://localhost:3000/`` にアクセスして画面が表示されることを確認します
4. ファイルを書き換えて保存すると変更が自動的に反映されます(ルーティングを書き換えた場合など幾つかの特殊なケースでは自動的に反映されないケースがあります)

現在実装されているモノ
======================

* 画面

  * ホーム画面の表示
  * TODO の一覧表示

* 機能

  * ルーティング
  * TODO の検索/追加/更新関数

簡単な用語解説
==============

* ハンドラー: Ring ハンドラーのこと。リクエストマップを受け取って、レスポンスマップを返す。

  * リクエストマップ: HTTP リクエストを Clojure のマップデータにしたもの
  * レスポンスマップ: HTTP レスポンスを Clojure のマップデータにしたもの

* 評価: Clojure のフォームを REPL 上に読み込ませること

どうやって機能を足すか
======================

ルート定義を増やしたい
----------------------

まずは ``todo-clj.routes`` ネームスペースを確認してみましょう。すると次のようなルーティングを確認出来ると思います。

.. sourcecode:: clojure

  (def main
    ["/" {"" :home
          "todo" {"" :todo-index}
          true :not-found}])

ここから ``/`` に対応するのが ``:home`` で、 ``/todo`` に対応するのが ``:todo-index`` であると読み取れます。またいずれでもない場合は ``:not-found`` が対応することになります。

これについて説明するには少々紙面が狭いので、次の例をみてなんとなくこう書けばいいんだなと思ってください。

.. sourcecode:: clojure

  (def main
    ["/" {"" :home
          "todo" {"" :todo-index}
          "settings" {"/user" {"" :user-index                ;; /settings/user
                               "/add" {:get :user-add        ;; /settings/user/add via HTTP GET メソッド
                                       :post :user-add-post} ;; /settings/user/add via HTTP POST メソッド
                               "/:id" :user-show}}           ;; /settings/user/:id :id はプレースホルダ
          true :not-found}])

このように書くことが出来ますが、詳しくは `bidi <https://github.com/juxt/bidi>`_ の README を参照してください。また、ルーティングを修正した場合は ``todo-clj.core/app`` を再評価するか ``todo-clj.core`` ネームスペースをファイルごと REPL 上へとロードしてください(ルーティングを書き換えたときは ``todo-clj.core/app`` をなんらかの方法で評価してください)。

ハンドラーを増やしたい
----------------------

``todo-clj.handler`` 以下にもうひとつ階層があって最初は ``todo-clj.handler.main`` や ``todo-clj.handler.todo`` というネームスペースが存在します。例えば、 ``todo-clj.handler.todo`` は以下のようになっています。

.. sourcecode:: clojure

  (ns todo-clj.handler.todo
    (:require [todo-clj.model.todo :as model]
              [todo-clj.util.handler :refer [defhandler]]
              [todo-clj.util.response :as res]
              [todo-clj.view.todo :as view]))

  (defhandler todo-index :todo-index [req]
    (let [todo-list (model/find-todo)]
      (-> (view/todo-index req todo-list)
          res/ok
          res/html)))

このプロジェクトでは ``todo-clj.util.handler/defhandler`` というマクロを使うことで簡単にハンドラーを定義出来るようにしています。気付いたと思いますが、基本的に普通の関数定義と同じです。
一点違うのは ``defhandler`` の第 2 引数としてキーワードを受け取ることです。このキーワードはルート定義のキーワードと一致させる必要があり、例えば ``todo-index`` ハンドラーはルート定義中の ``:todo-index`` と一致するようになっています。

データベースを操作する関数を増やしたい
--------------------------------------

まずはこのプロジェクトでは `honeysql <https://github.com/jkk/honeysql>`_ という SQL を生成する DSL を提供してくれるライブラリを使っていることを知っておいてください。このライブラリは一般的に次のように使うことが出来ます。

.. sourcecode:: clojure

  user> (require '[honeysql.core :as sql]
                 '[honeysql.helpers :as h])
  ;; => nil
  user> (-> (h/select :*)
            (h/from :todo)
            sql/format)
  ;; => ["SELECT * FROM todo"]

  user> (let [todo-id 10]
          (-> (h/select :*)
              (h/from :todo)
              (h/where [:= :id :?])
              (sql/format :params [todo-id])))
  ;; => ["SELECT * FROM todo WHERE id = ?" 10] ;; prepared statement

これさえ押さえていれば ``todo-clj.model.todo`` ネームスペースを参考に新しい関数は書けるかもしれません。

画面のレイアウトを変更したい
----------------------------

``todo-clj.view.layout`` にレイアウトの定義があるのでそこを修正すれば全ての画面に共通のレイアウトを適用出来ます。

セッションを使いたい
--------------------

セッションを使いたい場合は次のように書くことが出来ます。

.. sourcecode:: clojure

  (defhandler login-handler :login-post [req]
    (if-let [user (find-user req)]
      (let [session (:session req) ;; リクエストマップから現在のセッションを取り出す
            updated-session (assoc session :identity user)] ;; 現在のセッションを更新する
       (-> (res/found "/home")
           (assoc :session updated-session) ;; レスポンスマップへと更新したセッションを追加する
           res/html))))

セッションに何かを追加したい場合にはレスポンスマップの ``:session`` キーに何か値を入れることで使うことが出来ます。
