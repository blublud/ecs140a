append([],M,M).
append([H|T],M,[H|U]):-append(T,M,U).
