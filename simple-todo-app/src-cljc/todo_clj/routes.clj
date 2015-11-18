(ns todo-clj.routes)

(def main
  ["/" {"" :home
        "todo" {"" :todo-index}
        true :not-found}])
