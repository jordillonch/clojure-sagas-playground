(ns clojure-cqrs-playground.core
  (:require [clojure.spec.alpha :as s])
  (:require [clojure.spec.test.alpha :as stest]))

(defn foo
  "I don't do a whole lot."
  [x y]
  (println x "Hello, World!" y))
