(ns clojure-cqrs-playground.playground.association.association-saga)

; commands and events
(def do-simple-stuff {:cqrs/effect-type :cqrs/command
                      :cqrs/name        "do-simple-stuff"
                      :cqrs/data        {:field-id-1 "foo"
                                         :field-id-2 "bar"}})

(def do-sync-stuff {:cqrs/effect-type :cqrs/command
                    :cqrs/name        "do-sync-stuff"
                    :cqrs/data        {:field-id-1 "foo"
                                       :field-id-2 "bar"}})

(def simple-stuff-done {:cqrs/effect-type :cqrs/event
                        :cqrs/name        "simple-stuff-done"})

(def more-stuff-done {:cqrs/effect-type :cqrs/event
                      :cqrs/name        "more-stuff-done"})


; saga handlers
(defn run-simple-stuff [{:keys [saga/state saga/associations cqrs/event]}]
  (printf event)
  {:saga/state        (update state :stage (constantly :step-1))
   :saga/associations (conj associations (:field-id-2 event))
   })

(defn run-more-stuff [{:keys [saga/state]}]
  {:saga/state   (update state :stage (constantly :step-2))
   :cqrs/effects [more-stuff-done]})


; saga definition
(def a-saga-with-associations
  {:saga/initial-state {:stage :step-0}
   :saga/handlers      [
                        {:saga/on do-simple-stuff :saga/do run-simple-stuff}
                        {:saga/on simple-stuff-done :saga/do run-more-stuff}
                        ]}
  )

