package fr.uparis.informatique.cpoo5.richtextdemo.jeu;

public class JeuSoloModeJeu extends Jeu{

    
    public JeuSoloModeJeu(String nom, int m) {
        super(nom, m);
        file = Fifo.initialisationFileJeu();
    }

    

    
}
