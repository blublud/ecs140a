mymember(X,[X|_]).
mymember(X,[_|Y]):-mymember(X,Y).

myis(X):- X = Y, isis(Y).
isis(X):-Y is 1000.

/*
a(X,Y):-b(X),c(Y),!.
b(1).
b(2).
c(3).
c(4).

a(X,Y):-b(X),(c(Y),!;d(Y)).
b(1).
c(2):-1=1.
d(3):-2=2.

c(X).
c(d(1)).

*/

mymember(E,[H|_]):-E is H.
mymember(E,[_|T]):-mymember(E,T),!.

