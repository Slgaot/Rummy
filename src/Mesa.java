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

        if (combo.size() < 3) return false;

        boolean mismoValor = true;

        Valor v = combo.get(0).getValor();

        for (Carta c : combo) {
            if (c.getValor() != v) {
                mismoValor = false;
                break;
            }
        }

        if (mismoValor) return true;

        ArrayList<Carta> copia = new ArrayList<>(combo);

        Collections.sort(copia, (a, b) ->
                a.getValor().getNumero() - b.getValor().getNumero()
        );

        Palo p = copia.get(0).getPalo();

        for (Carta c : copia) {
            if (c.getPalo() != p) {
                return false;
            }
        }

        for (int i = 1; i < copia.size(); i++) {

            int anterior = copia.get(i - 1).getValor().getNumero();
            int actual = copia.get(i).getValor().getNumero();

            if (actual != anterior + 1) {
                return false;
            }
        }

        return true;
    }
}