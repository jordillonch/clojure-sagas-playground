(ns clojure-cqrs-playground.common.cqrs.bus.in-memory-test
  (:require [clojure.test :refer :all])
  (:require [clojure-cqrs-playground.common.cqrs.bus.in-memory :refer :all]))


(def a-command-1 {:cqrs/effect-type :cqrs/command
                  :cqrs/name        "a command 1"
                  :cqrs/data        {:field1 "foo"
                                     :field2 "bar"}})

(def a-command-2 {:cqrs/effect-type :cqrs/command
                  :cqrs/name        "a command 2"
                  :cqrs/data        {:field1 "foo"
                                     :field2 "bar"}})

(def an-event-1 {:cqrs/effect-type :cqrs/event
                 :cqrs/name        "a event 1"
                 :cqrs/data        {:field1 "foo"
                                    :field2 "bar"}})

(def an-event-2 {:cqrs/effect-type :cqrs/event
                 :cqrs/name        "a event 2"
                 :cqrs/data        {:field1 "foo"
                                    :field2 "bar"}})

(defn a-command-handler-1 [{:keys [cqrs/command]}]
  :cqrs/handle-ok)

(defn a-command-handler-2 [{:keys [cqrs/command]}]
  :cqrs/handle-ok)

(defn an-event-handler-1 [{:keys [cqrs/event]}]
  :cqrs/handle-ok)

(defn an-event-handler-2 [{:keys [cqrs/event]}]
  :cqrs/handle-ok)

(def a-command-handler-definition-1 {
                                     :cqrs/handler    a-command-handler-1
                                     :cqrs/on-command a-command-1
                                     })

(def a-command-handler-definition-2 {
                                     :cqrs/handler    a-command-handler-2
                                     :cqrs/on-command a-command-2
                                     })

(def an-event-handler-definition-1 {
                                    :cqrs/handler  an-event-handler-1
                                    :cqrs/on-event an-event-1
                                    })

(def an-event-handler-definition-2 {
                                    :cqrs/handler  an-event-handler-2
                                    :cqrs/on-event an-event-2
                                    })

(deftest bus-in-memory-test
  (testing "it should register command handlers"
    (is (= (-> (bus-in-memory)
               (bus-in-memory-add a-command-handler-definition-1))
           {:handlers [a-command-handler-definition-1]}))
    (is (= (-> (bus-in-memory)
               (bus-in-memory-add a-command-handler-definition-1)
               (bus-in-memory-add a-command-handler-definition-2))
           {:handlers [a-command-handler-definition-1 a-command-handler-definition-2]}))
    )

  (testing "it should register event handlers"
    (is (= (-> (bus-in-memory)
               (bus-in-memory-add an-event-handler-definition-1))
           {:handlers [an-event-handler-definition-1]}))
    (is (= (-> (bus-in-memory)
               (bus-in-memory-add an-event-handler-definition-1)
               (bus-in-memory-add an-event-handler-definition-2))
           {:handlers [an-event-handler-definition-1 an-event-handler-definition-2]}))
    )

  (testing "it should register command and event handlers"
    (is (= (-> (bus-in-memory)
               (bus-in-memory-add a-command-handler-definition-1)
               (bus-in-memory-add an-event-handler-definition-1)
               (bus-in-memory-add a-command-handler-definition-2)
               (bus-in-memory-add an-event-handler-definition-2))
           {:handlers [a-command-handler-definition-1
                       an-event-handler-definition-1
                       a-command-handler-definition-2
                       an-event-handler-definition-2]}))
    )

  (testing "it should handle a command")
  (testing "it should handle an event")
  )
