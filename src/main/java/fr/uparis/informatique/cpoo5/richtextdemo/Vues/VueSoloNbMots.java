package fr.uparis.informatique.cpoo5.richtextdemo.Vues;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class VueSoloNbMots extends Vue{


    private ControleurSoloNbMots controleur;
    private ModeleSoloNbMots modele;
    

    public VueSoloNbMots(JeuSoloNbMots j){
        super();
        modele = new ModeleSoloNbMots(j);
        controleur = new ControleurSoloNbMots(this, modele);
        
        

        nom = new Text(modele.getJeu().getJoueur().getNom());

        limite = new Label(Integer.toString(controleur.getLimite()));

        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/bulle.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl, 30, 30, true, true);
            sablier = new ImageView(image);
            timer_sablier.getChildren().add(sablier);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }
        timer_sablier.getChildren().add(limite);

        infosJoueurs.getChildren().addAll(nom, infos, timer_sablier);



        /*************************************Tableau de Jeu***************************************/
        
        
        ArrayList<String> file_affichage = modele.getJeu().getFile().toList();
        
        file_affichage.stream().forEach(val -> {
            Label mot = new Label(val);
            mot.getStyleClass().add("ligne");
            boxFile.getChildren().add(mot); 
            boxFile.getChildren().add(new Label("  ")); 
        });
    
        limite.getStyleClass().add("time");
        nom.getStyleClass().add("time");
        
    }
    
}
