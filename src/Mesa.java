import java.util.ArrayList;
import java.util.Collections;

public class Mesa {

    private ArrayList<ArrayList<Carta>> combinaciones;

    public Mesa() {
        combinaciones = new ArrayList<>();
    }

    public void agregarCombinacion(ArrayList<Carta> combo) {
        combinaciones.add(combo);
    }

    public void mostrarMesa() {

        System.out.println("Mesa:");

        for (int i = 0; i < combinaciones.size(); i++) {

            System.out.print(i + " → [ ");

            for (Carta c : combinaciones.get(i)) {
                System.out.print(c + " ");
            }

            System.out.println("]");
        }
    }

    public boolean agregarACartaACombinacion(int indice, Carta carta) {

        if (indice < 0 || indice >= combinaciones.size()) {
            return false;
        }

        ArrayList<Carta> combo = combinaciones.get(indice);

        combo.add(carta);

        if (esValida(combo)) {
            return true;
        } else {
            combo.remove(carta);
            return false;
        }
    }

    public boolean esValida(ArrayList<Carta> combo) {

        if (combo.size() < 3) {
            return false;
        }

        // ===== TRÍO =====

        Valor valorBase = null;
        boolean esTrio = true;

        for (Carta c : combo) {

            if (!c.esJoker()) {

                if (valorBase == null) {
                    valorBase = c.getValor();
                }
                else if (c.getValor() != valorBase) {
                    esTrio = false;
                    break;
                }
            }
        }

        if (esTrio) {
            return true;
        }

        // ===== ESCALERA =====

        ArrayList<Carta> normales = new ArrayList<>();
        int jokers = 0;

        for (Carta c : combo) {

            if (c.esJoker()) {
                jokers++;
            } else {
                normales.add(c);
            }
        }

        if (normales.isEmpty()) {
            return true;
        }

        Palo paloBase = normales.get(0).getPalo();

        for (Carta c : normales) {

            if (c.getPalo() != paloBase) {
                return false;
            }
        }

        Collections.sort(normales, (a, b) ->
                a.getValor().getNumero()
                        - b.getValor().getNumero());

        int faltantes = 0;

        for (int i = 1; i < normales.size(); i++) {

            int anterior =
                    normales.get(i - 1).getValor().getNumero();

            int actual =
                    normales.get(i).getValor().getNumero();

            faltantes += (actual - anterior - 1);
        }

        return faltantes <= jokers;
    }

    public int calcularPuntos(ArrayList<Carta> cartas) {

        int puntos = 0;

        for (Carta c : cartas) {

            int valor = c.getValor().getNumero();

            if (valor > 10) {
                puntos += 10;
            } else {
                puntos += valor;
            }
        }

        return puntos;
    }
}