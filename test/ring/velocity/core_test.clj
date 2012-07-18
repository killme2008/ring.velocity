(ns ring.velocity.core-test
  (:use clojure.test
        ring.velocity.core))
(deftest render-test
  (testing "test render"
    (is "hello,dennis,your age is 29." (render "test.vm" :name "dennis" :age 29))))

(deftest render-benchmark
  (testing "render benchmark"
    (time (dotimes [_ 10000] (render "test.vm" :name "dennis" :age 29)))))




