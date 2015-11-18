(ns todo-clj.server
  (:require [immutant.web :as server]))

(defonce server (atom nil))

(defn start-server [handler & {:keys [port] :or {port 3000}}]
  {:pre [(nil? @server)]}
  (when-not @server
    (reset! server (server/run handler {:port port}))))

(defn stop-server [& {:keys [port] :or {port 3000}}]
  (when @server
    (server/stop {:port port})
    (reset! server nil)))
