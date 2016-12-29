(ns fwpd.core)
(def filename "/Users/raikhan/coding/clojure/braveclojureNotes/src/fwpd/suspects.csv")

;; define keys for vampire data
(def vamp-keys [:name :glitter-index])

;; function to convert a string to integer
(defn str->int
  [str]
  (Integer. str))

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



