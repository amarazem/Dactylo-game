package fr.uparis.informatique.cpoo5.richtextdemo.Modele;

import fr.uparis.informatique.cpoo5.richtextdemo.jeu.Jeu;

public abstract class Modele {
    protected Jeu jeu;
    public Modele(Jeu j){
        jeu = j;
    }
    public Jeu getJeu() {
        return jeu;
    }
}

