package fr.uparis.informatique.cpoo5.richtextdemo.jeu;
public abstract class Jeu {
    private Joueur joueur;
    private int mode; // 0 -> solo mode normal | 1 -> solo mode jeu | 2-> multijoueur
    protected Fifo file;
    protected int temps;
    protected int n = 0;
    protected int limite;


    public Jeu(String nom, int m){
        mode = m;
        if(!nom.equals("")) joueur = new Joueur(nom.toUpperCase());
        else joueur = new Joueur("JOUEUR");
    }


    public Joueur getJoueur() {
        return joueur;
    }


    public int getMode() {
        return mode;
    }

    public Fifo getFile() {
        return file;
    }


    public int getTemps() {
        return temps;
    }
    
    public int getN() {
        return n;
    }

    public double f(){
        return (3 * Math.pow(0.9, n));
    }
    

    public void setN() {
        n++;
    }

    public int getLimite() {
        return limite;
    }
    


}
