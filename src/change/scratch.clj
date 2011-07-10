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

