(ns clojure-cqrs-playground.common.cqrs.bus.in-memory)

(defn bus-in-memory []
  {:handlers []})

(defn bus-in-memory-add [bus handler-definition]
  (update bus :handlers conj handler-definition))

(defn bus-in-memory-handle [bus effect]
  :cqrs/handle-fail)
