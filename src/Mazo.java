import java.util.ArrayList;
import java.util.Collections;

public class Mazo {

    private final ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
        crearMazo();
        barajar();
    }

    private void crearMazo() {

        for (Palo palo : Palo.values()) {

            if (palo == Palo.NINGUNO) {
                continue;
            }

            for (Valor valor : Valor.values()) {

                if (valor == Valor.JOKER) {
                    continue;
                }

                cartas.add(new Carta(valor, palo));
            }
        }

        cartas.add(new Carta(Valor.JOKER, Palo.NINGUNO));
        cartas.add(new Carta(Valor.JOKER, Palo.NINGUNO));
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public Carta robarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(0);
    }

    public boolean estaVacio() {
        return cartas.isEmpty();
    }

    public void agregarCartas(ArrayList<Carta> nuevasCartas) {

        cartas.addAll(nuevasCartas);

        Collections.shuffle(cartas);
    }
}

