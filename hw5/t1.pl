instructor(a,[c1]).
instructor(b,[]).
instructor(c,[c2]).
instructor(d,[c3,c4]).

lazy_instructors(Ins):-instructor(Ins,[]);instructor(Ins,[_]).
lazy_instructors1(Ins):- instructor(Ins,Cs),(Cs=[];Cs=[_]).

student(john,[c3,c4,c5]).
instructor(e,[c5]).
instructor_names1(I):- instructor(I,CI),student(john,CS),member(C,CI),member(C,CS).
instructor_names2(I):- instructor(I,CI),student(john,CS),member(C,CI),member(C,CS),!.

%instructor_names1(I).

my_reverse([],[]).
my_reverse([H|T],LR):-my_reverse(T,TR), append(TR,[H],LR).
