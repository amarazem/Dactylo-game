package fr.uparis.informatique.cpoo5.richtextdemo.jeu;

public class JeuSolo extends Jeu{
    
    public JeuSolo(String nom, int m, int t) {
        super(nom, m);
        file = Fifo.initialisationFileSolo();
        temps = t;
    }



    
}
