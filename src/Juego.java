import java.util.ArrayList;

public class Juego {
    public static void main(String[] args) {

        Mazo mazo = new Mazo();
        Jugador j1 = new Jugador("Juan");
        Jugador j2 = new Jugador("Ana");

        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(j1);
        jugadores.add(j2);

        //bucle para robar cartas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < jugadores.size(); j++) {
                jugadores.get(j).recibirCarta(mazo.robarCarta());
            }
        }

        //bucle para mostrar las manos de los jugadores anteriormente robadas
        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).mostrarMano();
            System.out.println();

        }

        System.out.println("Cartas restantes: " + mazo.cartasRestantes());


        System.out.println();


        Descarte descarte = new Descarte();
        Carta cartaDescartada = j1.descartarCarta(0);
        Carta cartaDescartada2 = j2.descartarCarta(0);
        descarte.descartar(cartaDescartada);
        descarte.descartar(cartaDescartada2);

        System.out.println("Descartó: " + cartaDescartada);
        System.out.println("Tope del descarte: " + descarte.verTope());

        j1.recibirCarta(Descarte.robar());

        for (int i = 0; i < jugadores.size(); i++) {
            jugadores.get(i).mostrarMano();
            System.out.println();

        }

        System.out.println("Tope del descarte: " + descarte.verTope());

    }
}
