(ns clojure-cqrs-playground.playground.simple.simple-saga)

; commands and events
(def do-simple-stuff {:cqrs/effect-type :cqrs/command
                      :cqrs/name        "do-simple-stuff"
                      :cqrs/data        {:field1 "foo"
                                         :field2 "bar"}})

(def simple-stuff-done {:cqrs/effect-type :cqrs/event
                        :cqrs/name        "simple-stuff-done"})

(def more-stuff-done {:cqrs/effect-type :cqrs/event
                      :cqrs/name        "more-stuff-done"})

(def all-stuff-done {:cqrs/effect-type :cqrs/event
                     :cqrs/name        "all-stuff-done"})


; saga handlers
(defn run-simple-stuff [{:keys [saga/state cqrs/event some-dependency other-dependency]}]
  (printf event)
  (printf some-dependency)
  (printf other-dependency)
  {:saga/state  (update state :stage (constantly :step-1))
   :cqrs/events [simple-stuff-done]})

(defn run-more-stuff [{:keys [saga/state]}]
  {:saga/state  (update state :stage (constantly :step-2))
   :cqrs/events [more-stuff-done]})

(defn run-extra-stuff-a [{:keys [saga/state]}]
  {:saga/state  (update state :stage (constantly :step-3))
   :cqrs/events [more-stuff-done]})

(defn run-extra-stuff-b [{:keys [saga/state]}]
  {:saga/state  (update state :stage (constantly :all-steps-done))
   :cqrs/events [all-stuff-done]})


; saga definition
(def a-simple-saga
  {:saga/initial-state {:stage :step-0}
   :saga/handlers      [
                        {:saga/on do-simple-stuff :saga/do run-simple-stuff}
                        {:saga/on simple-stuff-done :saga/do run-more-stuff}
                        {:saga/on more-stuff-done :saga/do run-extra-stuff-a :saga/when #(= (:stage %) :step-2)}
                        {:saga/on more-stuff-done :saga/do run-extra-stuff-b :saga/when #(= (:stage %) :step-3)}
                        ]}
  )

