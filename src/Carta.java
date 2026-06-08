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

        if (esJoker()) {
            return "\u001B[35mJOKER ★\u001B[0m";
        }

        String color;

        switch (palo) {
            case CORAZONES:
            case DIAMANTES:
                color = "\u001B[31m";
                break;

            default:
                color = "\u001B[30m";
        }

        return color + valor.getSimbolo() + " " + palo.getSimbolo() + "\u001B[0m";
    }

    public boolean esJoker() {
        return valor == Valor.JOKER;
    }

}
