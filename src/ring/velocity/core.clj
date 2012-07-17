(ns ring.velocity.core
  (:import [org.apache.velocity VelocityContext Template]
           [org.apache.velocity.app Velocity]
           [java.util Properties]
           [java.io StringWriter])
  (:use [clojure.java.io :only [resource reader]]))

(def ^{:dynamic true :private true} VELOCITY-PROPS "ring-velocity.properties")

(defn- get-default-properties []
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

(defn render
  [tname & kvs]
  (let [^Template template (Velocity/getTemplate tname)]
    (if template
      (let [^VelocityContext ctx (map->context (apply hash-map kvs))
            ^StringWriter sw (StringWriter.)]
        (.merge template ctx sw)
        (.toString sw))
      (throw (RuntimeException. (format "could not find template named `%s`" tname))))))
