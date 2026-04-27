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

        for (int i = 0; i < Palo.values().length; i++) {
            for (int j = 0; j < Valor.values().length; j++) {

                Palo palo = Palo.values()[i];
                Valor valor = Valor.values()[j];

                cartas.add(new Carta(valor, palo));
            }
        }
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

    public int cartasRestantes() {
        return cartas.size();
    }
}

