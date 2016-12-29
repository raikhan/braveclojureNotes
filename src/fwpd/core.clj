(ns fwpd.core)
(def filename "/Users/raikhan/coding/clojure/braveclojureNotes/src/fwpd/suspects.csv")

;; define keys for vampire data
(def vamp-keys [:name :glitter-index])

;; function to convert a string to integer
(defn str->int
  [str]
  (Integer. (clojure.string/replace  str " " "")))

;; define what to do with each data key
(def conversions {:name identity
                  :glitter-index str->int})

;; function to convert raw data from the csv file, depending on which key it belongs to
(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

;; function to extract data from the csv
(defn parse
  "Convert CSV vampire data to separated entries by row"
  [string]  ;; input is the raw string of the csv
  (map #(clojure.string/split % #",")         ;; split each row into columns on ","
       (clojure.string/split string #"\n")))  ;; split raw string into rows on newline symbol

;; parse the raw csv data
(parse (slurp filename))

;; define the function to parse the data in the required form
(defn mapify
  "Return a sequence of maps with vampire data"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))


;; function to filter vampires on their glitterness
(defn glitter-filter
  "Filter vampires by their glitterness"
  [minimum-glitter vampire-data]
  (filter #(>= (:glitter-index %) minimum-glitter) vampire-data))

;; apply mapify to the csv data
 (glitter-filter 3 (mapify (parse (slurp filename))))

;;
;; Exercises
;;

;; 1.
(map #(:name %) (glitter-filter 3 (mapify (parse (slurp filename)))))

;; 2. (not sure if this is the correct solution - how to add things at the end of a list???
(def suspect-list (mapify (parse (slurp filename))))

(defn append 
  "Add a suspect to the list"
  [suspect-list suspect-name suspect-glitter-index]
  (conj suspect-list {:name suspect-name :glitter-index suspect-glitter-index}))
(append suspect-list "Vlad Dracula" 9000)

;; 3. to do later...
