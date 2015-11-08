
opposite(left,right).
opposite(right,left).

move(state(F,W,G,C),state(FF,WW,GG,CC)):- 
	opposite(F,FF);
	F = W, opposite(F,FF),opposite(W,WW),G=GG,C=CC;
	F=G,opposite(F,FF),opposite(G,GG),W=WW,C=CC;
	F=C,opposite(F,FF),opposite(C,CC),W=WW,G=GG.
	
%move(state(left,left,left,left),state(FF,WW,GG,CC)).
	
print_list:-get_list(L),member(M,L).
get_list([1,2,3]).

/*
solve(A,B,C,D,A,B,C,D,_,[]):-!.
solve(A,B,C,D, A2,B2,C2,D2,Path,[H|T]):-
	(move(A,AA),safe(AA,B,C,D), \+ member([AA,B,C,D],Path),solve(AA,B,C,D,A2,B2,C2,D2,[[AA,B,C,D]|Path],T),H=farmer);
	(A=B,move(A,AA),safe(AA,AA,C,D), \+ member([AA,AA,C,D],Path), solve(AA,AA,C,D,A2,B2,C2,D2,[[AA,AA,C,D]|Path],T),H=wolf);
	(A=C,move(A,AA),safe(AA,B,AA,D), \+ member([AA,B,AA,D],Path), solve(AA,B,AA,D,A2,B2,C2,D2,[[AA,B,AA,D]|Path],T),H=goat);
	(A=D,move(A,AA),safe(AA,B,C,AA), \+ member([AA,B,C,AA],Path), solve(AA,B,C,AA,A2,B2,C2,D2,[[AA,B,C,AA]|Path],T),H=cabbage).

move(left,right).
move(right,left).

safe(A,B,C,D):- \+unsafe(A,B,C,D).
unsafe(A,B,B,_):-A\=B.
unsafe(A,_,C,C):-A\=C.

puzzle(L):-solve(left,left,left,left ,right,right,right,right,[],L).
%solve
*/