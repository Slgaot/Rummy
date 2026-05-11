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

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < jugadores.size(); j++) {
                jugadores.get(j).recibirCarta(mazo.robarCarta());
            }
        }

        Descarte descarte = new Descarte();

        descarte.descartar(mazo.robarCarta());

        Mesa mesa = new Mesa();

        int turno = 0;

        while (true) {

            Jugador jugadorActual = jugadores.get(turno);

            boolean turnoTerminado = false;
            boolean yaRobo = false;

            System.out.println("\n======================");
            System.out.println("Turno de: " + jugadorActual.getNombre());
            System.out.println("======================");

            while (!turnoTerminado) {

                System.out.println("\nTope descarte: " + descarte.verTope());

                System.out.println("\n1. Robar del mazo");
                System.out.println("2. Robar del descarte");
                System.out.println("3. Bajar combinación");
                System.out.println("4. Mostrar mano");
                System.out.println("5. Mostrar mesa");
                System.out.println("6. Descartar carta");

                int opcion = sc.nextInt();

                switch (opcion) {

                    case 1:

                        if (!yaRobo) {

                            Carta robada = mazo.robarCarta();

                            jugadorActual.recibirCarta(robada);

                            System.out.println("Robaste: " + robada);

                            yaRobo = true;

                        } else {
                            System.out.println("Ya robaste este turno");
                        }

                        break;

                    case 2:

                        if (!yaRobo) {

                            Carta robada = descarte.robar();

                            if (robada != null) {

                                jugadorActual.recibirCarta(robada);

                                System.out.println("Robaste: " + robada);

                                yaRobo = true;

                            } else {
                                System.out.println("No hay cartas en descarte");
                            }

                        } else {
                            System.out.println("Ya robaste este turno");
                        }

                        break;

                    case 3:

                        jugadorActual.mostrarMano();

                        System.out.println("¿Cuántas cartas quieres usar?");
                        int cantidad = sc.nextInt();

                        int[] indices = new int[cantidad];

                        for (int i = 0; i < cantidad; i++) {

                            System.out.print("Índice " + (i + 1) + ": ");
                            indices[i] = sc.nextInt();
                        }

                        ArrayList<Carta> seleccionadas =
                                jugadorActual.seleccionarCartas(indices);

                        if (jugadorActual.esTrio(seleccionadas) ||
                                jugadorActual.esEscalera(seleccionadas)) {

                            mesa.agregarCombinacion(seleccionadas);

                            jugadorActual.quitarCartas(seleccionadas);

                            System.out.println("¡Combinación bajada!");

                        } else {

                            System.out.println("Combinación inválida");
                        }

                        break;

                    case 4:

                        jugadorActual.mostrarMano();

                        break;

                    case 5:

                        mesa.mostrarMesa();

                        break;

                    case 6:

                        if (!yaRobo) {
                            System.out.println("Debes robar antes de descartar");
                            break;
                        }

                        jugadorActual.mostrarMano();

                        System.out.print("Elige índice de carta a descartar: ");

                        int indice = sc.nextInt();

                        if (indice >= 0 && indice < jugadorActual.cantidadCartas()) {

                            Carta descartada = jugadorActual.descartarCarta(indice);

                            descarte.descartar(descartada);

                            System.out.println("Descartaste: " + descartada);

                            turnoTerminado = true;

                        } else {
                            System.out.println("Índice inválido");
                        }

                        break;

                    default:
                        System.out.println("Opción inválida");
                }
            }

            if (jugadorActual.cantidadCartas() == 0) {

                System.out.println("\nGANADOR: " + jugadorActual.getNombre());

                break;
            }

            turno = (turno + 1) % jugadores.size();
        }

        sc.close();
    }
}