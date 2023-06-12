package fr.uparis.informatique.cpoo5.richtextdemo.jeu;

public class JeuMulti extends Jeu{
    
    public JeuMulti(String nom, int m) {
        super(nom, m);
        file = Fifo.initialisationFileJeu();
    }

}
