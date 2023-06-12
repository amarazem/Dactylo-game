package fr.uparis.informatique.cpoo5.richtextdemo.jeu;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class GenerateurMots {
    public static String Generer(){
        ArrayList<String> list= new ArrayList<String>();
        File doc = new File("src/main/java/fr/uparis/informatique/cpoo5/richtextdemo/dictionnaires/dictionnaireFr.txt");
		Scanner sc;
		try {
			sc = new Scanner(doc);
			while(sc.hasNext()) {
				String next = sc.next();
				if(next.length()>2 && next.length() < 9) list.add(next);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        Random r = new Random();
        int nb = r.nextInt(list.size());
		
		String str = list.get(nb);
		str = str.toLowerCase();
		str = Character.toString(Character.toUpperCase(str.charAt(0))) + str.substring(1);
		
        return str;
    }

	
}