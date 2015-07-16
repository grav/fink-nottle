(require '[cljs.repl.node :as node]
         '[cemerick.piggieback :as piggieback])

(piggieback/cljs-repl
 (node/repl-env)
 :output-dir "out"
 :optimizations :none
 :target :nodejs
 :cache-analysis true
 :source-map true)

