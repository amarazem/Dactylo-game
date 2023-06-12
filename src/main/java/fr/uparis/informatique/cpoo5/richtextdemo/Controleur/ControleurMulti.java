package fr.uparis.informatique.cpoo5.richtextdemo.Controleur;


import java.util.concurrent.CompletableFuture;

import fr.uparis.informatique.cpoo5.richtextdemo.Modele.*;
import fr.uparis.informatique.cpoo5.richtextdemo.Vues.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.*;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.Fifo.Noeud;
import fr.uparis.informatique.cpoo5.tcpdemo.ChatClient;
import fr.uparis.informatique.cpoo5.tcpdemo.ChatServer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class ControleurMulti extends Controleur{
  private VueMulti vue;
  private ModeleMulti modele;
  private boolean partieTerminee = false;
  private boolean isServer = false;
  private String motRouge;

  public boolean getIsServer(){
    return isServer;
  }


  public ControleurMulti(VueMulti v, ModeleMulti m){
    super(v, m);
    vue = v;
    modele = m;
    try {  
      isServer = false; 
      ChatClient.connexionToServer(this);
    } catch (Exception e) {
      try {
        isServer = true;
        ChatServer.serverConnection(this);
      } catch (Exception eServer) {
        System.err.println("Impossible de lancer le serveur");
      }
    }

      
    CompletableFuture futureVitesse = CompletableFuture.supplyAsync(() -> 
      new Timeline(
      new KeyFrame(Duration.seconds(3), event -> updateTimelineVitesse()))
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
  @Override
  public void validation(String mot){
    if(Fifo.verifNbFautes(mot, modele.getJeu().getFile().getHead()) == 0){
      if(modele.getJeu().getFile().getHeadBleu()) {
        modele.getJeu().getJoueur().viesEnPlus(mot.length());
      }
      else if(modele.getJeu().getFile().getHeadRouge()) {
        motRouge = modele.getJeu().getFile().getHead();
      }
      this.nbMotsCorrect++;
      vue.getVies().setText(Integer.toString(modele.getJeu().getJoueur().getVies()));
    }else{
      modele.getJeu().getJoueur().viesEnMoins(Fifo.verifNbFautes(mot, modele.getJeu().getFile().getHead()));
      vue.getVies().setText(Integer.toString(modele.getJeu().getJoueur().getVies()));
      if (modele.getJeu().getJoueur().getVies() <= 0) {
        partieTerminee = true;
        vue.getVies().setText(Integer.toString(0));
        timelineVitesse.stop();
        timelineTempsDeJeu.stop();
        timelineTempsDeJeuMilis.stop();
        partieTerminee(); 
      }
      this.nbMotsErrones++;
    }         
    
    vue.getZone_ecriture().clear();
    modele.getJeu().getFile().suppElement();
    vue.getBoxFile().getChildren().remove(0, 2);
    cpt = 0;
  }


  public void ajoutMot(String s){
    modele.getJeu().getFile().ajouterElementSansSuppression(s);
    Noeud noeud = modele.getJeu().getFile().get(modele.getJeu().getFile().getSize()-1);
    Label new_mot = new Label(noeud.getData());
    if(noeud.getBleue()) new_mot.getStyleClass().add("ligne_bleue");
    else if(noeud.getRouge()) new_mot.getStyleClass().add("ligne_rouge");
    else new_mot.getStyleClass().add("ligne");
    
    vue.getBoxFile().getChildren().add(new_mot); 
    vue.getBoxFile().getChildren().add(new Label("  "));
    if(modele.getJeu().getFile().getSize() >= 20){
      String mot = vue.getZone_ecriture().getText().substring(0, vue.getZone_ecriture().getText().length());
      validation(mot);
    }
   }


  public boolean getPartieTerminee(){
    return partieTerminee;
  }
    
/***********************************Calcul stats**********************************/

    public String getMotRouge(){
      return motRouge;
    }

    public ModeleMulti getModele() {
        return modele;
    }




}
