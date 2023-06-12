package fr.uparis.informatique.cpoo5.tcpdemo;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.ControleurMulti;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatServer
{
    public static int port = 13000;
    private static ServerSocket ssock;
    private static Socket csock;
    public static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public static ServerSocket getSsock() {
        return ssock;
    }

    public static Socket getCsock() {
        return csock;
    }

    public static void serverConnection(ControleurMulti c) throws IOException{
        ssock = new ServerSocket(port);
        //System.out.println("server: Waiting for client to connect");
        csock = ssock.accept();
        //System.out.println("server: Connection established");

        BufferedReader csock_br = new BufferedReader(new InputStreamReader(csock.getInputStream()));
        PrintWriter csock_pw = new PrintWriter(csock.getOutputStream(), true);

        Thread chat_server_writer = new ChatWriter("chat_server_writer", csock_pw, con_br, c);
        chat_server_writer.start();

        Runnable runnable = new Runnable() {

            public void run() {
                
                try {
                    String s = csock_br.readLine();
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
                } catch (Exception e) {
                    System.out.println("erreur");
                }
            }
                
            
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.MILLISECONDS);
        // while((s = csock_br.readLine()) != null)
        // {
        //     System.out.println("\rclient: " + s);
        //     System.out.print("> ");
        // }

        //System.out.println("\rserver: Client has disconnected");
    }

    public static void closeSocket() throws IOException{
        ssock.close();
        csock.close();
    }
    // public static void main(String[] args)throws IOException
    // {
    //     ServerSocket ssock = new ServerSocket(port);
    //     System.out.println("server: Waiting for client to connect");
    //     Socket csock = ssock.accept();
    //     System.out.println("server: Connection established");

    //     BufferedReader csock_br = new BufferedReader(new InputStreamReader(csock.getInputStream()));
    //     PrintWriter csock_pw = new PrintWriter(csock.getOutputStream(), true);

    //     Thread chat_server_writer = new ChatWriter("chat_server_writer", csock_pw, con_br);
    //     chat_server_writer.start();

    //     String s;
    //     while((s = csock_br.readLine()) != null)
    //     {
    //         System.out.println("\rclient: " + s);
    //         System.out.print("> ");
    //     }

    //     System.out.println("\rserver: Client has disconnected");
    //     csock.close();
    //     ssock.close();
    // }
}