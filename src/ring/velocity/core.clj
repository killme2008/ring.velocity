(ns ring.velocity.core
  (:import [org.apache.velocity VelocityContext Template]
           [org.apache.velocity.app Velocity]
           [java.util Properties]
           [java.io StringWriter])
  (:use ring.middleware.content-type
        compojure.core)
  (:use [clojure.java.io :only [resource reader]]))

(def ^{:dynamic true :private true} VELOCITY-PROPS "ring-velocity.properties")

(defn- ^{:tag Properties} get-default-properties []
  (let [props (Properties.)]
    (.load props (reader (resource "default/velocity.properties")))
    props))

;;initialize velocity
(if-let [r (resource VELOCITY-PROPS)]
  (let [props (Properties.)]
    (do (.load props (reader r)) (Velocity/init props)))
  (Velocity/init (get-default-properties)))

(defn- ^{:tag VelocityContext} map->context [kvs]
  "Convert a map to a velocity context instance"
  (let [^VelocityContext ctx (VelocityContext.)]
    (doseq [[k v] kvs]
      (.put ctx (name k) v))
    ctx))

(defprotocol ^{:doc "Template render protocol"}
  TemplateRender
  (render-template [this ^String tname kvs]))

(deftype ^:private VelocityRender []
         TemplateRender
         (render-template [this tname kvs]
           (let [^Template template (Velocity/getTemplate tname)]
             (if template
               (let [^VelocityContext ctx (map->context kvs)
                     ^StringWriter sw (StringWriter.)]
                 (.merge template ctx sw)
                 (.toString sw))
               (throw (RuntimeException. (format "could not find template named `%s`" tname)))))))

(def ^:private *velocity-render (VelocityRender.))

(defn render
  [tname & kvs]
  "Render a template to string with vars:

     (render :name \"dennis\" :age 29)

  :name and :age are the variables in template.
  "
  (let [kvs (apply hash-map kvs)]
    (render-template *velocity-render tname kvs)))

(defn- add-wildcard
  "Add a wildcard to the end of a route path."
  [path]
  (str path (if (.endsWith ^String path "/") "*" "/*")))

(defn template-resources
  "A route for serving template resources in templates directory."
  [path & [options]]
  (-> (GET (add-wildcard path) {{resource-path :*} :route-params}
           (render resource-path))
      (wrap-content-type options)))
