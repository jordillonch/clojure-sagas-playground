(ns clojure-cqrs-playground.common.cqrs.bus.in-memory)

(defn bus-in-memory []
  {:handlers []})

(defn add [bus handler-definition]
  (update bus :handlers conj handler-definition))

(defn handle-command [bus command]
  (-> (filter #(= (:cqrs/name command) (-> % :cqrs/on-command :cqrs/name)) (:handlers bus))
      (first)
      (:cqrs/handler)
      (apply [{:cqrs/command command}])))

(defn handle-event [bus event]
  (->> (filter #(= (:cqrs/name event) (-> % :cqrs/on-event :cqrs/name)) (:handlers bus))
       (map #(-> %
                 (:cqrs/handler)
                 (apply [{:cqrs/event event}]))))
  :cqrs/handle-ok)
