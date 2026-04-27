public class Carta {
    private final Valor valor;
    private final Palo palo;

    public Carta (Valor valor,Palo palo){
        this.valor = valor;
        this.palo = palo;
    }

    public Palo getPalo() {
        return palo;
    }

    public Valor getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}
