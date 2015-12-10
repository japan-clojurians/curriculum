==========
 分配束縛
==========

ある関数の結果がリストだったりして、その中の先頭の要素だけを使いたいとか、マップに入っているキーの幾つかだけを使いたいということは往々にしてよくあります。これを愚直に書くと次にようになります。

.. sourcecode:: clojure

  user> (let [ls (range 10)
              fst (first ls)]
          (println fst))
  0
  nil
  user> (let [m {:name "Scott" :age 10}
              n (:name m)]
          (println n))
  Scott
  nil

少々冗長に感じますよね。これを短く書けるのが分配束縛です。以下に簡単に良く使う分配束縛を紹介します。

.. sourcecode:: clojure

  user> (let [[a & more :as org] (range 10)] ;; a が先頭だけを取り、 & を挟むことで残りを表現出来ます。 :as org は元のリストを org として参照することが出来ます。
          (println org)
          (println a)
          (println more))
  (0 1 2 3 4 5 6 7 8 9)
  0
  (1 2 3 4 5 6 7 8 9)
  nil
  user> (let [{:keys [name] :as m} {:name "Alex" :age 30}] ;; マップの場合は :keys の後にベクタでキーを指定することで取り出せます。
          (println m)
          (println name))
  {:name Alex, :age 30}
  Alex
  nil

  user> (let [{n :name} {:name "Alex" :age 30}] ;; このように指定することも可能
          (println n))
  Alex
  nil


また分配束縛は ``let`` だけでなく、関数の引数部でも使うことが出来ます。

.. sourcecode:: clojure

  user> (defn make-user-info [name age & {:as opts}]
          (merge {:name name :age age}
                 opts))
  #'user/make-user-info
  user> (make-user-info "Murphy" 24 :country "America" :job "Musician")
  {:name "Murphy", :age 24, :country "America", :job "Musician"}
