(ns clojure-noob.euler1
  (:gen-class))

;;
;; Euler problem 1
;;

;; check if number is multiple of 3 or 5
(defn multiple-of
  [factor]
  (fn [x] 
    (if (= (mod x factor) 0)
      true
      false)))
(defn multiple35-for-sum
  [n]
  (if (or ((multiple-of 3) n) ((multiple-of 5) n))
    n
    0))

(reduce + 
        (map multiple35-for-sum 
             (range 0 10)))    ;; 23 - correct!

(reduce + 
        (map multiple35-for-sum 
             (range 0 1000)))   ;; final answer = 233168

