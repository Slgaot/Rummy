import java.util.ArrayList;

public class Juego {
    public static void main(String[] args) {

        Mazo mazo = new Mazo();
        Jugador j1 = new Jugador("Juan");
        Jugador j2 = new Jugador("Ana");

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < jugadores.size(); j++) {
                jugadores.get(j).recibirCarta(mazo.robarCarta());
            }
        }

        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).mostrarMano();
            System.out.println();

        }

        System.out.println("Cartas restantes: " + mazo.cartasRestantes());
    }
}
