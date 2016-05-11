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
;; That way, every sequence is converted to a list. This has consequences for map types:
(seq {:name "ja" :work "yes"})  ;; this becomes a list of key/value vectors!!!

;; to convert list of vectors back to a map, use into
(into {} (seq {:a 1 :b 2})) ;; this is a new map

;;
;; seq library functions
;;

;; map

(map inc [1 2 3])  ;; the standard usage
(map str ["a" "b" "c"] [1 2 3]) ;; mapping multiple sequences
                                ;; 1st sequence is the first argument to str, 2nd sequence second etc.

(defn hash-format
  [data1 data2]
  {:data1 data1
   :data2 data2})
(map hash-format [1 4 6] [9 10 11])  ;; combine two vectors into one map

;; use map to run multiple functions on sequences
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

;; take - extract n first element from a sequences
;; drop - remove n first element from a sequences
(take 3 [1 2 3 4 5 6 7])
(drop 3 [1 2 3 4 5 6 7])

;; take-while and drop-while can select elements using a provided selection function
;; these two are limited versions of filter. Their advantage is they don't need to check the whole 
;; sequence, while filter does. However, their use is more limited

(def food-journal
  [{:month 1 :day 1 :human 5.3 :critter 2.3}
   {:month 1 :day 2 :human 5.1 :critter 2.0}
   {:month 2 :day 1 :human 4.9 :critter 2.1}
   {:month 2 :day 2 :human 5.0 :critter 2.5}
   {:month 3 :day 1 :human 4.2 :critter 3.3}
   {:month 3 :day 2 :human 4.0 :critter 3.8}
   {:month 4 :day 1 :human 3.7 :critter 3.9}
   {:month 4 :day 2 :human 3.7 :critter 3.6}])

(take-while #(< (:month %) 3) food-journal)
(take-while #(> (:month %) 3) food-journal) ;; does not work because it only returns elements from the begining

(take-while #(< (:month %) 4) 
            (drop-while #(< (:month %) 2) food-journal)) ;; can combine for more complicated quaries


;; filter and some 

;; filter - return all elements that test true using a selection function
(filter #( < (:human %) 4) food-journal)

;; some - check if any elements in sequence are true for given selection function
(some #(> (:critter %) 3) food-journal)
(some #(> (:critter %) 5) food-journal)


;; sort and sort-by

;; sort - base sort in ascending order
(sort [3 1 2])
(sort ["r" "a" "c"])

;; sort-by - provide your own key function
(sort ["cc" "b" "aaa"]) ;; alphabetical order
(sort-by count ["cc" "b" "aaa"]) ;; order by string length


;; concat - append sequences
(concat [1 2] [3 4])


;;
;; Lazy sequences - sequences whole members are not computed before they are needed
;;
