(defun all-length(l)
	(cond
		((null l) 0)
		((listp (car l)) (+ (all-length (car l)) (all-length (cdr l))))
		((atom (car l)) (+ 1 (all-length (cdr l))))		
	)
)

(defun minl(l)
	(cond
		((null (cdr l)) (car l))
		(t (min (car l) (minl (cdr l))))
	)
)

(defun maxl(l)
	(cond
		((null (cdr l)) (car l))
		(t (max (car l) (maxl (cdr l))))
	)
)


(defun range(l)
	(list (minl l) (maxl l))
)

(defun before_failed(a b l)
	(cond
		((null l) l)
		((and (null a) (equal b (car l))) (list (car l)))
		((null a) (cons (car l) (before a b (cdr l))))
		((equal (car l) a) (cons a (before nil b (cdr l))))
		(t (before a b (cdr l)))
	)
)

(defun before_help (a b seen_a l)
	(cond 
		((null l) l)
		((and seen_a (equal b (car l))) (list (car l)))
		((and t seen_a) (cons (car l) (before_help a b seen_a (cdr l))))
		((equal (car l) a) (cons (car l) (before_help a b t (cdr l))))
		(t (before_help a b seen_a (cdr l)))
	)
)

(defun before(a b l)
	(before_help a b nil l)
)


(defun splitif-eval (func l bool)
	(cond
		((null l) l)
		((equal (funcall func (car l)) bool) (cons (car l) (splitif-eval func (cdr l) bool)))
		(t (splitif-eval func (cdr l) bool))
	)
)

(defun split-if (func l)
	(list (splitif-eval func l nil) (splitif-eval func l T))
)

(defun group_help(l g c)
	(cond
		((null l) (list g))
		((equal (length g) c) (cons g (group_help l '() c)))
		(t (group_help (cdr l) (append g (list(car l))) c))
	)
)
(defun group(l c)
	(group_help l '() c)
)


(defun mostn_helper (f l n r)
		(cond
			((null l)
				(cond
					;((> (funcall f l) n) (list (list l) (funcall f l)))
					;((equal (funcall f l) n) (list (append r (list l)) n))
					(t (list r n))
				)
			)
			((> (funcall f (car l)) n) (mostn_helper f (cdr l) (funcall f (car l)) (list (car l))))
			((equal (funcall f (car l))n) (mostn_helper f (cdr l) n (append r (list (car l)))))
			(t (mostn_helper f (cdr l) n r))
		)
)

(defun mostn (f l)
	(cond 
		((null l) (list (list l) (funcall f l)))
		(t (mostn_helper f (cdr l) (funcall f (car l)) (list (car l))))
	)
)

(defun match (l1 l2)
	(cond
		((or (null l1) (null l2)) (equal l1 l2))
		((equal (car l1) '?)  (match (cdr l1) (cdr l2)))
		((and (equal (car l1) '!) (match (cdr l1) (cdr l2))) t)
		((equal (car l1) '!) (match l1 (cdr l2)))
		(t (and (equal (car l1) (car l2)) (match (cdr l1) (cdr l2))))
		
	)
)

;PART 3
(defun my-nconc (l e)
	(cond 
		((null l) (setf l (list e)))
		;(t (nconc l (list e)))
	)
)
(defun reset-var(l)
	(cond
		((null l) t)
		((and (listp (car l)) (equal (caar l) '!)) (eval (list 'setq (cadar l) 'nil)) (reset-var (cdr l))) 
		(t (reset-var (cdr l)))
	)
)

(defun match-var-helper(l1 l2)
	(cond
		((or (null l1) (null l2)) (equal l1 l2))
		((null (car l1)) (and (equal (car l1) (car l2)) (match-var-helper (cdr l1) (cdr l2))))
		((listp (car l1)) 
			(cond
				((equal (caar l1) '?) 
					(eval (list 'setq (cadar l1) (list 'quote (car l2))))
					(match-var-helper (cdr l1) (cdr l2))
				)
				(	(and 
						(equal (caar l1) '!)
						(reset-var (cdr l1))
						(eval (list 'setq (cadar l1) (list 'append (cadar l1) (list 'list (list 'quote (car l2))))))
						(match-var-helper (cdr l1) (cdr l2))
					)
					t
				)
				(	(and (equal (caar l1) '!) 
						(reset-var (cdr l1))						
						(match-var-helper l1 (cdr l2))
					)
					t
				)
			)
		)
		(t (and (equal (car l1) (car l2)) (match-var-helper (cdr l1) (cdr l2))))
	)
)

; (step (match-var '((! u) a (? v)) '(b c a d) ))
;	(match-var '((! u) a (? v)) '(b c a d) )
(defun match-var(l1 l2)
	(reset-var l1)
	(match-var-helper l1 l2)
)


