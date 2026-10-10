almacenar(N, [N]) :-
    N < 10.

almacenar(N, [D|L]) :-
    N >= 10,
    D is N mod 10,
    N1 is N // 10,
    almacenar(N1, L).