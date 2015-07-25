(ns fink-nottle.test.util
  (:require [glossop.core :as g
             #? (:clj :refer :cljs :refer-macros) [go-catching <?]]
            #?@ (:clj
                 [[clojure.test :as test]
                  [clojure.core.async :as async]]
                 :cljs
                 [[cljs.test]]))
  #? (:cljs (:require-macros [fink-nottle.test.util])))

#? (:clj
    (defmacro deftest [t-name & forms]
      (if (:ns &env)
        `(cljs.test/deftest ~t-name
           (cljs.test/async
            done#
            (go-catching
              (try
                (<? (do ~@forms))
                (catch js/Error e#
                  (.error js/console e# (aget e# "stack"))
                  (cljs.test/is (nil? e#))))
              (done#))))
        `(test/deftest ~t-name
           (g/<?! (do ~@forms))))))

#? (:clj
    (defmacro is [& args]
      (if (:ns &env)
        `(cljs.test/is ~@args)
        `(test/is ~@args))))
