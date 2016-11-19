(ns clojufa.core
  (:require [instaparse.core :as insta]
            [clojure.string :refer [replace] :rename {replace basti}]))

(defn tarvihu
  "gasnu lo nu zo'oi *) noi .instaparse ke pinka sinxa cu co'u nenri"
  [tarduhe]
  (basti tarduhe "*)" "*\\)"))

(defn sitnyvihu
  "si'au na nelci zo'oi ' ji'a"
  [sitnyduhe]
  (basti sitnyduhe "'" "kuot"))

(defn pinka-staile-galfi
  "galfi lo laldo .peg. bo staile pinka lo .instaparse mapti"
  [pinka]
  (str "(* "
       (sitnyvihu (tarvihu (pinka 1)))
       " *)\n"))

(defn camxes-galfi
  "galfi lo .camxes.peg. lo se nelci be la'oi instaparse"
  [pegli]
  (-> pegli
      (basti "<-" "=")
      (basti "/" "|")
      (basti #"#(.*)\n" pinka-staile-galfi)
      (basti "[.\\t\\n\\r?!\\u0020]"
             "( '.' | #'\\s+' )")
      (basti #"\[(.*)\]" "#\"[$1]\"")
      (basti ".*" "#\".*\"")
      (basti "!." "!#\".\"")))

(def camxes-peg
  (-> "camxes.peg"
      (clojure.java.io/resource)
      (clojure.java.io/file)
      (slurp)))

(def camxes-gerna
  (insta/parser (camxes-galfi camxes-peg)))

(defn camxes
  "gentufa do'e la .camxes."
  [uenzi]
  (apply camxes-gerna [uenzi]))
