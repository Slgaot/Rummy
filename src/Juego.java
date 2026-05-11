import java.util.ArrayList;
import java.util.Scanner;

public class Juego {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Mazo mazo = new Mazo();
        Mesa mesa = new Mesa();
        Descarte descarte = new Descarte();

        System.out.print("¿Cuántos jugadores? ");
        int numJugadores = sc.nextInt();
        sc.nextLine();

        ArrayList<Jugador> jugadores = new ArrayList<>();

        for (int i = 0; i < numJugadores; i++) {

            System.out.print("Nombre del jugador " + (i + 1) + ": ");
            String nombre = sc.nextLine();

            jugadores.add(new Jugador(nombre));
        }

        int cartasPorJugador = (numJugadores <= 3) ? 13 : 10;

        for (int i = 0; i < cartasPorJugador; i++) {
            for (Jugador j : jugadores) {
                j.recibirCarta(mazo.robarCarta());
            }
        }

        descarte.descartar(mazo.robarCarta());

        int turno = 0;

        while (true) {

            Jugador jugadorActual = jugadores.get(turno);

            boolean turnoTerminado = false;
            boolean yaRobo = false;

            int puntosBajadaTurno = 0;

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
                System.out.println("8. Añadir carta a mesa");
                System.out.println("9. Terminar bajadas");

                int opcion = sc.nextInt();

                switch (opcion) {

                    case 1:
                    case 2:

                        if (yaRobo) {
                            System.out.println("Ya has robado este turno");
                            break;
                        }

                        Carta robada;

                        if (opcion == 1) {
                            robada = mazo.robarCarta();
                        } else {
                            robada = descarte.robar();
                        }

                        if (robada == null) {
                            System.out.println("No hay carta para robar");
                            break;
                        }

                        jugadorActual.recibirCarta(robada);
                        yaRobo = true;

                        System.out.println("Robaste: " + robada);

                        break;

                    case 3:

                        if (!yaRobo) {
                            System.out.println("Debes robar primero");
                            break;
                        }

                        boolean seguirBajando = true;

                        while (seguirBajando) {

                            jugadorActual.mostrarMano();

                            System.out.println("¿Cuántas cartas?");
                            int cantidad = sc.nextInt();

                            int[] indices = new int[cantidad];

                            for (int i = 0; i < cantidad; i++) {
                                System.out.print("Índice " + (i + 1) + ": ");
                                indices[i] = sc.nextInt();
                            }

                            ArrayList<Carta> seleccionadas =
                                    jugadorActual.seleccionarCartas(indices);

                            if (seleccionadas == null) {
                                System.out.println("Selección inválida");
                                continue;
                            }

                            if (jugadorActual.esTrio(seleccionadas)
                                    || jugadorActual.esEscalera(seleccionadas)) {

                                mesa.agregarCombinacion(seleccionadas);
                                jugadorActual.quitarCartas(seleccionadas);

                                int puntos = mesa.calcularPuntos(seleccionadas);
                                puntosBajadaTurno += puntos;

                                System.out.println("✔ Bajada OK (" + puntos + " puntos)");
                            } else {
                                System.out.println("❌ Combinación inválida");
                            }

                            System.out.println("¿Seguir bajando? (1=Sí / 2=No)");
                            int resp = sc.nextInt();

                            if (resp == 2) {
                                seguirBajando = false;
                            }
                        }

                        break;

                    case 4:

                        System.out.println("1. Por valor");
                        System.out.println("2. Por palo");

                        int op = sc.nextInt();

                        if (op == 1) {
                            jugadorActual.ordenarPorValor();
                        } else if (op == 2) {
                            jugadorActual.ordenarPorPalo();
                        } else {
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

                        System.out.print("Índice de carta: ");
                        int indice = sc.nextInt();

                        if (!jugadorActual.indiceValido(indice)) {
                            System.out.println("Índice inválido");
                            break;
                        }

                        Carta descartada = jugadorActual.descartarCarta(indice);

                        if (descartada == null) {
                            System.out.println("Error al descartar");
                            break;
                        }

                        descarte.descartar(descartada);

                        System.out.println("Descartaste: " + descartada);

                        turnoTerminado = true;

                        break;

                    case 8:

                        if (!yaRobo) {
                            System.out.println("Debes robar primero");
                            break;
                        }

                        if (!jugadorActual.haHechoPrimeraBajada()) {
                            System.out.println("Debes hacer primera bajada antes de añadir cartas");
                            break;
                        }

                        mesa.mostrarMesa();

                        System.out.print("Combinación: ");
                        int combo = sc.nextInt();

                        jugadorActual.mostrarMano();

                        System.out.print("Índice carta: ");
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

                    case 9:

                        if (!jugadorActual.haHechoPrimeraBajada()) {

                            if (puntosBajadaTurno >= 30) {
                                jugadorActual.marcarPrimeraBajada();
                                System.out.println("✔ Primera bajada completada");
                            } else {
                                System.out.println("❌ Necesitas mínimo 30 puntos en este turno");
                                break; // no termina turno
                            }
                        }

                        System.out.println("Bajadas finalizadas");
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