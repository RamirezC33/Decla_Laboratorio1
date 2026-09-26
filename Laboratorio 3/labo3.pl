%caso base
suma_hasta(1, 1).

%regla recursiva
suma_hasta(N, R) :-
    N > 1,
    N1 is N - 1,
    suma_hasta(N1, R1),
    R is N + R1.