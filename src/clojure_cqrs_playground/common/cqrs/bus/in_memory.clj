(ns clojure-cqrs-playground.common.cqrs.bus.in-memory)

(defn bus-in-memory []
  {:handlers []})

(defn bus-in-memory-add [bus handler-definition]
  (update bus :handlers conj handler-definition))

(defn bus-in-memory-handle-command [bus command]
  (-> (filter #(= (:cqrs/name command) (-> % :cqrs/on-command :cqrs/name)) (:handlers bus))
      (first)
      (:cqrs/handler)
      (apply [command])
      )
  )

(defn bus-in-memory-handle-event [bus event]
  (->> (filter #(= (:cqrs/name event) (-> % :cqrs/on-event :cqrs/name)) (:handlers bus))
       (map #(-> %
                 (:cqrs/handler)
                 (apply [event])))
       )
  :cqrs/handle-ok
  )
