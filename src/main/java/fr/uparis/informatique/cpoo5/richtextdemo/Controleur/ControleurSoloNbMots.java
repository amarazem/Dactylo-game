package fr.uparis.informatique.cpoo5.richtextdemo.Controleur;


import java.util.concurrent.CompletableFuture;

import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Vues.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.Fifo.Noeud;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class ControleurSoloNbMots extends Controleur{
    private VueSoloNbMots vue;
    private ModeleSoloNbMots modele;


    public ControleurSoloNbMots(VueSoloNbMots v, ModeleSoloNbMots m){
      super(v, m);
      vue = v;
      modele = m;
      limite = modele.getJeu().getLimite();
      
      CompletableFuture futureTemps = CompletableFuture.supplyAsync(() -> 
      new Timeline(
      new KeyFrame(Duration.seconds(1), event -> updateTimelineTempsDeJeu()))
      ).thenAccept(value -> {
        timelineTempsDeJeu = value;
        timelineTempsDeJeu.setCycleCount(Animation.INDEFINITE);
        timelineTempsDeJeu.play();
      });

    } 
      CompletableFuture futureTempsMilis = CompletableFuture.supplyAsync(() -> 
      new Timeline(
      new KeyFrame(Duration.millis(1), event -> updateTimelineTempsDeJeuMilis()))
      ).thenAccept(value -> {
        timelineTempsDeJeuMilis = value;
        timelineTempsDeJeuMilis.setCycleCount(Animation.INDEFINITE);
        timelineTempsDeJeuMilis.play();
      });



  @Override
  public void validation(String mot) {
    if(mot.equals(modele.getJeu().getFile().getHead()))
      this.nbMotsCorrect++;
    else 
      this.nbMotsErrones++;
    limite--;
    vue.getLimite().setText(Integer.toString(limite));
    if(limite == 0) {
      timelineTempsDeJeu.stop();
      partieTerminee();
    }
    vue.getZone_ecriture().clear();
    modele.getJeu().getFile().ajoutElement();
    vue.getBoxFile().getChildren().remove(0, 2);
    Noeud noeud = modele.getJeu().getFile().get(modele.getJeu().getFile().getSize()-1);
    Label new_mot = new Label(noeud.getData());
    new_mot.getStyleClass().add("ligne");
    vue.getBoxFile().getChildren().add(new_mot); 
    vue.getBoxFile().getChildren().add(new Label("  ")); 
    cpt = 0;
    
  }

}
