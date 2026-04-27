public class Juego {
    public static void main (String [] args){
        Carta carta = new Carta(Valor.AS, Palo.CORAZONES);
        System.out.println(carta);

        Mazo mazo = new Mazo();

        Carta c1 = mazo.robarCarta();
        Carta c2 = mazo.robarCarta();

        System.out.println(c1);
        System.out.println(c2);

        System.out.println("Quedan: " + mazo.cartasRestantes());
    }
}
