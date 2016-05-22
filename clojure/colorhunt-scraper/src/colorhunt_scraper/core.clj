(ns colorhunt-scraper.core
  (:require [clojure.string :as string]))

;; this is far too imperative
(def data (string/split (slurp "resources/data.txt") #","))

(def clean-data (map #(string/replace % #"'" "") data))

;; this is super ugly code
(def colors (mapv (fn [s]
                   [(str "#" (subs s 0 6))
                    (str "#" (subs s 6 12))
                    (str "#" (subs s 12 18))
                    (str "#" (subs s 18 24))]) clean-data))

(spit "resources/clean_data.txt" colors)


