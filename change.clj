(defn calc-coins [amount denom]
  {:mod (mod amount denom),
   :dividend (int (/ amount denom))})

(defn clos [amount]
  ;; denoms is a list containing the currency denominations
  (let [denoms '(2000 1000 500 100 25 10 5 1)]
    (reduce
     ;; defining an anonymous function for utilization by (reduce).
     (fn [[(int amount-left) coins] denom]
       ;; let x equal the result of (calc-coins amount-left denom), which will be
       ;; of type `map`
       (let [x (calc-coins amount-left denom),
             ;; assign `mod` the value of the keyword `mod` in the mapping
             ;; assigned to `x`
             mod (:mod x),
             ;; assign `dividend` the value of the keyword `dividend` in the 
             ;; mapping assigned to `x`
             dividend (:dividend x)]
         ;; if `dividend` is positive (i.e. neither negative nor 0):
         (if (pos? dividend)
           ;; return a vector containing the remainder and a map of a 
           ;; denomination to the number of times you could divide it into 
           ;; amount-left. This return value will be consumed by reduce.
           [mod (assoc coins denom dividend)]
           ;; otherwise, return the original input value, which would be
           ;; `[amount {}]`. This will only happen if the amount-left is not
           ;; evenly divisible by the denomination, e.g. denom is 10 and
           ;; amount-left == 80.
           [amount-left coins])))
     [amount {}]
     denoms)))

;; dnolen_, #clojure:
;; (def denoms [20 10 5 1 0.25 0.05 0.01])

;; (defn make-change
;;   ([amt] (make-change amt denoms {}))
;;   ([amt denoms r]
;;      (if (< amt 1e-6)
;;        r
;;        (let [[f :as denoms] (drop-while #(> % amt) denoms)
;;              amt (- amt f)]
;;          (recur amt denoms (update-in r [f] (fnil inc 0)))))))

;; amalloy, #clojure:
;; (def denoms [[:twenty 20]
;;              [:ten 10]
;;              [:five 5]
;;              [:dollar 1]
;;              [:quarter 1/4]
;;              [:dime 1/10]
;;              [:nickel 1/20]
;;              [:penny 1/100]])

;; (defn change-denom [denom amount]
;;   (let [[label size] denom
;;         [num remaining] ((juxt quot rem) amount size)]
;;     (into {:remainder remaining}
;;           (when-not (zero? num)
;;             {label num}))))

;; (defn make-change [amount]
;;   (dissoc (apply merge
;;                  (reductions (fn [{:keys [remainder]} denom]
;;                                (change-denom denom remainder))
;;                              {:remainder amount}
;;                              denoms))
;;           :remainder))

