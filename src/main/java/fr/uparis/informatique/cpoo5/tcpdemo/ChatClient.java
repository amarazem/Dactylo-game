/*
 * D'après https://github.com/sharmaeklavya2/tcp-chat/ (un peu remanié)
 */

 package fr.uparis.informatique.cpoo5.tcpdemo;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.ControleurMulti;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;



public class ChatClient
{
    public static int port = 13000;
    private static Socket sock;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static Socket getSock() {
        return sock;
    }

    public static void connexionToServer(ControleurMulti c) throws IOException{
        sock = new Socket("localhost", port);
        System.out.println("Connection established");
        BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), true);

        Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br, c);
        chat_client_writer.start();
        
        Runnable runnable = new Runnable() {

            public void run() {
                try {
                    String s = sock_br.readLine();
                    //System.out.println("---> "+s);
                    if(s!=null &&  s.equals("fin")){
                        System.out.println(s);
                        c.getTimelineVitesse().stop();
                        c.getTimelineTempsDeJeu().stop();
                        c.getTimelineTempsDeJeuMilis().stop();
                        c.partieTerminee();
                        closeSocket();
                        scheduler.shutdown();
                    }else if(s!=null){
                        System.out.println(s);
                        c.ajoutMot(s);
                    }
                } catch (IOException e) {

                }
            }
                
            
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MILLISECONDS);

        // while((s = sock_br.readLine()) != null)
        // {
        //     System.out.println("\rserver: " + s);
        //     System.out.print("> ");
        // }
    }

    public static void closeSocket() throws IOException{
        sock.close();
    }



    // public static void main(String[] args)throws IOException
    // {
    //     System.out.print("Enter server address: ");
    //     String address = con_br.readLine();
    //     Socket sock = new Socket(address, port);
    //     BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    //     PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), true);
    //     System.out.println("Connection established");

    //     Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br);
    //     chat_client_writer.start();
        
    //     String s;
    //     while((s = sock_br.readLine()) != null)
    //     {
    //         System.out.println("\rserver: " + s);
    //         System.out.print("> ");
    //     }
    //     sock.close();
    // }
}
