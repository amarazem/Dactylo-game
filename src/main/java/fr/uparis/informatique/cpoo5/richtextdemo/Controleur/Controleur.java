package fr.uparis.informatique.cpoo5.richtextdemo.Controleur;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import fr.uparis.informatique.cpoo5.richtextdemo.Modele.Modele;
import fr.uparis.informatique.cpoo5.richtextdemo.Vues.Vue;
import fr.uparis.informatique.cpoo5.richtextdemo.jeu.Fifo.Noeud;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public abstract class Controleur {
    protected Timeline timeline;
    protected Timeline timelineTempsDeJeu;
    protected Timeline timelineTempsDeJeuMilis;
    protected Timeline timelineVitesse;
    protected Timeline timelineHide;
    protected int temps;
    protected int tempsJeu = 1;
    protected int tempsJeuMilis = 1;
    protected int debut = 0;
    protected int limite;
    protected ArrayList<Double> fluidite = new ArrayList<Double>();
    protected int cpt = 0;
    protected double vitesse;
    protected int vies;
    protected double nbCarTotal = 0; //nombre total de caracteres
    protected double nbCarUtils = 0; //nombre de caracteres utiles
    protected int nbMotsCorrect = 0; // nombre de mots corrects
    protected int nbMotsErrones = 0;
    protected Vue vue;
    protected Modele modele;

    public Controleur(Vue v, Modele m){
      vue = v;
      modele = m;
      keyPressed();
      keyReleased();
      keyTyped();
    }
    
    /************************************************ Key events*************************************/

    public void keyPressed(){
        vue.getZone_ecriture().addEventFilter(KeyEvent.KEY_PRESSED,event->{
          if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.CONTROL || event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.CAPS ) {
            event.consume();
            return;
          }
          else if(event.getCode() == KeyCode.BACK_SPACE && cpt > 0){
            cpt--;
            return;
          } 
          else event.consume();
        });
     }

      public void keyTyped(){
        vue.getZone_ecriture().addEventFilter(KeyEvent.KEY_TYPED, e -> {
          if(!isLettre(e.getCharacter().charAt(0))) e.consume();
        });
  
        vue.getZone_ecriture().setOnKeyTyped(event->{
          String premierMot = modele.getJeu().getFile().getHead();
          if(isLettre(event.getCharacter().charAt(0))) {
            if(cpt < premierMot.length() && (vue.getZone_ecriture().getText().charAt(cpt) == Character.toLowerCase(premierMot.charAt(cpt)) || vue.getZone_ecriture().getText().charAt(cpt) == premierMot.charAt(cpt)) ){
              vue.getZone_ecriture().replaceText(cpt, cpt+1, Character.toString(premierMot.charAt(cpt)) );
              vue.getZone_ecriture().setStyleClass(cpt, cpt+1, "green");
              nbCarUtils++;
              fluidite.add((double)(tempsJeuMilis - debut));
              debut = tempsJeu;
            }
    
          else 
            vue.getZone_ecriture().setStyleClass(cpt, cpt+1, "red");
              
          cpt++;
    
          nbCarTotal++;
          }
          
        });
    }

    public void keyReleased(){
      vue.getZone_ecriture().addEventFilter(KeyEvent.KEY_RELEASED,event->{
        if (event.getCode() == KeyCode.SPACE) {
          if(modele.getJeu().getFile().isEmpty()) return;
          String mot = vue.getZone_ecriture().getText().substring(0, vue.getZone_ecriture().getText().length());
          validation(mot);        
        }
      });
    }
    
    


    /****************************************** Les differentes timelines *********************************************/

    public void updateTimeline(){
      String[] parts = vue.getTime().getText().split(":");
      int min = Integer.parseInt(parts[0]);
      int sec = Integer.parseInt(parts[1]);
      long elapsedTime = min * 60 + sec - 1;
      int min_after_change = (int) (elapsedTime / 60);
      int sec_after_change = (int) (elapsedTime % 60);
      vue.getTime().setText(String.format("%02d:%02d", min_after_change, sec_after_change));
      if(elapsedTime == 0){
        partieTerminee();
        timeline.stop();
      } 
    }

      public void updateTimelineTempsDeJeu(){
        tempsJeu++;
      }

      public void updateTimelineVitesse(){
        modele.getJeu().getFile().ajoutElementSansSuppression();
        Noeud noeud = modele.getJeu().getFile().get(modele.getJeu().getFile().getSize()-1);
        Label new_mot = new Label(noeud.getData());
        if(noeud.getBleue()) new_mot.getStyleClass().add("ligne_bleue");
        else new_mot.getStyleClass().add("ligne");
        
        vue.getBoxFile().getChildren().add(new_mot); 
        vue.getBoxFile().getChildren().add(new Label("  "));
        if(modele.getJeu().getFile().getSize() >= 20){
          String mot = vue.getZone_ecriture().getText().substring(0, vue.getZone_ecriture().getText().length());
          validation(mot);
          
        }
      }

      public void updateTimelineTempsDeJeuMilis(){
        tempsJeuMilis++;
      }

      public abstract void validation(String mot);

    /************************************* Partie terminee et calculs des stats****************************/
    public void partieTerminee(){
      vue.getZone_ecriture().setDisable(true);
      vue.getVitesse().setText("Vitesse : " + calculVitesse() + " MPM");
      vue.getVitesse().setMinWidth(200);
      vue.getPrecision().setText("Precision : " + calculPrecision() + " %");
      vue.getPrecision().setMinWidth(200);
      vue.getMotsTrouves().setText("Mots corrects : " + nbMotsCorrect);
      vue.getMotsTrouves().setMinWidth(200);
      vue.getMotsErrones().setText("Fluidité : " + calculFluidite());
      vue.getMotsErrones().setMinWidth(200);
    }
    
    
    public double calculVitesse(){
        double res;
        try { 
            if(this instanceof ControleurSoloNormal) {res = (nbCarUtils / ((double)(temps) / 60) / 5);}
            else res = (nbCarUtils / ((double)(tempsJeu) / 60) / 5);
            return Math.round(res*100.0)/100.0;
        } catch (Exception e) {
            return 0;
        }
        
    }
  
    public double calculPrecision(){
        try {
            double d = ((nbCarUtils / nbCarTotal) * 100);
            return Math.round(d*100.0)/100.0;
        } catch (Exception e) {
            return 0;
        }
        
    }

    public double calculFluidite(){
      double[] ecarts = new double[fluidite.size()];
      for (int i = 0; i < fluidite.size(); i++) {
        ecarts[i] = fluidite.get(i);  
      }
      double mean = Arrays.stream(ecarts).average().orElse(Double.NaN);
      double variance = Arrays.stream(ecarts)
                          .map(x -> x - mean)
                          .map(x -> x * x)
                          .average()
                          .orElse(Double.NaN);
      double res = Math.sqrt(variance);
      return Math.round(res*100.0)/100.0;
    }

    /**************************************** Fonctions auxiliaires ***********************************/
    public void hideInfos(){
      CompletableFuture hide = CompletableFuture.supplyAsync(() -> 
      new Timeline(
      new KeyFrame(Duration.seconds(vitesse), event -> vue.getInfos().setText("")))
      ).thenAccept(value -> {
        timelineHide = value;
        timelineHide.setCycleCount(0);
        timelineHide.play();
    });
    }
    public int getTemps() {
        return temps;
    }

    public int getLimite() {
      return limite;
    }

    public static boolean isLettre(char c){
      if((c >= 65 && c <= 90) || (c >= 97 && c <= 122) ) return true;
      else return false;
    }

    public Vue getVue() {
      return vue;
    }

    public Timeline getTimelineTempsDeJeuMilis() {
        return timelineTempsDeJeuMilis;
    }

    public Timeline getTimelineTempsDeJeu() {
        return timelineTempsDeJeu;
    }

    public Timeline getTimelineVitesse() {
        return timelineVitesse;
    }

    }
