(ns clojure-cqrs-playground.common.cqrs.saga.cqrs-schema
  (:require [clojure.spec.alpha :as s]))

(s/def :cqrs/effect-type keyword?)
(s/def :cqrs/name string?)
(s/def :cqrs/data map?)

(s/def :cqrs/command (s/and
                       (s/keys :req [:cqrs/effect-type :cqrs/name])
                       #(= (:cqrs/effect-type %) :cqrs/command)))

(s/def :cqrs/event (s/and
                     (s/keys :req [:cqrs/effect-type :cqrs/name])
                     #(= (:cqrs/effect-type %) :cqrs/event)))
