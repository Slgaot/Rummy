import java.util.ArrayList;

public class Jugador {

    private String nombre;
    private ArrayList<Carta> mano;

    public Jugador (String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    //con el add añado cartas a la array list, le meto una variable para despues usarla con robarCarta de LA CLASE mazo.
    public void recibirCarta(Carta carta){
        mano.add(carta);
    }

    //metodo para ver la mano del jugador
    public void mostrarMano() {
        System.out.println("Mano de " + nombre + ":");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(mano.get(i));

        }
    }

    public Carta descartarCarta(int indice) {
        if (indice >= 0 && indice < mano.size()) {
            return mano.remove(indice);
        }
        return null;
    }
}
