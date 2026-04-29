import java.util.ArrayList;

public class Jugador {

    private String nombre;
    private ArrayList<Carta> mano;

    public Jugador (String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public void recibirCarta(Carta carta){
        mano.add(carta);
    }

    public void mostrarMano() {
        System.out.println("Mano de " + nombre + ":");
        for (int i = 0; i < mano.size(); i++) {
            System.out.println(mano.get(i));
        }
    }
}
