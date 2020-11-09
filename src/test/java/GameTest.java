import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.SizeLimitExceededException;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;

    @BeforeEach
    void init() {
        game = new Game();
    }

    @Test
    void maximo_de_bolos_tirados_en_una_tirada_dentro_de_lo_permitido() {
        int bolosTiradosLegal = 10;
        assertDoesNotThrow(() -> {
            game.tirada(bolosTiradosLegal);
        });
    }

    @Test
    void excepcion_maximo_de_bolos_tirados_en_una_tirada_mayor_a_10() {
        int bolosTiradosIllegal = 11;
        assertThrows(SizeLimitExceededException.class, () -> {
            game.tirada(bolosTiradosIllegal);
        });
    }

    @Test
    void maximo_de_bolos_tirados_en_un_turno_dentro_de_lo_permitido() throws SizeLimitExceededException {
        int tirada1 = 7;
        int tirada2 = 3;

        game.tirada(tirada1);
        assertDoesNotThrow(() -> {
            game.tirada(tirada2);
        });
    }

    @Test
    void excepcion_maximo_de_bolos_tirados_en_un_turno_mayor_a_10() throws SizeLimitExceededException {
        int tirada1 = 7;
        int tirada2 = 4;

        game.tirada(tirada1);
        assertThrows(SizeLimitExceededException.class, () -> {
            game.tirada(tirada2);
        });
    }

    @Test
    void suma_puntuaciones_de_las_tiradas() throws SizeLimitExceededException {
        int tirada1 = 7;
        int tirada2 = 2;

        int tirada1_turno1 = 2;
        int tirada2_turno1 = 5;

        game.tirada(tirada1);
        game.tirada(tirada2);

        game.tirada(tirada1_turno1);
        game.tirada(tirada2_turno1);

        assertEquals(game.score(), 16);
    }

    @Test
    void semipleno() throws SizeLimitExceededException {
        int tirada1 = 5;
        int tirada2 = 5;

        int tirada1_turno1 = 2;
        int tirada2_turno1 = 5;

        game.tirada(tirada1);
        game.tirada(tirada2);

        game.tirada(tirada1_turno1);
        game.tirada(tirada2_turno1);

        assertEquals(game.score(), 19);
    }

    @Test
    void pleno() throws SizeLimitExceededException {
        int tirada1 = 10;

        int tirada1_turno1 = 2;
        int tirada2_turno1 = 5;

        game.tirada(tirada1);

        game.tirada(tirada1_turno1);
        game.tirada(tirada2_turno1);

        assertEquals(game.score(), 24);
    }

    @Test
    void partida_1() throws SizeLimitExceededException {
        game.tirada(10); // 20

        game.tirada(5);
        game.tirada(5); // 16

        game.tirada(6);
        game.tirada(0); // 6

        game.tirada(0);
        game.tirada(3); // 3

        game.tirada(4);
        game.tirada(6); // 17

        game.tirada(7);
        game.tirada(0); // 7

        game.tirada(0);
        game.tirada(2); // 2

        game.tirada(0);
        game.tirada(0); // 0

        game.tirada(9);
        game.tirada(1); // 20

        game.tirada(10); // 10

        assertEquals(game.score(), 101);
    }

    @Test
    void partida_2() throws SizeLimitExceededException {
        game.tirada(10);

        game.tirada(1);
        game.tirada(1); // No esta sumando

       assertEquals(game.score(), 14);
    }

    @Test
    void partida_3() throws SizeLimitExceededException {
        game.tirada(10);

        assertEquals(game.score(), 10);
    }

    @Test
    void partida_4() throws SizeLimitExceededException {
        game.tirada(5);
        game.tirada(5);

        assertEquals(game.score(), 10);
    }

    @Test
    void partida_5() throws SizeLimitExceededException {
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);

        assertEquals(game.score(), 270);
    }

    @Test
    void partida_6() throws SizeLimitExceededException {
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);
        game.tirada(9);
        game.tirada(1);


        assertEquals(game.score(), 181);
    }

    @Test
    void sucesion_de_plenos() throws SizeLimitExceededException {
        game.tirada(10);
        game.tirada(10);
        game.tirada(10);
        game.tirada(1);
        game.tirada(1);

        assertEquals(game.score(), 65);
    }

    @Test
    void sucesion_de_semiplemos_y_ultima_tirada_suelta() throws SizeLimitExceededException {
        game.tirada(5);
        game.tirada(5); // 15

        game.tirada(5);
        game.tirada(5); // 11

        game.tirada(1);
        game.tirada(5); // 6

        game.tirada(1); // 1

        assertEquals(game.score(), 33);
    }

    @Test
    void tirada_suelta() throws SizeLimitExceededException {
        game.tirada(1);

        assertEquals(game.score(), 1);
    }
}
