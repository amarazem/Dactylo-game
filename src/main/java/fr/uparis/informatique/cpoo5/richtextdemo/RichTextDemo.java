package fr.uparis.informatique.cpoo5.richtextdemo;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Vues.*;


import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;


import fr.uparis.informatique.cpoo5.tcpdemo.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class RichTextDemo extends Application {
    private Scene Solo;
    private Scene accueil;
    private Button soloNormal;
    private Button soloJeu;
    private Button modeMots;
    private Button start;
    private Spinner s = new Spinner<>();
    private Text choixTemps = new Text("Séléctionnez le temps de jeu");
    private Text choixMots = new Text("Séléctionnez la limite de mots");
    private static boolean normal = false;
    private static boolean jeu = false;
    private static boolean normalTemps = false;
    private static boolean normalMots = false;

    public void start(Stage primaryStage) throws Exception{

        Text titre = new Text("Bienvenu sur Dactylo game");
        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().addAll(titre);
        textFlow.setTextAlignment(TextAlignment.CENTER);
        
        Text labelNom = new Text("Saisissez votre nom");
        TextField nom = new TextField("");
        nom.setMaxWidth(120);
        VBox boxNom = new VBox();
        boxNom.setSpacing(30);
        boxNom.getChildren().addAll(labelNom, nom);
        boxNom.setAlignment(Pos.CENTER);
        
        Text choixMode = new Text("Selectionnez votre mode de jeu");

        Button solo = new Button("solo");
        solo.setOnAction(event -> primaryStage.setScene(Solo));
        solo.setMinWidth(120);
        Button multi = new Button("multi");
        multi.setOnAction(event -> {
            JeuMulti j = new JeuMulti(nom.getText(), 2);
            VueMulti v = new VueMulti(j);
            v.getStylesheets().add(getClass().getResource("style.css").toExternalForm());                                                              
            primaryStage.setScene(v);
        });
        multi.setMinWidth(120);

        HBox buttons = new HBox();
        buttons.setSpacing(50);
        buttons.getChildren().addAll( solo, multi);
        buttons.setAlignment(Pos.CENTER);

        VBox boxMode = new VBox();
        boxMode.setSpacing(30);
        boxMode.getChildren().addAll(choixMode, buttons);
        boxMode.setAlignment(Pos.CENTER);
        
        VBox centre = new VBox();
        centre.setSpacing(100);
        centre.getChildren().addAll(boxNom, boxMode);
        centre.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(50, 0, 0, 0));
        pane.setTop(textFlow);
        pane.setCenter(centre);
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/fond-parchemin.jpg");
            String localUrl = file.toURI().toURL().toString();
            BackgroundImage myBI= new BackgroundImage(new Image(localUrl,800,800,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
            pane.setBackground(new Background(myBI));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }
/********************** parametrage Solo ********************************************/
        HBox topSolo = new HBox();
        topSolo.setAlignment(Pos.TOP_LEFT);
        Button retour = new Button();
        retour.setOnAction(e -> primaryStage.setScene(accueil));
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/fleche.png");
            String localUrl = file.toURI().toURL().toString();
            Image image = new Image(localUrl,90,50,false,true);
            ImageView icon = new ImageView(image); 
            retour.setGraphic(icon);

            
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur fleche");
        }
        topSolo.getChildren().add(retour);

        VBox centreSolo = new VBox();
        centreSolo.setSpacing(30);

        Text choixModeSolo = new Text("Encore quelques précisions avant de commencer");

        Button modeTemps = new Button("Mode temps");
        modeTemps.setOnAction(event -> {
            start.setDisable(false);
            normalTemps = true;
            if(normalMots) normalMots = false;
            modeTemps.setOpacity(1);
            modeMots.setOpacity(0.5);
            s = new Spinner<Integer>(3, 10, 3);
            if(centreSolo.getChildren().size() == 6){
                centreSolo.getChildren().remove(3, 5);
            }
            centreSolo.getChildren().add(3, s);
            centreSolo.getChildren().add(3, choixTemps);
        

        });
        modeMots = new Button("Mode mots");
        modeMots.setOnAction(event -> {
            start.setDisable(false);
            normalMots = true;
            if(normalTemps) normalTemps = false;
            modeTemps.setOpacity(0.5);
            modeMots.setOpacity(1);
            s = new Spinner<>(50, 300, 50, 50);
            if(centreSolo.getChildren().size() == 6){
                centreSolo.getChildren().remove(3, 5);
            }
            centreSolo.getChildren().add(3, s);
            centreSolo.getChildren().add(3, choixMots);
        
        });
        HBox buttonsSoloNormal = new HBox();
        buttonsSoloNormal.setSpacing(50);
        buttonsSoloNormal.getChildren().addAll( modeTemps, modeMots);
        buttonsSoloNormal.setAlignment(Pos.CENTER);

        start = new Button("C'est parti!");
        start.setOnAction(event -> {
            if(normal) {
                if(normalTemps){
                    JeuSolo j = new JeuSolo(nom.getText(), 0, ((int)s.getValue())*60);
                    VueSoloNormal v = new VueSoloNormal(j);
                    v.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    primaryStage.setScene(v);
                }
                else if(normalMots){
                    JeuSoloNbMots j = new JeuSoloNbMots(nom.getText(), 0, ((int)s.getValue()));
                    VueSoloNbMots v = new VueSoloNbMots(j);
                    v.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                    primaryStage.setScene(v);
                }
                
            }
            else if(jeu){
                JeuSoloModeJeu j = new JeuSoloModeJeu(nom.getText(), 1);
                VueSoloJeu v = new VueSoloJeu(j);
                v.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                primaryStage.setScene(v);
            }
        });
        start.setDisable(true);

        soloNormal = new Button("Mode Solo Normal");
        soloNormal.setOnAction(event -> {
            normal = true;
            if(jeu) jeu = false;
            soloNormal.setOpacity(1);
            soloJeu.setOpacity(0.5);
            if(centreSolo.getChildren().size() == 3) centreSolo.getChildren().add(2,buttonsSoloNormal);
            
        });
        soloNormal.setMinWidth(120);
        soloJeu = new Button("Mode Solo Jeu");
        soloJeu.setOnAction(event -> { 
            start.setDisable(false);
            jeu = true;
            if(normal) normal = false;
            modeTemps.setOpacity(1);
            modeMots.setOpacity(1);
            soloNormal.setOpacity(0.5);
            soloJeu.setOpacity(1);
            if(centreSolo.getChildren().size() == 4) centreSolo.getChildren().remove(2);
            else if(centreSolo.getChildren().size() == 6) centreSolo.getChildren().remove(2, 5);
            
        });
        soloJeu.setMinWidth(120);
        HBox buttonsSolo = new HBox();
        buttonsSolo.setSpacing(50);
        buttonsSolo.getChildren().addAll( soloNormal, soloJeu);
        buttonsSolo.setAlignment(Pos.CENTER);

        
        
        centreSolo.getChildren().addAll(choixModeSolo, buttonsSolo, start);
        centreSolo.setAlignment(Pos.CENTER);
       

        VBox paramSolo = new VBox();
        paramSolo.setAlignment(Pos.TOP_CENTER);
        paramSolo.setSpacing(300);
        paramSolo.getChildren().addAll(topSolo, centreSolo);
        paramSolo.setPadding(new Insets(20, 0, 0, 20));
        try {
            File file = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/images/fond-parchemin.jpg");
            String localUrl = file.toURI().toURL().toString();
            BackgroundImage myBI= new BackgroundImage(new Image(localUrl,800,800,false,true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
            paramSolo.setBackground(new Background(myBI));
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            System.out.println("erreur sablier");
        }

        Solo = new Scene(paramSolo, 800, 800);
/************************Finalisation ***********************************************/
        accueil = new Scene(pane, 800, 800);
        primaryStage.setScene(accueil);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        accueil.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        Solo.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        titre.getStyleClass().add("titre");
        labelNom.getStyleClass().add("labelNom_choixMode");
        choixMode.getStyleClass().add("labelNom_choixMode");
        choixModeSolo.getStyleClass().add("labelNom_choixMode");
        solo.getStyleClass().add("solo");
        multi.getStyleClass().add("multi");
        soloNormal.getStyleClass().add("solo");
        s.getStyleClass().add("spinner");
        choixTemps.getStyleClass().add("labelNom_choixMode");
        choixMots.getStyleClass().add("labelNom_choixMode");
        soloJeu.getStyleClass().add("multi");
        modeTemps.getStyleClass().add("solo");
        modeMots.getStyleClass().add("multi");
        start.getStyleClass().add("solo");
        retour.getStyleClass().add("retour");
    }

    public static void main(String[] args) {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> "Dactylo game").thenAccept(value -> {
            System.out.println(value);
            Application.launch(args);
        });
        
    }

    

}

