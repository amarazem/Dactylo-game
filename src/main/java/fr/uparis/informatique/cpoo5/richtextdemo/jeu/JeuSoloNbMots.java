package fr.uparis.informatique.cpoo5.richtextdemo.jeu;

public class JeuSoloNbMots extends Jeu {


    public JeuSoloNbMots(String nom, int m, int t) {
        super(nom, m);
        file = Fifo.initialisationFileSolo();
        limite = t;
    }


}
