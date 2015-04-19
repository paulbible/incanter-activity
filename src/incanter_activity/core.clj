(ns incanter-activity.core
  (:require [incanter.core]
            [incanter.io]
            [incanter.charts]
            [incanter.stats]
            [incanter.datasets]
            [incanter.optimize]))

(use '(incanter core io charts stats datasets optimize))

(def quotes-fname "data/HistoricalQuotes_T.csv")

(def quote-data (read-dataset quotes-fname
                              :delim \,
                              :header true))

;;;; Activity ;;;;;
;; Plot a moving average for period 15 and period 30.
;;
;; 1) Find the intersection points for these movinging averages
;;
;; 2) Find local minima of the chart
;;
;; 3) plot a trend line


;; getting started, a method for moving average
(map mean (partition 5 1 ($ :close quote-data)))

;; a plot with moving average a an example verticle line
(with-data quote-data
  (doto (xy-plot (range 0 (nrow $data)) ($ :close $data))    
    (set-y-range (reduce min ($ :low quote-data))
                 (reduce max ($ :high quote-data)))
    (add-lines (range 15 (nrow $data))
               (map mean (partition 15 1 ($ :close quote-data))))
    (add-lines (list 50 50) (list 0 50))
    (view)))

