(require 'clojure.contrib.strint)

(defn calc-coins [amount denom]
  {:mod (mod amount denom),
   :dividend (int (/ amount denom))})

(defn clos [amount]
  ;; denoms is a list containing the currency denominations
  (let [denoms '(2000 1000 500 100 25 10 5 1)]
    (reduce
     ;; defining an anonymous function for utilization by (reduce).
     (fn [[amount-left coins] denom]
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

(defn make-change [amount]
  (def denoms {2000 "Twenties", 1000 "Tens", 500 "Fives", 100 "Ones",
               25 "Quarters", 10 "Dimes", 5 "Nickels", 1 "Pennies"})
  (let [bar (for [i (keys (last (clos amount)))] {(last (find denoms i))
                                                  (last (find result i))})]
    (for [item bar] (<< "~{(first (keys item))}: ~{(first (vals item))}"))))
