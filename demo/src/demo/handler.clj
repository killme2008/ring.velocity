(ns demo.handler
  (:use compojure.core)
  (:use [ring.velocity.core :only [render template-resources]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] (render "index.vm"))
  (POST "/hello" [name] (render "index.vm" :name name))
  (template-resources "/vm")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
