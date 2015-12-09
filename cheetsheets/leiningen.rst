==========================
 Leiningen のチートシート
==========================

Clojure は他の言語と違い Java の Jar として配布されているためコマンドがありません( Ruby の ``ruby`` とか、 Groovy の ``groovy`` みたいな)。では、どうやって REPL を起動したり Jar や War を作るのかというと `Leiningen <http://leiningen.org/>`_ というツールを使います [#]_ 。このチートシートでは Leiningen に関することをまとめているので参考にしてください。

.. [#] 厳密に言えば REPL の起動くらいなら Clojure の Jar を使えば出来ます。

インストール
============

Mac / \*nix / \*bsd な環境なら以下のようにコマンドを実行することでインストール出来ます。

.. sourcecode:: shell

  $ wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
  $ mv lein ~/bin
  $ chmod u+x ~/bin/lein
  $ lein

Windows の方はインストーラーでインストールすると簡単のようです。

http://leiningen-win-installer.djpowell.net/

REPL
====

以下のコマンドで REPL を起動出来ます。

.. sourcecode:: shell

  $ lein repl

コマンドを実行したディレクトリに ``project.clj`` がある場合はその設定を読みます。

グローバルな設定
================

全てのプロジェクトで読み込んで欲しい設定(例えば開発時に使うツールなど)がある場合は ``~/.lein/profiles.clj`` に記述します。

.. sourcecode:: clojure

  {:repl {:plugins [[cider/cider-nrepl "0.10.0"]
                    [refactor-nrepl "2.0.0-SNAPSHOT"]
                    [lein-try "0.4.3"]]
          :dependencies [[org.clojure/tools.nrepl "0.2.12"]]}}

ネット上のサンプルでは ``:repl`` の部分が ``:user`` となっていることが多いですが、 REPL を使っているときだけ使いたいプラグインやライブラリは ``:repl`` に書いておきましょう。

アプリケーションを新規で作成する
================================

次のようにターミナルで実行するとアプリケーションを作成することが出来ます。

.. sourcecode:: shell

  $ lein new my-app
  # or
  $ lein new template-name my-app

プロジェクトごとの設定
======================

``lein new`` コマンドを使って作成したプロジェクトではデフォルトで以下のような ``project.clj`` というファイルが存在します。

.. sourcecode:: clojure

  (defproject my-app "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.7.0"]])

テンプレートを使用してプロジェクトを作成した場合はこのファイルに最初から色々と書いてあります。

ライブラリの依存関係を追加する
------------------------------

``:dependencies`` のベクタに追加したいライブラリを追加します。

.. sourcecode:: clojure

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring "1.4.0"]] ;; 追加した

この情報は追加したいライブラリの README または `Clojars <https://clojars.org/>`_ や `Maven Repository <http://mvnrepository.com/>`_ などに記述してあるのでコピーしましょう。

ディレクトリ構成を変更する
--------------------------

デフォルトで次のディレクトリは設定しなくてもプロジェクト内で意味があるディレクトリとして読み込まれます。

::

  src -> ソースディレクトリ
  resources -> リソースディレクトリ
  test -> テストディレクトリ

以下の設定を追加することで新しいディレクトリをソースディレクトリとして扱ったりすることが出来ます。

.. sourcecode:: clojure

  :source-paths ["src" "new-src"]
  :resource-paths ["resources" "new-resources"]
  :test-paths ["test" "new-test"]

開発/テスト時だけ使うプロジェクト用の設定を書く
-----------------------------------------------

Leiningen にはプロファイルというものがありこれを使うことで開発時のみに適用する設定を書くことが出来ます。

プロファイルは ``:profiles`` キーに対して設定します。

.. sourcecode:: clojure

  :profiles
  {:dev
   {:source-paths ["env/dev/clj"]
    :dependencies [[ring/ring-devel "1.4.0"] ;; 開発用ミドルウェア
                   [prone "0.8.2"] ;; Better Error
                   [com.h2database/h2 "1.4.190"]]
    :env {:dev true
          :db {:classname "org.h2.Driver"
               :subprotocol "h2:file"
               :subname "./db/todo-clj"}}}}

dev プロファイルは通常 ``lein repl`` を使うと自動的に読み込まれますが、他のプロファイルを設定して使う場合は ``lein with-profile foo-profile repl`` のように実行します。
また幾つかのプロファイルはタスクと直接紐付いているものもあります。 ``:repl`` や ``:uberjar`` などがそれにあたり、これらはそのタスクが実行されるときに自動的に読み込まれます。

その他
------

紹介した以外にも ``project.clj`` の設定項目は沢山あるので、もし興味があれば以下を参照してください。

https://github.com/technomancy/leiningen/blob/master/sample.project.clj

依存関係の解決
==============

``project.clj`` の ``:dependencies`` に新しい依存関係を追加した場合には次のコマンドを実行することで依存関係を解決することが出来ます。

.. sourcecode:: clojure

  $ lein deps

明示的にこのコマンドを実行しなくても ``lein repl`` などとすると自動的にこのコマンドが REPL 起動前に実行されます(余談ですが、 ``lein install`` はプロジェクトをローカルのリポジトリにインストールするもので依存関係を解決するものではありません)。

またプロジェクトの依存関係が複雑になってきて、同じライブラリの違うバージョンを読み込んでいるというようなときには次のコマンドを使って解決策を探します。

.. sourcecode:: clojure

  $ lein deps :tree

このコマンドで特定のライブラリがどのバージョンで読み込まれているかなどが簡単に分かります。
