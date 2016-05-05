(ns clojure-noob.chapter3
  (:gen-class))

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

