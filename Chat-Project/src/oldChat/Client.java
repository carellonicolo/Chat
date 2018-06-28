package oldChat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    private final Socket socketConnessione;
    private final BufferedReader dalServer;
    private final PrintStream alServer;
    private String name;

    //buffered reader ha bisogno di inputestreamreader 
    public Client(String name) throws IOException {
        
        this.name = name;
        BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ipServer;
        System.out.print("> Inserire indirizzo server: ");
        String indirizzo = t.readLine();
        ipServer = InetAddress.getByName(indirizzo);
        socketConnessione = new Socket(ipServer, 10000);
        System.out.println("> Connessione al server avvenuta con successo\n> Ecco le informazioni della sessione " + socketConnessione);
        
        //creo due Stream, uno per l'input e uno per l''output
        dalServer = new BufferedReader(new InputStreamReader(socketConnessione.getInputStream()));
        alServer = new PrintStream(socketConnessione.getOutputStream());
    
    } // Client()

    public void conversazione() {
        String messIN = "", messOUT = "";
        
        //inputStream proveniente da tastiera (stdin)
        BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            
            //il primo messagio è il nome del server
            String nameServer = dalServer.readLine();
            System.out.println(nameServer);
            
            while (true){

                //controllo messaggi in arrivo dal server
                messIN = dalServer.readLine();
                System.out.println(messIN);
                
                //ora aspetto 
                System.out.print("> " + name + ":");
                messOUT = t.readLine();
                
                //controllo se è stato fatto il comando logout, e lo faccio prima di mandare il messaggio al server
                if(messOUT.equals("/logout"))
                    break;
                
                if(messOUT.equals("/rename")){
                    System.out.print("inserire nuovo nome utente: ");
                    name = t.readLine();
                }
                
                if(!messOUT.equals("/rename")){
                    alServer.println("> "+name + ": " + messOUT);                    
                    System.out.println(messOUT);
                }
                
            } // while
            
            System.out.println("> Logout eseguito correttamente!");
            socketConnessione.close();
            
        } catch (IOException e) {
            System.out.println("> Conversazione interrotta per un errore sconosciuto.");
        }
    } // conversazione()

    //PER ESEGUIRE IL CLIENT
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("> Inserisci il nome utente: ");
        String user = input.next();

        Client c = new Client(user);
        c.conversazione();
    }
}//fine classe clietn
