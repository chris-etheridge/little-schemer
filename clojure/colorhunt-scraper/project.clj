(defproject colorhunt-scraper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [clj-tagsoup "0.3.0"]
                 [lein-light-nrepl "0.3.3"]
                 [enlive "1.1.6"]
                 [cljs-ajax "0.5.4"]]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})
