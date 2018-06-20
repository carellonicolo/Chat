import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewClient implements Runnable{
    private Socket socket;
    private Scanner input;
    private PrintStream output;
    private final String name;
    private final InetAddress IP;
    
    NewClient(String name, InetAddress IP) throws IOException, InterruptedException{
        
        //INIZIALIZZO I PARAMETRI
        this.name = name;
        this.IP = IP;
        
        //CREO LA SOCKET
        socket = new Socket(IP, 9876);
        Thread.sleep(100);
        System.out.println("> Connessione al server riuscita... ");
        Thread.sleep(100);
        
        //COLLEGO GLI STREAM
        System.out.println("> Collegamento dei flussi dati in corso... ");
        input = new Scanner(socket.getInputStream());
        output = new PrintStream(socket.getOutputStream());
        
        Thread.sleep(100);
        System.out.println("> Collegamento dei flussi dati riuscita... ");
        
    }
    
    void send(){
       Scanner stin = new Scanner(System.in);
       String messIN;
       String messOUT;
       while(true){
           
       }
    }
    
    
    
     @Override
    public void run() {//riceevo i messaggi dal server
        String mess = "";
        
        while(true){
            mess = input.next();
            System.out.println("> "+mess);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(NewClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
               
    }//Thread
    
     public static void main(String[] args){
        
        Scanner s = new Scanner(System.in);
        InetAddress IPServer;
        String nomeUtente;
        while(true){
            System.out.print("> Inserisci IP del server, inserisci 1 se il serve è 'Localhost': ");
            String nomeServer = s.next();
            try{
                if(nomeServer.equals("1"))
                    IPServer = InetAddress.getByName("127.0.0.1");
                else//non mi server controllare se la stringa passata sia "" o " "
                    IPServer = InetAddress.getByName(nomeServer);
                break;

            }catch(UnknownHostException e){
                System.out.println("> Inserire un'IP valido!!! ");
            }
        }//1° while
        
        
        System.out.println(IPServer.toString());
       
        
        while(true){
            System.out.print("> Inserisci nome utente: ");
            nomeUtente = s.next();
            if(!nomeUtente.equals(" ") && !nomeUtente.equals(""))
                    break;
            System.out.println("> Inserire un nome utente valido!!!");
        }//2° while
        
        
        
        NewClient chat;
        try {
            chat = new NewClient(nomeUtente, IPServer);
        } catch (IOException ex) {
            Logger.getLogger(NewClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(NewClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(chat);
        t.start(); 
        chat.send();
        
        
    }//MAIN

}//newCient