package fr.uparis.informatique.cpoo5.richtextdemo.jeu;
import java.util.ArrayList;
import java.util.Random;

public class Fifo {

    public class Noeud {

        private String data;
        private Noeud next;
        private boolean bleue;
        private boolean rouge;

        private Noeud(String data, Noeud next) {
            this.data = data;
            this.next = next;
            Random r = new Random();
		    int rand = r.nextInt(100);
            if (rand < 15) bleue = true;
            else bleue = false;
            if(!bleue){
                Random r1 = new Random();
                int rand1 = r1.nextInt(100);
                if (rand1 < 50) rouge = true;
            }
        }

        private Noeud(String data, Noeud next, boolean bleue, boolean rouge) {
            this.data = data;
            this.next = next;
		    this.bleue = bleue;
            this.rouge = rouge;
        }

        private Noeud getNext() {
            return new Noeud(this.next.data, this.next.next, this.next.bleue, this.next.rouge);
        }




        private void ajoutElement(String s){
            if (this.next == null){
                next = new Noeud(s,null);
            }else{
                this.next.ajoutElement(s);
            }
        }

        private Noeud get(int ind){
            if(ind < 0){
                return null;
            }else if(this.next == null && ind != 0){
                return this;
            }else if (ind == 0){
                return this;
            }else{
                return this.next.get(ind-1);
            }
        }

        public String getData() {
            return data;
        }

        public boolean getBleue(){
            return bleue;
        }

        public boolean getRouge(){
            return rouge;
        }


        public String toString(int mode){
                if (this.next == null) {
                    return data;
                }
    
                else return data + " " + next.toString(mode);
            
        }
            
        


        
    }

    private Noeud head;
    private int size = 0;

    public Fifo(){
        this.head = null;
    }

    

    

    public int getSize() {
        return size;
    }

    public String getHead() {
        return head.data;
    }

    public Boolean getHeadBleu() {
        return head.bleue;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public Boolean getHeadRouge() {
        return head.rouge;
    }

    public void ajoutElement(){
        String s = GenerateurMots.Generer();
        if(head == null) {
            head = new Noeud(s, null); 
            size++;
            return;
        }
        if(size >= 20){
            suppElement();
            head.ajoutElement(s);
            size++;
        }
        else{
            head.ajoutElement(s);
            size++;
        }
    }

    public void ajouterElementSansSuppression(String s){
        if(head == null) {
            head = new Noeud(s, null); 
            size++;
            return;
        }
        else{
            head.ajoutElement(s);
            size++;
        }
    }

    public void ajoutElementSansSuppression(){
        String s = GenerateurMots.Generer();
        if(head == null) {
            head = new Noeud(s, null); 
            size++;
            return;
        }
        else{
            head.ajoutElement(s);
            size++;
        }
    }


    public void suppElement(){
        if(size == 1){
            head = null;
            size = 0; 
            return;
        }
        if(size > 0){
            head = head.getNext();
            size--;
        }
    }

    public Noeud get(int ind){
        if(size == 0 || ind < 0){
            return null;
        }else{
            return head.get(ind);
        }
    }

    public static Fifo initialisationFileSolo(){
        Fifo f = new Fifo();

        for (int i = 0; i < 20;i++ ) {
            f.ajoutElement();
        }
        
        
        return f;
    }

    public static Fifo initialisationFileJeu(){
        Fifo f = new Fifo();

        f.ajoutElement();
        
        return f;
    }

    public ArrayList<String> toList(){
        ArrayList<String> res = new ArrayList<String>();
        if(head == null) return res;

        Noeud act = head;
        while(act != null){
            res.add(act.data);
            act = act.next;
        }
        return res;
    }

    public static int verifNbFautes(String motEcrit, String motFile){
        int nbFautes = 0;
        if(motEcrit.length() < motFile.length()){
            for (int i = 0; i < motEcrit.length(); i++) {
                if (motEcrit.charAt(i) != motFile.charAt(i)) {
                    nbFautes++;
                }
            }
            for (int i = 0; i < motFile.length() - motEcrit.length(); i++) {
                nbFautes++;
            }
        }
        else if (motEcrit.length() == motFile.length()) {
            for (int i = 0; i < motEcrit.length(); i++) {
                if (motEcrit.charAt(i) != motFile.charAt(i)) {
                    nbFautes++;
                }
            }
        }
        else{
            for (int i = 0; i < motFile.length(); i++) {
                if (motEcrit.charAt(i) != motFile.charAt(i)) {
                    nbFautes++;
                }
            }
            for (int i = 0; i < motEcrit.length() - motFile.length(); i++) {
                nbFautes++;
            }
        }
        return nbFautes;
    }

    public String toString(int mode){
        if(head == null) return "";
        return head.toString(mode);
    }


}
