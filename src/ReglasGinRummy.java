public class ReglasGinRummy extends Reglas{

    public int cartasIniciales(){
        return 10;
    }

    public boolean usaJoker(){
        return false;
    }

    public boolean primeraBajada(){
        return false;
    }

    public int puntosPrimeraBajada(){
        return 0;
    }

    public boolean reorganizarMesa(){
        return false;
    }
}