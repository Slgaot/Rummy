import java.util.ArrayList;

public class Reorganizacion {

    private ArrayList<Carta> cartas;
    private ArrayList<ArrayList<Carta>> nuevasCombinaciones;

    public Reorganizacion() {

        cartas = new ArrayList<>();
        nuevasCombinaciones = new ArrayList<>();
    }

    public void agregarCartas(ArrayList<Carta> lista) {

        cartas.addAll(lista);
    }

    public void agregarCarta(Carta carta) {

        cartas.add(carta);
    }

    public ArrayList<Carta> getCartas() {

        return cartas;
    }

    public ArrayList<ArrayList<Carta>> getCombinaciones() {

        return nuevasCombinaciones;
    }

    public void mostrarCartas() {

        System.out.println();

        System.out.println("Cartas disponibles:");

        for(int i=0;i<cartas.size();i++){

            System.out.println(i+" -> "+cartas.get(i));
        }
    }

    public void mostrarCombinaciones(){

        System.out.println();

        System.out.println("Combinaciones nuevas:");

        for(int i=0;i<nuevasCombinaciones.size();i++){

            System.out.print(i+" -> [ ");

            for(Carta c:nuevasCombinaciones.get(i)){

                System.out.print(c+" ");
            }

            System.out.println("]");
        }
    }

    public void agregarNuevaCombinacion(ArrayList<Carta> combo){

        nuevasCombinaciones.add(combo);

        cartas.removeAll(combo);
    }

    public boolean quedanCartas(){

        return !cartas.isEmpty();
    }

    public void limpiar(){

        cartas.clear();

        nuevasCombinaciones.clear();
    }

}