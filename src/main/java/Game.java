import javax.naming.SizeLimitExceededException;
import java.util.ArrayList;
import java.util.List;

class Game {

    static final int MAX_PUNTUACION_TIRADA = 10;

    List<Integer> tiradas = new ArrayList<>();

    void tirada(int pins) throws SizeLimitExceededException {

        if(pins > 10) {
            throw new SizeLimitExceededException("No se pueden derribar más de diez bolos en la tirada");
        }

        if(isSegundaTirada(tiradas.size()) && tiradas.size() > 0 && tiradas.get(tiradas.size()-1) + pins > 10) {
            throw new SizeLimitExceededException("No se pueden derribar más de diez bolos en el turno");
        }

        tiradas.add(pins);

        if(isPleno(pins)) {
            tiradaPleno();
        }

    }

    void tiradaPleno() {
        tiradas.add(0);
    }

    int score() {

        int score = 0;
        for(int i = 0; i < tiradas.size(); i++) {
            int segundaTirada = tiradas.get(i);
            if(isSegundaTirada(i)) {
                int primeraTirada = tiradas.get(i-1);

                if(isSemipleno(primeraTirada, segundaTirada)) {
                    score += this.calcularSemiPleno(i);
                }else if(isPleno(primeraTirada)) {
                    score += this.calcularPleno(i);
                }else {
                    score += primeraTirada + segundaTirada;
                }
            }else {
                score = calcularUltimaTiradaSuelta(score, i);
            }
        }
        return score;
    }

    private int calcularUltimaTiradaSuelta(int score, int indiceSegundaTirada) {
        if(esUltimaTirada(indiceSegundaTirada)) {
            int segundaTirada = tiradas.get(indiceSegundaTirada);
            score += segundaTirada;
        }
        return score;
    }

    boolean isSemipleno(int primeraTirada, int segundaTirada) {
        return segundaTirada > 0 && segundaTirada + primeraTirada == MAX_PUNTUACION_TIRADA;
    }

    boolean isPleno(int primeraTirada) {
        return primeraTirada == MAX_PUNTUACION_TIRADA;
    }

    boolean isSegundaTirada(int index) {
        return index % 2 != 0;
    }

    boolean esUltimaTirada(int index) {
        return index == tiradas.size()-1;
    }

    int calcularSemiPleno(int indiceSegundaTirada) {
        if(!esUltimaTirada(indiceSegundaTirada)) {
            int primeraTiradaDelSiguienteTurno = tiradas.get(indiceSegundaTirada + 1);
            return MAX_PUNTUACION_TIRADA + primeraTiradaDelSiguienteTurno;
        }
        return MAX_PUNTUACION_TIRADA;
    }

    int calcularPleno(int indiceSegundaTirada) {
        if(!esUltimaTirada(indiceSegundaTirada)) {
            int primeraTiradaDelSiguienteTurno = tiradas.get(indiceSegundaTirada + 1);
            int segundaTiradaDelSiguienteTurno = tiradas.get(indiceSegundaTirada + 2);

            int total = MAX_PUNTUACION_TIRADA + primeraTiradaDelSiguienteTurno;
            if(primeraTiradaDelSiguienteTurno == MAX_PUNTUACION_TIRADA) {
                if(existeTirada(indiceSegundaTirada + 3)) {
                    int primeraTiradaDeLosSiguientesDosTurnos = tiradas.get(indiceSegundaTirada + 3);
                    total += primeraTiradaDeLosSiguientesDosTurnos;
                }
            }else {
                total += segundaTiradaDelSiguienteTurno;
            }
            return total;
        }
        return MAX_PUNTUACION_TIRADA;
    }

    boolean existeTirada(int index) {
        return index < tiradas.size();
    }
}
