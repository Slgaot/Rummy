import java.util.ArrayList;
public class Descarte {

    private static ArrayList<Carta> pila;

    public Descarte() {
        pila = new ArrayList<>();
    }

    public void descartar(Carta carta) {
        pila.add(carta);
    }

    public static Carta robar() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.remove(pila.size() - 1);
    }

    public Carta verTope() {
        if (pila.isEmpty()) {
            return null;
        }
        return pila.get(pila.size() - 1);
    }
}

