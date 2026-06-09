public class ReglasRummyArgentino extends Reglas{

    public int cartasIniciales(){
        return 13;
    }

    public boolean usaJoker(){
        return true;
    }

    public boolean primeraBajada(){
        return true;
    }

    public int puntosPrimeraBajada(){
        return 30;
    }

    public boolean reorganizarMesa(){
        return true;
    }
}