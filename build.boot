(def project 'uniappi)
(def version "0.1.0-SNAPSHOT")

(set-env! :resource-paths #{"resources" "src/cljs"}
          :source-paths   #{"test" "src/clj"}
          :dependencies   '[[org.clojure/clojure "RELEASE"]
                            [org.clojure/clojurescript "1.9.495"]
                            [ring/ring-core "1.5.1"]
                            [ring/ring-defaults "0.2.3"]
                            [ring/ring-jetty-adapter "1.3.1"]
                            [compojure "1.5.2"]
                            [hiccup "1.0.5"]
                            [reagent "0.6.0"]
                            [aleph "0.4.1"]
                            [adzerk/boot-test "RELEASE" :scope "test"]
                            [adzerk/boot-cljs "RELEASE" :scope "test"]])
(require '[adzerk.boot-cljs :refer [cljs]])

(task-options!
 aot {:namespace   #{'uniappi.core}}
 pom {:project     project
      :version     version
      :description "FIXME: write description"
      :url         "http://example/FIXME"
      :scm         {:url "https://github.com/yourname/uniappi"}
      :license     {"Eclipse Public License"
                    "http://www.eclipse.org/legal/epl-v10.html"}}
 jar {:main        'uniappi.core
      :file        (str "uniappi-" version "-standalone.jar")}
 cljs {:optimizations :whitespace})

(deftask build
  "Build the project locally as a JAR."
  [d dir PATH #{str} "the set of directories to write to (target)."]
  (let [dir (if (seq dir) dir #{"target"})]
    (comp (aot) (pom) (uber) (jar) (target :dir dir))))

(deftask run
  "Run the project."
  [a args ARG [str] "the arguments for the application."]
  (require '[uniappi.handler :as app])
  (apply (resolve 'app/-main) args))

(require '[adzerk.boot-test :refer [test]])
