(ns clojure-noob.chapter4
  (:gen-class))

;; get doc to the current namespace
(clojure.core/use '[clojure.repl :only (doc)])

;; sequences - abstract collections that implement 'first', 'rest' and 'cons' functions
;; lists, vectors, sets and maps are all sequences

(defn add-title
  [topic]
  (str topic " in a title"))
(map add-title ["mama" "tata"]) ;; vector
(map add-title '("word" "another word")) ;; list

;; Indirection: first, rest and cons funcions do polymorphic dispatching - call appropriate functions 
;; depending on the input type

;; internally, Clojure calls seq before operating on types that use seq functions (first, rest, cons)
;; That way, every collection is converted to a list. This has consequences for map types:
(seq {:name "ja" :work "yes"})  ;; this becomes a list of key/value vectors!!!

;; to convert list of vectors back to a map, use into
(into {} (seq {:a 1 :b 2})) ;; this is a new map

;;
;; seq library functions
;;

;; map

(map inc [1 2 3])  ;; the standard usage
(map str ["a" "b" "c"] [1 2 3]) ;; mapping multiple collections
                                ;; 1st collection is the first argument to str, 2nd collection second etc.

(defn hash-format
  [data1 data2]
  {:data1 data1
   :data2 data2})
(map hash-format [1 4 6] [9 10 11])  ;; combine two vectors into one map

;; use map to run multiple functions on collections
(def sum #(reduce + %))
(def avg #(/ (sum %) (count %)))
(defn stats
  [numbers]
  (map #(% numbers) [sum count avg]))  ;; apply each function (sum, count, avg) to numbers

(stats [1 3 6])
(stats [2 55 6 23 123])

;; common usage, get values for a given key out of a list of maps
(def identities
  [{:alias "Batman" :real "Bruce Wayne"}
   {:alias "Hawkeye" :real "Clint Burton"}])
(map :real identities)


;; reduce

;; use reduce to filter maps
(reduce (fn [new-map [key val]]
          (if (> val 4)
            (assoc new-map key val)
            new-map))
        {}            ;; initial value (optional). If not given, the return is a vector!!!
        {:one 4.1
         :two 3.0})   ;; value :two removed from output map
;; Exercise: implement map using reduce


;; take, drop, take-while and drop-while


