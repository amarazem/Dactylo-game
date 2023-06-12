package fr.uparis.informatique.cpoo5.richtextdemo.Controleur;


import java.util.concurrent.CompletableFuture;

import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Vues.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class ControleurSoloJeu extends Controleur{
  private VueSoloJeu vue;
  private ModeleSoloJeu modele;

  public ControleurSoloJeu(VueSoloJeu v, ModeleSoloJeu m){
    super(v, m);
    vue = v;
    modele = m;
    vitesse = modele.getJeu().f(); 
    vies = modele.getJeu().getJoueur().getVies();
      
    CompletableFuture futureVitesse = CompletableFuture.supplyAsync(() -> 
      new Timeline(
      new KeyFrame(Duration.seconds(vitesse), event -> updateTimelineVitesse()))
      ).thenAccept(value -> {
        timelineVitesse = value;
        timelineVitesse.setCycleCount(Animation.INDEFINITE);
        timelineVitesse.play();
    });

    CompletableFuture futureTemps = CompletableFuture.supplyAsync(() -> 
    new Timeline(
    new KeyFrame(Duration.seconds(1), event -> updateTimelineTempsDeJeu()))
    ).thenAccept(value -> {
      timelineTempsDeJeu = value;
      timelineTempsDeJeu.setCycleCount(Animation.INDEFINITE);
      timelineTempsDeJeu.play();
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

/***********************************validation***************************************/
  public void validation(String mot){
    if(Fifo.verifNbFautes(mot, modele.getJeu().getFile().getHead()) == 0){
      if(modele.getJeu().getFile().getHeadBleu()) {
        modele.getJeu().getJoueur().viesEnPlus(mot.length());
      }
      this.nbMotsCorrect++;
      if((nbMotsCorrect % 3) == 0){
        modele.getJeu().setN();
        vitesse = modele.getJeu().f();
        vue.getInfos().setText((Math.round(vitesse*100.0)/100.0) + " secondes!");
        hideInfos();
        timelineVitesse.stop();
        timelineVitesse = new Timeline(
        new KeyFrame(Duration.seconds(vitesse), e -> updateTimelineVitesse()));
        timelineVitesse.setCycleCount(Animation.INDEFINITE);
        timelineVitesse.play();
      }
      
    }else{
      modele.getJeu().getJoueur().viesEnMoins(Fifo.verifNbFautes(mot, modele.getJeu().getFile().getHead()));
      if (modele.getJeu().getJoueur().getVies() <= 0) {
        vue.getVies().setText(Integer.toString(0));
        partieTerminee();
        timelineTempsDeJeu.stop();
        timelineVitesse.stop();
        timelineTempsDeJeuMilis.stop();  
        return;
      }
      this.nbMotsErrones++;
    }       
    vue.getVies().setText(Integer.toString(modele.getJeu().getJoueur().getVies()));
    vue.getZone_ecriture().clear();
    modele.getJeu().getFile().suppElement();
    vue.getBoxFile().getChildren().remove(0, 2);
    cpt = 0;
  }
    
}
