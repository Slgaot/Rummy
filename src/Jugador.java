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

    public ArrayList<Carta> seleccionarCartas(int[] indices) {

        ArrayList<Carta> seleccionadas = new ArrayList<>();

        for (int i = 0; i < indices.length; i++) {
            seleccionadas.add(mano.get(indices[i]));
        }

        return seleccionadas;
    }

    public boolean esTrio(ArrayList<Carta> cartas) {

        if (cartas.size() < 3) {
            return false;
        }

        Valor valorBase = cartas.get(0).getValor();

        for (int i = 1; i < cartas.size(); i++) {

            if (cartas.get(i).getValor() != valorBase) {
                return false;
            }
        }

        return true;
    }

    public boolean esEscalera(ArrayList<Carta> cartas) {

        if (cartas.size() < 3) {
            return false;
        }

        Palo paloBase = cartas.get(0).getPalo();

        for (int i = 1; i < cartas.size(); i++) {

            if (cartas.get(i).getPalo() != paloBase) {
                return false;
            }
        }

        cartas.sort((a, b) ->
                a.getValor().getNumero() - b.getValor().getNumero()
        );

        for (int i = 1; i < cartas.size(); i++) {

            int anterior = cartas.get(i - 1).getValor().getNumero();
            int actual = cartas.get(i).getValor().getNumero();

            if (actual != anterior + 1) {
                return false;
            }
        }

        return true;
    }

    public void quitarCartas(ArrayList<Carta> cartas) {
        mano.removeAll(cartas);
    }
}