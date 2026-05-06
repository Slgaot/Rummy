import java.util.ArrayList;

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
            System.out.print("[ ");
            for (Carta c : combinaciones.get(i)) {
                System.out.print(c + " ");
            }
            System.out.println("]");
        }
    }
}
