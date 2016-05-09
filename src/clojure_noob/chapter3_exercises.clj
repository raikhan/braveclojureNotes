(ns clojure-noob.chapter3_exercises
  (:gen-class))

;; get doc to the current namespace
(clojure.core/use '[clojure.repl :only (doc)])

;; write a map function that returns a set
(defn mapset
  [func data]
  (set (map func data)))
(mapset inc [1 1 2 2])
