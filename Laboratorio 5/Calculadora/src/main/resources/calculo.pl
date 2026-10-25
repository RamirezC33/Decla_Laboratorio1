% Suma de dos numeros
suma(A, B, R) :-
number(A), number(B), R is A + B.
% Resta de dos numeros
resta(A, B, R) :-
number(A), number(B), R is A - B.
% Multiplicacion de dos numeros
multiplicacion(A, B, R) :-
number(A), number(B), R is A * B.
% Division de dos numeros 
% Se multiplica por 1.0 
division(A, B, R) :-
number(A), number(B), B =\= 0, R is A * 1.0 / B.
