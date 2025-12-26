abstract class CompteBancaire {
    protected String numCompte;
    protected double solde;
    protected String nomTitulaire;

    public CompteBancaire(String num, String nom, double soldeInit) {
        this.numCompte = num;
        this.nomTitulaire = nom;
        this.solde = soldeInit;
    }

    public void deposer(double montant) { this.solde += montant; }

    public abstract void retirer(double montant) throws FondsInsuffisantsException;

    public void afficherSolde() {
        System.out.println("Compte " + numCompte + " | Solde: " + solde);
    }

    public String getNumCompte() { return numCompte; }
}

class CompteCourant extends CompteBancaire {
    private double decouvertAutorise;

    public CompteCourant(String num, String nom, double solde, double decouvert) {
        super(num, nom, solde);
        this.decouvertAutorise = decouvert;
    }

    @Override
    public void retirer(double m) throws FondsInsuffisantsException {
        if (solde + decouvertAutorise < m) throw new FondsInsuffisantsException("Découvert dépassé");
        solde -= m;
    }
}

class CompteEpargne extends CompteBancaire {
    private double tauxInteret = 0.05;

    public CompteEpargne(String num, String nom, double solde) { super(num, nom, solde); }

    @Override
    public void retirer(double m) throws FondsInsuffisantsException {
        if (solde < m) throw new FondsInsuffisantsException("Solde insuffisant");
        solde -= m;
    }

    public void calculerInteret() { solde += solde * tauxInteret; }
}
