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

    public boolean indiceValido(int i) {
        return i >= 0 && i < mano.size();
    }

    public ArrayList<Carta> seleccionarCartas(int[] indices) {

        ArrayList<Carta> seleccionadas = new ArrayList<>();

        for (int i = 0; i < indices.length; i++) {

            if (!indiceValido(indices[i])) {
                return null;
            }

            for (int j = i + 1; j < indices.length; j++) {

                if (indices[i] == indices[j]) {
                    return null;
                }
            }

            seleccionadas.add(mano.get(indices[i]));
        }

        return seleccionadas;
    }

    public boolean esTrio(ArrayList<Carta> cartas) {

        if (cartas.size() < 3) {
            return false;
        }

        Valor valorBase = null;

        for (Carta c : cartas) {

            if (!c.esJoker()) {

                valorBase = c.getValor();
                break;
            }
        }

        if (valorBase == null) {
            return true;
        }

        for (Carta c : cartas) {

            if (!c.esJoker() && c.getValor() != valorBase) {
                return false;
            }
        }

        return true;
    }

    public boolean esEscalera(ArrayList<Carta> cartas) {

        if (cartas.size() < 3) {
            return false;
        }

        ArrayList<Carta> normales = new ArrayList<>();
        int jokers = 0;

        for (Carta c : cartas) {

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

        normales.sort((a, b) ->
                a.getValor().getNumero()
                        - b.getValor().getNumero()
        );

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

    public void quitarCartas(ArrayList<Carta> cartas) {
        mano.removeAll(cartas);
    }

    public void ordenarPorValor() {
        mano.sort((a, b) ->
                a.getValor().getNumero() - b.getValor().getNumero()
        );
    }

    public void ordenarPorPalo() {
        mano.sort((a, b) -> {

            int comp = a.getPalo().compareTo(b.getPalo());

            if (comp == 0) {
                return a.getValor().getNumero()
                        - b.getValor().getNumero();
            }

            return comp;
        });
    }

    private boolean primeraBajadaHecha = false;

    public boolean haHechoPrimeraBajada() {
        return primeraBajadaHecha;
    }

    public void marcarPrimeraBajada() {
        primeraBajadaHecha = true;
    }
}