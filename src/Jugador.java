import java.util.ArrayList;
import java.util.Collections;

public class Jugador {

    private String nombre;
    private ArrayList<Carta> mano;

    public Jugador (String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public String getNombre() {
        return nombre;
    }

    public void recibirCarta(Carta carta){
        mano.add(carta);
    }

    public void mostrarMano() {
        System.out.println("Mano de " + nombre + ":");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(i + ": " + mano.get(i));
        }
    }
    public int cantidadCartas() {
        return mano.size();
    }

    public Carta descartarCarta(int indice) {
        if (indice >= 0 && indice < mano.size()) {
            return mano.remove(indice);
        }
        return null;
    }

    public boolean tieneTrio() {

        for (int i = 0; i < mano.size(); i++) {

            int contador = 0;
            Valor valorActual = mano.get(i).getValor();

            for (int j = 0; j < mano.size(); j++) {
                if (mano.get(j).getValor() == valorActual) {
                    contador++;
                }
            }

            if (contador >= 3) {
                return true;
            }
        }

        return false;
    }

    public boolean tieneEscalera() {

        for (int i = 0; i < mano.size(); i++) {

            Palo paloBase = mano.get(i).getPalo();

            ArrayList<Integer> numeros = new ArrayList<>();

            for (int j = 0; j < mano.size(); j++) {
                if (mano.get(j).getPalo() == paloBase) {
                    numeros.add(mano.get(j).getValor().getNumero());
                }
            }

            Collections.sort(numeros);

            int consecutivas = 1;

            for (int k = 1; k < numeros.size(); k++) {
                if (numeros.get(k) == numeros.get(k - 1) + 1) {
                    consecutivas++;
                    if (consecutivas >= 3) {
                        return true;
                    }
                } else {
                    consecutivas = 1;
                }
            }
        }

        return false;
    }
    public ArrayList<Carta> obtenerTrio() {

        ArrayList<Carta> trio = new ArrayList<>();

        for (int i = 0; i < mano.size(); i++) {

            Valor valorActual = mano.get(i).getValor();
            trio.clear();

            for (int j = 0; j < mano.size(); j++) {
                if (mano.get(j).getValor() == valorActual) {
                    trio.add(mano.get(j));
                }
            }

            if (trio.size() >= 3) {

                for (Carta c : trio) {
                    mano.remove(c);
                }

                return trio;
            }
        }

        return null;
    }

    public ArrayList<Carta> obtenerEscalera() {

        for (int i = 0; i < mano.size(); i++) {

            Palo paloBase = mano.get(i).getPalo();

            ArrayList<Carta> posibles = new ArrayList<>();

            for (int j = 0; j < mano.size(); j++) {
                if (mano.get(j).getPalo() == paloBase) {
                    posibles.add(mano.get(j));
                }
            }

            posibles.sort((a, b) -> a.getValor().getNumero() - b.getValor().getNumero());

            ArrayList<Carta> escalera = new ArrayList<>();
            escalera.add(posibles.get(0));

            for (int k = 1; k < posibles.size(); k++) {

                int actual = posibles.get(k).getValor().getNumero();
                int anterior = posibles.get(k - 1).getValor().getNumero();

                if (actual == anterior + 1) {
                    escalera.add(posibles.get(k));

                    if (escalera.size() >= 3) {

                        // quitar de la mano
                        mano.removeAll(escalera);

                        return escalera;
                    }
                } else {
                    escalera.clear();
                    escalera.add(posibles.get(k));
                }
            }
        }

        return null;
    }
}