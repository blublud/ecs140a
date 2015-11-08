 solve(state(),state(),Moves).
 
 opposite(left,right).
 opposite(right,left).
 unsafe(A):- A is state(F,W,G,C),
				((opposite(F,W),W=G);
				 (opposite(F,G),G=C);
				 )
%safe(A):- \unsafe(A).


