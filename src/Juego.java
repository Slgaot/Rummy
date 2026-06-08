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

        Reorganizacion reorganizacion = new Reorganizacion();
        boolean modoReorganizacion = false;
        ArrayList<ArrayList<Carta>> copiaMesa = new ArrayList<>();

        while (true) {

            Jugador jugadorActual = jugadores.get(turno);

            boolean turnoTerminado = false;
            boolean yaRobo = false;


            ArrayList<ArrayList<Carta>> bajadasTurno = new ArrayList<>();
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
                System.out.println("10. Iniciar reorganización");
                System.out.println("11. Confirmar reorganización");
                System.out.println("12. Crear combinación reorganización");

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

                            if (mazo.estaVacio()) {

                                System.out.println("Mazo vacío. Reciclando descarte...");

                                ArrayList<Carta> recicladas =
                                        descarte.recuperarCartasMenosTope();

                                mazo.agregarCartas(recicladas);
                            }

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
                            break;
                        }

                        if (jugadorActual.esTrio(seleccionadas)
                                || jugadorActual.esEscalera(seleccionadas)) {

                            bajadasTurno.add(seleccionadas);

                            jugadorActual.quitarCartas(seleccionadas);

                            int puntos = mesa.calcularPuntos(seleccionadas);
                            puntosBajadaTurno += puntos;

                            System.out.println("✔ Combinación válida (" +
                                    puntos + " puntos)");

                            System.out.println("Total acumulado: " +
                                    puntosBajadaTurno);

                        } else {

                            System.out.println("Combinación inválida");
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

                        if (bajadasTurno.isEmpty()) {
                            System.out.println("No has preparado ninguna combinación");
                            break;
                        }

                        if (!jugadorActual.haHechoPrimeraBajada()) {

                            if (puntosBajadaTurno >= 30) {

                                for (ArrayList<Carta> combinacion : bajadasTurno) {

                                    mesa.agregarCombinacion(combinacion);
                                }

                                jugadorActual.marcarPrimeraBajada();

                                System.out.println(
                                        "✔ Primera bajada completada (" +
                                                puntosBajadaTurno +
                                                " puntos)"
                                );

                                bajadasTurno.clear();
                                puntosBajadaTurno = 0;

                            } else {

                                System.out.println(
                                        "Necesitas al menos 30 puntos. Llevas "
                                                + puntosBajadaTurno
                                );

                                for (ArrayList<Carta> combinacion : bajadasTurno) {

                                    for (Carta c : combinacion) {
                                        jugadorActual.recibirCarta(c);
                                    }
                                }

                                bajadasTurno.clear();
                                puntosBajadaTurno = 0;
                            }

                        } else {

                            for (ArrayList<Carta> combinacion : bajadasTurno) {

                                mesa.agregarCombinacion(combinacion);
                            }

                            System.out.println("✔ Combinaciones bajadas");

                            bajadasTurno.clear();
                            puntosBajadaTurno = 0;
                        }

                        break;

                    case 10:

                        if (!jugadorActual.haHechoPrimeraBajada()) {

                            System.out.println("Debes hacer la primera bajada.");
                            break;
                        }

                        modoReorganizacion = true;

                        reorganizacion.limpiar();

                        copiaMesa.clear();

                        for (ArrayList<Carta> c : mesa.getCombinaciones()) {

                            copiaMesa.add(new ArrayList<>(c));
                        }

                        boolean seguirSacando = true;

                        while (seguirSacando) {

                            mesa.mostrarMesa();

                            System.out.print("Índice de combinación (-1 para terminar): ");

                            int indiceCombo = sc.nextInt();

                            if (indiceCombo == -1) {
                                break;
                            }

                            ArrayList<Carta> combinacion =
                                    mesa.obtenerCombinacion(indiceCombo);

                            if (combinacion == null) {

                                System.out.println("Combinación inválida");
                                continue;
                            }

                            reorganizacion.agregarCartas(
                                    new ArrayList<>(combinacion));

                            mesa.eliminarCombinacion(indiceCombo);

                            System.out.println("Combinación extraída.");

                            System.out.println("¿Extraer otra?");
                            System.out.println("1. Sí");
                            System.out.println("2. No");

                            int opExtraer = sc.nextInt();

                            if (opExtraer == 2) {
                                seguirSacando = false;
                            }
                        }

                        System.out.println();

                        System.out.println("Ahora puedes añadir cartas de tu mano.");

                        boolean seguirMano = true;

                        while (seguirMano) {

                            jugadorActual.mostrarMano();

                            System.out.print("Índice (-1 para terminar): ");

                            int indiceMano = sc.nextInt();

                            if (indiceMano == -1) {
                                break;
                            }

                            if (!jugadorActual.indiceValido(indiceMano)) {

                                System.out.println("Índice inválido");
                                continue;
                            }

                            Carta cartaReorganizacion =
                                    jugadorActual.descartarCarta(indiceMano);

                            reorganizacion.agregarCarta(cartaReorganizacion);

                            reorganizacion.mostrarCartas();

                            System.out.println("¿Añadir otra carta?");
                            System.out.println("1. Sí");
                            System.out.println("2. No");

                            int opCarta = sc.nextInt();

                            if (opCarta == 2) {
                                seguirMano = false;
                            }
                        }

                        reorganizacion.mostrarCartas();

                        System.out.println();
                        System.out.println("Usa ahora la opción 11 para crear nuevas combinaciones.");

                        break;

                    case 11:

                        if (!modoReorganizacion) {

                            System.out.println("No hay reorganización iniciada.");
                            break;
                        }

                        reorganizacion.mostrarCartas();

                        System.out.print("¿Cuántas cartas tendrá la combinación?: ");

                        int cantidadNueva = sc.nextInt();

                        int[] indicesNueva = new int[cantidadNueva];

                        ArrayList<Carta> nuevaCombinacion = new ArrayList<>();

                        boolean error = false;

                        for (int i = 0; i < cantidadNueva; i++) {

                            System.out.print("Índice " + (i + 1) + ": ");

                            indicesNueva[i] = sc.nextInt();

                            if (indicesNueva[i] < 0 ||
                                    indicesNueva[i] >= reorganizacion.getCartas().size()) {

                                error = true;
                                break;
                            }

                            for (int j = 0; j < i; j++) {

                                if (indicesNueva[i] == indicesNueva[j]) {

                                    error = true;
                                    break;
                                }
                            }

                            if (error) {
                                break;
                            }

                            nuevaCombinacion.add(
                                    reorganizacion.getCartas().get(indicesNueva[i])
                            );
                        }

                        if (error) {

                            System.out.println("Selección inválida.");

                            break;
                        }

                        if (!mesa.esValida(nuevaCombinacion)) {

                            System.out.println("Combinación inválida.");

                            break;
                        }

                        reorganizacion.agregarNuevaCombinacion(nuevaCombinacion);

                        System.out.println("Combinación creada correctamente.");

                        reorganizacion.mostrarCombinaciones();

                        if (!reorganizacion.quedanCartas()) {

                            System.out.println();
                            System.out.println("No quedan cartas.");
                            System.out.println("Ahora usa la opción 12 para confirmar.");
                        }

                        break;

                    case 12:

                        if (!modoReorganizacion) {

                            System.out.println("No hay reorganización iniciada.");
                            break;
                        }

                        if (reorganizacion.quedanCartas()) {

                            System.out.println("Todavía quedan cartas sin colocar.");

                            reorganizacion.mostrarCartas();

                            break;
                        }

                        if (!mesa.validarMesa(reorganizacion.getCombinaciones())) {

                            System.out.println("La reorganización no es válida.");

                            mesa.reemplazarMesa(copiaMesa);

                            reorganizacion.limpiar();

                            modoReorganizacion = false;

                            copiaMesa.clear();

                            break;
                        }

                        mesa.reemplazarMesa(reorganizacion.getCombinaciones());

                        reorganizacion.limpiar();

                        modoReorganizacion = false;

                        copiaMesa.clear();

                        System.out.println();
                        System.out.println("✔ Reorganización completada correctamente.");

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