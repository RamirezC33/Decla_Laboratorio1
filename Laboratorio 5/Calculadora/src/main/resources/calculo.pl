% Suma de dos numeros
suma(A, B, R) :-
number(A), number(B), R is A + B.
% Resta de dos numeros
resta(A, B, R) :-
number(A), number(B), R is A - B.
% Multiplicacion de dos numeros
multiplicacion(A, B, R) :-
number(A), number(B), R is A * B.
% Division de dos numeros (el divisor no puede ser cero)
% Se multiplica por 1.0 para que 7/2 de 3.5 y no la division entera
division(A, B, R) :-
number(A), number(B), B =\= 0, R is A * 1.0 / B.
