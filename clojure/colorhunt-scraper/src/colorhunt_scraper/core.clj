(ns colorhunt-scraper.core
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

;; this is far too imperative
(def data (string/split (slurp "resources/data.txt") #","))

(def clean-data (map #(string/replace % #"'" "") data))

;; this is super ugly code
(def colors (mapv (fn [s]
                    [(str "#" (subs s 0 6))
                     (str "#" (subs s 6 12))
                     (str "#" (subs s 12 18))
                     (str "#" (subs s 18 24))]) clean-data))

;; cleans & spits data
(defn spit-data [inp outp]
  (let [data (-> (slurp inp)
                 (string/split #","))
        data (map #(string/replace %"'" "") data)
        cs (mapv (fn [s]
                   [(str "#" (subs s 0 6))
                    (str "#" (subs s 6 12))
                    (str "#" (subs s 12 18))
                    (str "#" (subs s 18 24))]) data)]
    (for [cgroup cs]
      (with-open [wrt (io/writer outp :append true)]
        (doseq [c cgroup]
          (.write wrt (str c ",")))
        (.write wrt (str "anonymous" ",false" " \n"))))))

(spit-data "resources/data.txt" "resources/clean_data.txt")


