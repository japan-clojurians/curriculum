(defproject todo-clj "0.1.0"
  :description "Clojure ワークショップの課題用 TODO アプリ"
  :url "http://example.com/FIXME"
  :license {:name "Creative Commons Attribution 4.0 International License"
            :url "http://creativecommons.org/licenses/by/4.0/deed.en_US"}
  :source-paths ["src" "src-cljc"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [ring/ring-core "1.4.0"] ;; Ring
                 [org.immutant/web "2.1.1"] ;; アプリケーションサーバー
                 [bidi "1.21.1"] ;; ルーティング
                 [hiccup "1.0.5"] ;; テンプレートエンジン
                 [org.clojure/java.jdbc "0.4.2"] ;; JDBC
                 [honeysql "0.6.2"] ;; SQL DSL
                 [environ "1.0.1"] ;; 環境変数管理
                 [com.taoensso/timbre "4.1.4"] ;; ロギング

                 ;; ユーティリティ
                 [potemkin "0.4.1"]
                 [ring/ring-defaults "0.1.5"]
                 [metosin/ring-http-response "0.6.5"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :plugins [[lein-environ "1.0.1"]]

  :profiles
  {:uberjar
   {:dependencies [[org.postgresql/postgresql "9.4-1205-jdbc42"]]}

   :dev
   {:dependencies [[ring/ring-devel "1.4.0"] ;; 開発用ミドルウェア
                   [prone "0.8.2"] ;; Better Error
                   [org.xerial/sqlite-jdbc "3.8.11.2"]]
    :env {:dev true
          :db {:subprotocol "sqlite"
               :subname "todo-clj.db"}}}})
