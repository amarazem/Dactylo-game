package fr.uparis.informatique.cpoo5.tcpdemo;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.uparis.informatique.cpoo5.richtextdemo.Controleur.ControleurMulti;

public class ChatWriter extends Thread


// Reads input from the console and sends it on the socket
// This thread needs to be killed when the socket closes
// Both the server and the client will use this class
{
    BufferedReader con_br;
    PrintWriter sock_pw;
    private ControleurMulti controleur;
    public ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public ChatWriter(String name, PrintWriter sock_pw, BufferedReader con_br, ControleurMulti c)
    {
        super(name);
        this.sock_pw = sock_pw;
        this.con_br = con_br;
        controleur = c;
        scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.MILLISECONDS);
    }

    public void run()
    {
        String s;
        String tmp = "";
        try
        {
            s = controleur.getMotRouge();

            if(s != null  && !s.equals(tmp)){
                tmp = s;
                sock_pw.println(s);
            }
            if(controleur.getPartieTerminee()){
                sock_pw.println("fin");
                scheduler.shutdown();    
            }
        }
        catch(Exception e){

        }
    }
}