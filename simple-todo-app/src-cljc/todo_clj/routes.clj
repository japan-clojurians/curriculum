(ns todo-clj.routes)

(def main
  ["/" {"" :home
        "todo" {"" :todo-index
                ["/" [#"\d+" :id]] :todo-show}
        true :not-found}])
