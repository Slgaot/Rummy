import java.util.ArrayList;
import java.util.Scanner;

public class Juego {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
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

        Descarte descarte = new Descarte();

        descarte.descartar(mazo.robarCarta());

        int turno = 0;

        Mesa mesa = new Mesa();

        while (true) {

            Jugador jugadorActual = jugadores.get(turno);

            System.out.println("\nTurno de: " + jugadorActual.getNombre());

            System.out.println("1. Robar del mazo");
            System.out.println("2. Robar del descarte");

            int opcion = sc.nextInt();

            Carta robada;

            if (opcion == 2 && descarte.verTope() != null) {
                robada = descarte.robar();
                System.out.println("Robaste del descarte: " + robada);
            } else {
                robada = mazo.robarCarta();
                System.out.println("Robaste del mazo: " + robada);
            }

            jugadorActual.recibirCarta(robada);

            jugadorActual.mostrarMano();

            if (jugadorActual.tieneTrio()) {
                System.out.println("¡Tienes un trío!");
            }

            if (jugadorActual.tieneEscalera()) {
                System.out.println("¡Tienes una escalera!");
            }

            ArrayList<Carta> trio = jugadorActual.obtenerTrio();

            if (trio != null) {
                mesa.agregarCombinacion(trio);
                System.out.println("¡Has bajado un trío!");
                jugadorActual.mostrarMano();

            }

            ArrayList<Carta> escalera = jugadorActual.obtenerEscalera();

            if (escalera != null) {
                mesa.agregarCombinacion(escalera);
                System.out.println("¡Has bajado una escalera!");
                jugadorActual.mostrarMano();

            }

            mesa.mostrarMesa();

            int indice;

            do {
                System.out.print("Elige índice de carta a descartar: ");
                indice = sc.nextInt();

            } while (indice < 0 || indice >= jugadorActual.cantidadCartas());

            Carta descartada = jugadorActual.descartarCarta(indice);
            descarte.descartar(descartada);

            System.out.println("Descartaste: " + descartada);

            System.out.println("Tope del descarte: " + descarte.verTope());

            turno = (turno + 1) % jugadores.size();
        }
    }
}