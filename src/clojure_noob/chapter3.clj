(ns clojure-noob.chapter3
  (:gen-class))

;; get doc to the current namespace
(clojure.core/use '[clojure.repl :only (doc)])

;; if form (then and else are only single forms)

(if true
    "this is true"
    "not really true in the literal sense")

;; to do more than one form in then/else operands, use "do"
(if false
  (do (println "TRUE")
      "this is true")
  (do (println "FALSE")
      "not really true in the literal sense"))

;; "when" is a combination of "if" and "do" without an "else". Always returns nil

(when true
  (println "This is when")
  "really true")

;; test for nil
(nil? 1)
(nil? nil)

;; NOTE: only nil and false are evaluated as logically false. Everything else is true
;; Because of this, logical operators can do weird things.

(or :test1 :test2) ;; returns test1, the first true value
(and :test1 :test2);; returns test2, the first true value
(or false nil) ;; because it didn't find anything true returns nil, the last false value
(and :test false nil) ;; returns false, the first false value

;; binding names to variables (vs assigning names, standard lingo in programing languages
;; that allow multiple assignement (mutability)

;; classic approach - adding to the already existing variable
(def severity :mild)
(def error-message "OH GOD! IT'S A DISASTER! WE'RE ")
(if (= severity :mild)
  (def error-message (str error-message "MILDLY INCONVENIENCED!"))
  (def error-message (str error-message "DOOOOOOOMED!")))
(println error-message)

;; clojure way - make a function to get the error-message when needed
(defn error-message
  [severity]
  (str "OH GOD! IT'S A DISASTER! WE'RE "
       (if (= severity :mild)
         "MILDLY INCONVENIENCED!"
         "DOOOOOOOMED!")))

(error-message :notmild)

(def myname "Slim Shady")  ;; define variable name
(str "Hi my name is: " myname)  ;; concatenate strings

;; maps (like Python dictionaries)

;; basic map - associating values to keywords
{:firstname "Milan", 
 :lastname "Raicevic",
 :age 35}   

;; associate function to string
{"string-key" +}

;; nested maps
{:person1 {:firstname "Milan", 
           :lastname "Raicevic",
           :age 35}}

;; also can use function hash-map to set up maps
(hash-map :a 1 :b 2)

;; use get function to get the value from a map
(get {:a 1 :b 2} :b)

(get {:a 1 :b 2} :c) ;; returns nil
(get {:a 1 :b 2} :c "default") ;; alternatively, set the default return value

(get-in {:a 1 :b {:c "nesting"}} [:b :c])  ;; function get-in can get values from nested maps

({:a 1 :b 2} :b) ;; simplest form, use map as a function

;; keywords

(:a {:a 1 :b 2 :c 3}) ;; use keywords as functions to get elements from maps
(:d {:a 1 :b 2 :c 3} "default") ;; can have defaults

;; vectors

(get [3 2 1] 0)  ;; use get to get i-th element of a vector
(get ["a" {:a 1 :b 2} 10] 2)  ;; vectors can be a mix of type (like Python lists)

(vector "this" "is" "a" "vector") ;; use vector function to create vectors
(conj [1 2 3] 4) ;; conj to add elements to vectors

;; lists - similar to vectors, but 'get' does not work

'(1 2 3 4)  ;; a list
(nth `(1 2 3 4) 2) ;; use nth to fetch elements from list at given index

(list 1 "a" [2 3]) ;; 'list' to generate a list. Lists can also have mixed types 

(conj '(1 2 3) 4) ;; elements are added to the BEGINNING of the list

;; Sets - collections of unique values
;; Two types: hash sets and sorted sets

#{1 "a" "test"} ;; hash set definition

(hash-set 1 2 1 2)  ;; will make the set unique

(conj #{1 2} 2) ;; adding an element which already exists will be ignored (no error)

(set [1 2 3 3 4 2]) ;; use 'set' to create a hash set from already existing lists/vectors

;; several ways to test if an element is in the set
(contains? #{:a :b} :a)
(contains? #{:a :b} :c)

(get #{:a :b} :a)
(get #{:a :b} :c)

(get #{nil} nil) ;; could be confusing when fetching nil from a set.
(get #{nil} 10) 

(contains? #{nil} nil) ;; better to use contains when just testing for membership

;;
;; Functions
;;

(or + -)  ;; functions are equal to any other variable
((or + -) 1 2 3)
((and (= 1 1) +) 1 2 3)

;; order of execution - all arguments are evaluated recursively before passing them to the function
(+ (inc 199) (/ 100 (- 7 2)))
;; (+ 200 (/ 100 (- 7 2)))
;; (+ 200 (/ 100 5))
;; (+ 200 20)
;; 220

;; defining functions
(defn test-function                   ;; defn name of the function
  "This is a doc string"              ;; documentation string (optional)
  [name]                              ;; parameters in [] brackets
  (str "This is jut a test: " name))  ;; function body

;; number of parameters is called 'arity' 

;; 0-arity function
(defn zero-ar
  []
  (str "No parameters!"))

;; 2-arity function
(defn two-ar
  [x y]
  (str "Two params: " x y))

;; functions can have different behavior depending on number of parameters given
;; (effectively operator overloading)

(defn multi-ar
  ([one]
   (str "Just one: " one))
  ([one two]
   (str "Two this time: " one two))
  ([one two three]
   (str "Max three: " one two three)))

;; use arity overloading to provide default parameters
(defn karate-chop
  ([name chop-type]
   (str name " will " chop-type " chop you!"))
  ([name] 
   (karate-chop name "karate")))

(karate-chop "I" "ass")

;; variable arity paramter - &. All extra parameters supplied as a vector
(defn send-greeting
  [person]
  (str "Whazzup " person))

(defn multi-greeting
  [& persons]     ;; variable arity !
  (map send-greeting persons))
(multi-greeting "mama" "tata" "baka")

;; variable arity vector comes after required parameters
(defn multi-greeting-plus-one
  [name & persons]     ;; variable arity !
  (conj (map send-greeting persons) (str "The name is " name))) ;; appended a string to vector from map
(multi-greeting-plus-one "ja"  "mama" "tata" "baka")

;; Destructuring - extracting individual parameters from passed in collections 

(defn first-element
  [[first-el]]  ;; [[]] means that a parameter will be a vector
  first-el)
(first-element [1 2 3 4]) ;; function returns 1

;; this can be combined with variable arity parameters
(defn chooser
  [[first-el second-el & other-el]]
  (println (str "Value 1: " first-el))
  (println (str "Value 2: " second-el))
  (println (str "All others: " 
                (clojure.string/join ", " other-el))))
(chooser [1 2 3 4 5 6])

;; maps can also be desrtuctured
(defn announce-treasure-location 
  [{lat :lat lng :lon}]
  (println (str "Latitude: " lat))
  (println (str "Longitude: " lng)))
(announce-treasure-location {:lat 23.4 :lon 12.2})

;; instead of renaming map keys, we can just break them out of the map:
(defn announce-treasure-location 
  [{:keys [lat lon]}]
  (println (str "Latitude: " lat))
  (println (str "Longitude: " lon)))
(announce-treasure-location {:lat 23.4 :lon 12.2})

;; :as keyword allow access to the input map directly
(defn steer-ship
  [treasure-map]
  (println (str "Onwards! " 
                (get treasure-map :lat)
                (get treasure-map :lon))))
(defn announce-treasure-location-and-move 
  [{:keys [lat lon] :as treasure-loc}]
  (println (str "Latitude: " lat))
  (println (str "Longitude: " lon))
  (steer-ship treasure-loc))
(announce-treasure-location-and-move {:lat 23.4 :lon 12.2})

;; function body 

;; functions automatically return the last evaluated form

(defn a-function 
  []
  (+ 1 2 3 4)
  30
  "return this")

;; Anonymous Functions
;; Two ways to define anonymous functions:

;; 1) (fn [param-list] function body)

(println 
 (map (fn [x] (str "What is x? " x))
      [1 2 3]))

;; give an anonymous function a name
(def anon-func (fn [x] (* x x)))
(anon-func 5)

;; 2) #(function %) where % is a parameter. This comes from 'reader macros' (Chapter 7)
(#(* % 3) 8)

;;
;; Functions returned by other functions are closures - they have access to parent function parameters
;;

(defn custom-increment 
  [inc-by]
  #(+ % inc-by))

(def inc3 (custom-increment 3))

(inc3 7)

;;
;; Chapter 3 - pulling it all together
;;
;; covers: let, loops and regular expressions
;;


;; let - bind name to value within an expression
;;       1) naming gives clarity
;;       2) naming allows reusing evaluated expressions
(let [x 3]
  x)

;; let introduces new scope:
(def x 0)
(let [x 1]
  (print x))

;; let accepts rest parameters
(def array [1 2 3 4])
(let [[x & y] array]    ;; here array is destructured to first element in x, rest in y
  [x y])

;; into - add elements from one vector to another vector
(into [4 5] [1 2 3])

;; loop / recur - for loops in Clojure
(loop [iteration 0]   ;; loop entry point with initial value
  (println (str "Iter: " iteration))
  (if (> iteration 3)
    (println "Goodbye")
    (recur (inc iteration))))

;; same can be implemented without a loop
(defn rec-printer
  ([]               ;; 0 arity
   (rec-printer 0))
  ([iteration]      ;; 1 arity
   (println iteration)
   (if (> iteration 3)
     (println "Goodbye!")
     (rec-printer (inc iteration)))))
(rec-printer)

;; loop can have multiple parameters
(loop [increment 0
       values []]
  (println increment)
  (println values)
  (if (> increment 3)
    (println "done")
    (recur (inc increment)
           (conj values increment))))



;; vector of maps. Only left parts of the body there, need to add right ones
(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])


;; Finished Hobbit routine
(defn matching-part
  "Replace string mention of \"left-\" with \"right-\""
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(matching-part {:name "left-test" :size 2})


(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts   ;; 2 loop parameters: remaining-asym-parts and final-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)  ;; if all the body parts have been processed, finish the loop
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]  ;; split the unprocessed body parts into first element and rest
        (recur remaining                ;; recur to operate on the leftover parts
               (into final-body-parts   ;; add processed part to the final vector
                     (set [part (matching-part part)])))))))   ;; set - makes a unique set from vector

(map 
 println
 (symmetrize-body-parts asym-hobbit-body-parts)) 



;; alternative way to symmetrise the hobbit body - reduce
(reduce + [1 2 3 4])
(reduce + 15 [1 2 3 4]) ;; reduce with optional initial value

;; symmetrizer with reduce
(defn better-symmetrize-body-parts
  "Epects a seq of maps with :name and :size. Returns another seq of same maps"
  [asym-body-parts]
  (reduce 
   (fn [final-body-parts part]
     (into final-body-parts (set [part (matching-part part)])))
   [] ;; initial value
   asym-body-parts))
(better-symmetrize-body-parts asym-hobbit-body-parts)

;; Hit the hobbit - randomly choose which body part is hit
(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)  ;; add right side of the body 
        body-part-size-sum (reduce + (map :size sym-parts))       ;; compute total size of the body
        target (rand body-part-size-sum)]                         ;; choose random number to hit
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]    ;; initial accumulated size of body parts
      (if (> accumulated-size target)        ;; body part is hit when the accumulated sizes of parts are bigger than chosen random number
        part
        (recur remaining (+ accumulated-size (:size (first remaining)))))))) ;; if no hit, get next part

(hit asym-hobbit-body-parts) ;; hit the hobbit

