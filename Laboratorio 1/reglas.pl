:- consult('hechos.pl').


% REGLA 1: Una zona tiene enemigos si algun enemigo aparece en ella.

zona_con_enemigos(Zona):-
    enemigo(Enemigo),
    aparece(Enemigo, Zona).


% REGLA 2: Kelvin puede recolectar un material si puede cargarlo y el material se encuentra en la superficie.

puede_recolectar(X, Material):-
    puede_cargar(X, Material),
    se_encuentra(Material, superficie).


% REGLA 3: Una necesidad basica es comida O agua O refugio.

necesidad_basica(X):-
    necesita(X, comida);
    necesita(X, agua);
    necesita(X, refugio).


% REGLA 4: Una zona es peligrosa si tiene nivel de peligro alto.

zona_peligrosa(X):-
    peligro(X, alto);
    peligro(X, _, alto).