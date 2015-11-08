%PART 1
%fc_course(C):-course(C,_,3);course(C,_,4).
fc_course(C):-course(C,_,Credit),(Credit is 3; Credit is 4).

prereq_110(C):-course(C,P,_), member(ecs110,P).

ecs140a_students(S):-student(S,C), member(ecs140a,C).

teach_john(I):-instructor(I,CI),student(john,CS),member(C,CS),member(C,CI),!.
instructor_names(I):-instructor(I,_),teach_john(I).

study_jim(S):-student(S,CS),instructor(jim,CI),member(C,CS),member(C,CI),!.
students(S):-student(S,_),study_jim(S).

prereq(C,P):-course(C,PP,_), member(M,PP),(P=M,!;prereq(M,P)).
allprereq(C,PS):-findall(CC,(course(CC,_,_),prereq(C,CC)),PS).

/*
prereq(C,P):-course(C,PP,_), member(M,PP),(P=PP,!;prereq(M,P)).
allpreq(C,PS):-course()
allprereq(C,PS):-course(C,_,_),

*/


%PART 2

all_length([],0).
all_length([[H|T1]|T],L):- all_length([H|T1],LH),all_length(T,LT),L is LH+LT.
all_length([H|T],L):- atom(H),all_length(T,LT),L is 1 + LT.

all_length1([],0).
all_length1([H|T],L):- list(H),all_length1(H,LH),all_length1(T,LT),L is LH+LT.
all_length1([H|T],L):- atom(H),all_length1(T,LT),L is 1 + LT.


eq_help([],A,A).
eq_help([a|T],A,B):- AA is A+1,eq_help(T,AA,B).
eq_help([b|T],A,B):- BB is B+1,eq_help(T,A,BB),!.
eq_help([H|T],A,B):-eq_help(T,A,B).
equal_a_b(L):-eq_help(L,0,0).

palin(A):-reverse(A,RA),A=RA.

good([0]).
good([1|L]):-append(L1,L2,L),good(L1),good(L2),!.

% swap
append3(L1,L2,L3,L):-append(L1,LL,L),append(L2,L3,LL).
swap_prefix_suffix(K,L,S):-append3(L1,K,L3,L),append3(L3,K,L1,S).

% PART 3
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