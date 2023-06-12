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


public class ControleurSoloNormal extends Controleur{
  private VueSoloNormal vue;
  private ModeleSoloNormal modele;

  public ControleurSoloNormal(VueSoloNormal v, ModeleSoloNormal m){
    super(v, m);
    vue = v;
    modele = m;
    temps = modele.getJeu().getTemps();
    
    CompletableFuture future = CompletableFuture.supplyAsync(() -> 
    new Timeline(
    new KeyFrame(Duration.seconds(1), event -> updateTimeline()))
    ).thenAccept(value -> {
    timeline = value;
    timeline.setCycleCount(Animation.INDEFINITE);
    timeline.play();
    });

    CompletableFuture futureTempsMilis = CompletableFuture.supplyAsync(() -> 
    new Timeline(
    new KeyFrame(Duration.millis(1), event -> updateTimelineTempsDeJeuMilis()))
    ).thenAccept(value -> {
      timelineTempsDeJeuMilis = value;
      timelineTempsDeJeuMilis.setCycleCount(Animation.INDEFINITE);
      timelineTempsDeJeuMilis.play();
    });
    

  } 

    
  @Override
  public void validation(String mot) {
    if(mot.equals(modele.getJeu().getFile().getHead())){
      this.nbMotsCorrect++;
    }else{
      this.nbMotsErrones++;
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
