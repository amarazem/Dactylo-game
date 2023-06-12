package fr.uparis.informatique.cpoo5.richtextdemo.Vues;
import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;

import java.io.File;
import java.net.MalformedURLException;

import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public abstract class Vue extends Scene{
    protected Text nom = new Text();
    protected Label infos = new Label();
    protected Label time = new Label();
    protected Label limite = new Label();
    protected Label vies = new Label();
    protected Label ligne = new Label();
    protected Label vitesse = new Label();
    protected Label precision = new Label();
    protected Label motsTrouves = new Label();
    protected Label motsErrones = new Label();
    protected Label finDePartie = new Label();
    protected ImageView ruban;
    protected ImageView sablier;
    protected FlowPane boxFile = new FlowPane();
    protected HBox timer_sablier = new HBox();
    protected HBox infosJoueurs = new HBox();      
    protected VBox top = new VBox();
    protected HBox box1Stats = new HBox();
    protected HBox box2Stats = new HBox();
    protected VBox vboxStats = new VBox();

    protected StyleClassedTextArea zone_ecriture = new StyleClassedTextArea();
    protected static BorderPane pane = new BorderPane();

    public Vue(){
        super(pane, 800, 800);
        timer_sablier.setSpacing(10);
        timer_sablier.setAlignment(Pos.CENTER);

        infosJoueurs.setSpacing(200);
        infosJoueurs.setAlignment(Pos.CENTER);
        
        
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/ruban.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl, 800, 150, false, true);
            ruban = new ImageView(image);
            top.getChildren().add(ruban);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }
        top.getChildren().add(infosJoueurs);

        top.setSpacing(10);
        top.setAlignment(Pos.CENTER);
        
        pane.setTop(top);

        zone_ecriture.getStyleClass().add("zone_ecriture");
        zone_ecriture.setMaxWidth(400);
        zone_ecriture.setMaxHeight(50);

        VBox box = new VBox();
        box.setSpacing(30);
        box.getChildren().addAll( boxFile, zone_ecriture);
        box.setAlignment(Pos.CENTER);
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/fond-parchemin.jpg");
            String localUrl = file.toURI().toURL().toString();
            BackgroundImage myBI= new BackgroundImage(new Image(localUrl,800,480,false,false),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
            box.setBackground(new Background(myBI));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }
        pane.setCenter(box);


        /*************************************Stats jeu *******************************************/
            

        
        box1Stats.setSpacing(200);
        box1Stats.getChildren().addAll(vitesse, precision);
        box1Stats.setAlignment(Pos.TOP_LEFT);

        box2Stats.setSpacing(200);
        box2Stats.getChildren().addAll(motsTrouves, motsErrones);
        box2Stats.setAlignment(Pos.TOP_LEFT);

        
        vboxStats.setSpacing(30);
        vboxStats.getChildren().addAll(box1Stats, box2Stats);
        vboxStats.setAlignment(Pos.TOP_LEFT);

        pane.setBottom(vboxStats);

/*************************************Finalisation ****************************************/

        pane.setPadding(new Insets(0, 0, 50, 0));
        time.getStyleClass().add("time");
        nom.getStyleClass().add("time");
        limite.getStyleClass().add("time");
        vitesse.getStyleClass().add("stats");
        precision.getStyleClass().add("stats");
        motsTrouves.getStyleClass().add("stats");
        motsErrones.getStyleClass().add("stats");
        
    }

    public Label getTime() {
        return time;
    }

    public static BorderPane getPane() {
        return pane;
    }

    public StyleClassedTextArea getZone_ecriture() {
        return zone_ecriture;
    }

    public Label getLigne() {
        return ligne;
    }

    public Label getVitesse() {
        return vitesse;
    }
    public Label getPrecision() {
        return precision;
    }
    public Label getMotsErrones() {
        return motsErrones;
    }
    public Label getMotsTrouves() {
        return motsTrouves;
    }
    
    public FlowPane getBoxFile() {
        return boxFile;
    }

    public Label getLimite() {
        return limite;
    }
    public Label getVies() {
        return vies;
    }
    public Label getInfos() {
        return infos;
    }
}
