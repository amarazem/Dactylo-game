package fr.uparis.informatique.cpoo5.richtextdemo.Vues;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class VueSoloNormal extends Vue{
    
    private ControleurSoloNormal controleur;
    private ModeleSoloNormal modele;
    
    public VueSoloNormal(JeuSolo j){
        
        modele = new ModeleSoloNormal(j);
        controleur = new ControleurSoloNormal(this, modele);
        
        nom = new Text(modele.getJeu().getJoueur().getNom());

        if (String.valueOf(controleur.getTemps() % 60).length() == 1) {
            time = new Label("0" + controleur.getTemps() / 60 + ":0" + controleur.getTemps() % 60);
        } else {
            time = new Label("0" + controleur.getTemps() / 60 + ":" + controleur.getTemps() % 60);
        }

       
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/sablier.jpg");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl, 30, 30, true, true);
            sablier = new ImageView(image);
            timer_sablier.getChildren().add(sablier);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }
        timer_sablier.getChildren().add(time);

        
        infosJoueurs.getChildren().addAll(nom, infos, timer_sablier);
        

        /*************************************Tableau de Jeu***************************************/
        
        ArrayList<String> file_affichage = modele.getJeu().getFile().toList();
        file_affichage.stream().forEach(val -> {
            Label mot = new Label(val);
            mot.getStyleClass().add("ligne");
            boxFile.getChildren().add(mot); 
            boxFile.getChildren().add(new Label("  ")); 
        });
        
        
        /*************************************Finalisation ****************************************/
        time.getStyleClass().add("time");
        nom.getStyleClass().add("time");
        
        
    }

}
