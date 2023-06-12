package fr.uparis.informatique.cpoo5.richtextdemo.jeu;
public class Joueur {
    private String nom;
    private int vies;

    public Joueur(String s){
        nom = s;
        vies = 100;
    }

    public int getVies() {
        return vies;
    }

    public String getNom() {
        return nom;
    }

    public void viesEnMoins(int nb){
        vies -= nb;
    }

    public void viesEnPlus(int nb){
        vies += nb;
    }
}
