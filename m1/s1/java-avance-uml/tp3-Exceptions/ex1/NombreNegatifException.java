public class NombreNegatifException extends Exception {
    private int valeurErronee;

    public NombreNegatifException(int valeur) {
        super("Valeur negative non autorisee: " + valeur);
        this.valeurErronee = valeur;
    }

    public int getValeurErronee() {
        return valeurErronee;
    }
}
