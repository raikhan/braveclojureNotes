(ns clojure-noob.chapter5
  (:gen-class))

;; pure functions have two properties:
;;
;; 1. referential transparency - given same input, they always return same output
;; 2. no side effects

;;
;; Clojure has ONLY imutable values
;;

;; define a name
(def some_name "My name")
some_name

;; we can redefine the name
(def some_name "My name again")
some_name

;; but the name cannot change in another scope
(let [some_name "Name in another scope"]
  some_name)
some_name

;; 
;; recursion is the way around imutability
;;

;; recursive sum function: each recursive call is its own scope, so the variables change values without becoming mutable
(defn sum
  ([vals] (sum vals 0)) ;; 1-arity - default mode, initial value is 0
  ([vals accumulating-total] ;; 2-arity - for recursive calls
   (if (empty? vals)
     accumulating-total
     (sum (rest vals) (+ (first vals) accumulating-total)))))
(sum [1 2 10])
(sum [1 2] 10)

;; IMPORTANT: Clojure recursion is not tail call optimized (http://en.wikipedia.org/ wiki/ Tail_call). 
;; When writing recursive functions always use "recur" for recursive calls
(defn sum
  ([vals] (sum vals 0)) 
  ([vals accumulating-total]
   (if (empty? vals)
     accumulating-total
     (recur (rest vals) (+ (first vals) accumulating-total)))))    ;; RECUR call here
(sum [1 2 10])
(sum [1 2] 10)

;;
;; Composing pure functions - making a new function that applies multiple other functions
;; 
(defn inc-prod
  [a b]
  (inc (* a b)))
(inc-prod 2 3)

;; This is so commonly done that Clojure has a function to do it - comp
((comp inc *) 2 3)

;; comp makes it clear that a function composes other functions and makes such functions more succint
(def character 
  {:name "Ragnar Lothbrock"
   :attributes {:int 18
                :str 16
                :cha 20}
   })
;; get attributes for character
((comp :int :attributes) character)
((comp :cha :attributes) character)
((comp :attributes :cha) character)  ;; order matters

(def c-int (comp :int :attributes))
(c-int character)

;; What if a function in the comp chain needs more arguments?
;; Wrap it in an anonymous function

;; computing the number of spell slots of a character, given their intelligence
(defn spell-slots
  [char]
  (int (inc (/ (c-int char) 2))))
(spell-slots character)

;; same with comp 
((comp int inc #(/ % 2) c-int) character) ;; functions are applied from right to left !

;; Exercise - implement comp by hand (for arbitrary number of functions)
(defn my-comp2 ;; 2 arguments
  [f g]
  (fn [& args]
    (f (apply g args))))
((my-comp2 inc inc ) 2)

(defn my-comp
  [funcs]
  (reduce (fn [f g] (fn [& args] (f (apply g args))))
          identity
          funcs)
  )
((my-comp  [inc inc inc #(+ % 10)]) 2)

;;
;; memoize - remember the result of an application of a pure function
;;
(def memo-inc (memoize inc))
(memo-inc 10) ;; after first call , the value of this is stored, and every second call just takes the result from memory
(memo-inc 10)
;; use memoization for expensive functions, such as network calls

;;
;; Final project - pegthing
;;






