(ns ring.velocity.core-test
  (:use clojure.test
        ring.velocity.core))
(deftest render-test
  (testing "test render"
    (is "hello,dennis" (render "test.vm" :name "dennis"))))


