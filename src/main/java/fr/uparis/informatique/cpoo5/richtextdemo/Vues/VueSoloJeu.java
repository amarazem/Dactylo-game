package fr.uparis.informatique.cpoo5.richtextdemo.Vues;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.Fifo.Noeud;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.net.MalformedURLException;

public class VueSoloJeu extends Vue{

    private ControleurSoloJeu controleur;
    private ModeleSoloJeu modele;
    

    public VueSoloJeu(JeuSoloModeJeu j){

        super();
        modele = new ModeleSoloJeu(j);
        controleur = new ControleurSoloJeu(this, modele);
        
        nom = new Text(modele.getJeu().getJoueur().getNom());
        vies = new Label(Integer.toString(modele.getJeu().getJoueur().getVies()));


        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/coeur.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl, 30, 30, true, true);
            sablier = new ImageView(image);
            timer_sablier.getChildren().add(sablier);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur coeur");
        }
        timer_sablier.getChildren().add(vies);


        infosJoueurs.getChildren().addAll(nom, infos, timer_sablier);
  
        /*************************************Tableau de Jeu***************************************/
    
        
        Fifo file_affichage = modele.getJeu().getFile();
        for (int i = 0; i < file_affichage.getSize(); i++) {
            Noeud noeud = file_affichage.get(i);
            Label mot = new Label(noeud.getData());
            if(noeud.getBleue()) mot.getStyleClass().add("ligne_bleue");
            else mot.getStyleClass().add("ligne");
            
            boxFile.getChildren().add(mot); 
            boxFile.getChildren().add(new Label("  "));        
        }

        vies.getStyleClass().add("time");
        nom.getStyleClass().add("time");
        infos.getStyleClass().add("ligne_rouge");
        
 
    }
  
}
