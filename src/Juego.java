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
                System.out.println("4. Ordenar mano");
                System.out.println("5. Mostrar mano");
                System.out.println("6. Mostrar mesa");
                System.out.println("7. Descartar carta");
                System.out.println("8. Añadir cartas");

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
                        System.out.println("¿Cómo quieres ordenar la mano?");
                        System.out.println("1. Por valor");
                        System.out.println("2. Por palo");

                        int opcionOrden = sc.nextInt();

                        if (opcionOrden == 1) {
                            jugadorActual.ordenarPorValor();
                            System.out.println("Mano ordenada por valor");
                        }
                        else if (opcionOrden == 2) {
                            jugadorActual.ordenarPorPalo();
                            System.out.println("Mano ordenada por palo");
                        }
                        else {
                            System.out.println("Opción inválida");
                        }

                        break;

                    case 5:

                        jugadorActual.mostrarMano();

                        break;

                    case 6:

                        mesa.mostrarMesa();

                        break;

                    case 7:

                        if (!yaRobo) {
                            System.out.println("Debes robar antes de descartar");
                            break;
                        }

                        jugadorActual.mostrarMano();

                        System.out.print("Elige índice de carta a descartar: ");
                        int indice = sc.nextInt();

                        if (!jugadorActual.indiceValido(indice)) {
                            System.out.println("Índice inválido");
                            break;
                        }

                        Carta descartada = jugadorActual.descartarCarta(indice);

                        if (descartada == null) {
                            System.out.println("No se pudo descartar la carta");
                            break;
                        }

                        descarte.descartar(descartada);

                        System.out.println("Descartaste: " + descartada);

                        turnoTerminado = true;

                        break;

                    case 8:

                        mesa.mostrarMesa();

                        System.out.print("Elige combinación: ");
                        int combo = sc.nextInt();

                        jugadorActual.mostrarMano();

                        System.out.print("Índice de carta: ");
                        int indiceCarta = sc.nextInt();

                        if (!jugadorActual.indiceValido(indiceCarta)) {
                            System.out.println("Índice inválido");
                            break;
                        }

                        Carta carta = jugadorActual.getMano().get(indiceCarta);

                        boolean ok = mesa.agregarACartaACombinacion(combo, carta);

                        if (ok) {
                            jugadorActual.descartarCarta(indiceCarta);
                            System.out.println("Carta añadida a la mesa");
                        } else {
                            System.out.println("Movimiento inválido");
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