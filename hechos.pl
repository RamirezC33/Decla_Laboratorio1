
protagonista(eric).
edad(eric, 30).

superviviente(timmy).

aliado_capturado(kelvin).
no_habla(kelvin).

mutante(virginia).
puede_volverse_aliada(virginia).


tiene(eric, hacha).
tiene(eric, encendedor).


% HABILIDADES

puede_cargar(kelvin, troncos).
puede_construir(kelvin).



zona(superficie).
zona(cuevas).
zona(bunkeres).



enemigo(canibales).
enemigo(mutantes).

aparece(canibales, superficie).
aparece(mutantes, superficie).
aparece(mutantes, cuevas).


% BUNKERES

sin_enemigos(bunkeres).
requiere(bunkeres, llaves).



peligro(cuevas, alto).

peligro(superficie, dia, medio).
peligro(superficie, noche, alto).



necesita(eric, refugio).
necesita(eric, comida).
necesita(eric, agua).


% MATERIALES

se_encuentra(troncos, superficie).
se_encuentra(piedras, superficie).