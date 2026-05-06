import java.util.ArrayList;

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

            for (int j = 0; j < mano.size(); j++) {

                for (int k = 0; k < mano.size(); k++) {

                    if (i != j && j != k && i != k) {

                        Carta c1 = mano.get(i);
                        Carta c2 = mano.get(j);
                        Carta c3 = mano.get(k);

                        if (c1.getPalo() == c2.getPalo() &&
                                c2.getPalo() == c3.getPalo()) {

                            int v1 = c1.getValor().getNumero();
                            int v2 = c2.getValor().getNumero();
                            int v3 = c3.getValor().getNumero();

                            if ((v1 + 1 == v2 && v2 + 1 == v3) ||
                                    (v1 + 1 == v3 && v3 + 1 == v2) ||
                                    (v2 + 1 == v1 && v1 + 1 == v3) ||
                                    (v2 + 1 == v3 && v3 + 1 == v1) ||
                                    (v3 + 1 == v1 && v1 + 1 == v2) ||
                                    (v3 + 1 == v2 && v2 + 1 == v1)) {

                                return true;
                            }
                        }
                    }
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
}