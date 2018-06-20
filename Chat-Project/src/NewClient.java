import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewClient implements Runnable{
    private final Socket socket;
    private final BufferedReader inputStream;
    private final PrintStream outputStream;
    private final String name;
    private final InetAddress IP;
    
    
    
    
    
    /************************************** CONSTRUCTOR *******************************************/
    NewClient(String name, InetAddress IP) throws IOException, InterruptedException{
        
        //INIZIALIZZO I PARAMETRI
        this.name = name;
        this.IP = IP;
        
        //CREO LA SOCKET1
        socket = new Socket(IP, 10000);
        Thread.sleep(100);
        System.out.println("> Connessione al server riuscita... ");
        Thread.sleep(100);
        
        //COLLEGO GLI STREAM
        System.out.println("> Collegamento dei flussi dati in corso... ");
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outputStream = new PrintStream(socket.getOutputStream());
        
        Thread.sleep(100);
        System.out.println("> Collegamento dei flussi dati riuscita... ");
        
    }//CONSTRUCTOR
    
    
    
    
    /********************************** INVIO MESSAGGI *******************************************/
    void send() throws IOException, Exception{
       BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
       String messOUT;
       
       while(true){
           messOUT = stdin.readLine();
           
           if(messOUT.equals("/logout"))
               break;
           
           outputStream.print(messOUT);
       }
       
       //STAMPO RESOCONTO DI LOGOUT
       System.out.println("Logout effettuato con successo!\nSei stato disconnesso dalla chat!");
       
       //MANDO L'ULTIMO MESSAGGIO DI SALUTO AL SERVER
       outputStream.println(""+name + " ha abbandonato la chat!");
       
       //CHIUDO TUTTE LE CONNESSIONI
       outputStream.close();
       stdin.close();
       inputStream.close();
       socket.close();
       
       //LANCIO UN'ECCEZIONE CHE GESTIRA' IL MAIN PER CHIUDERE IL THREAD
       throw new Exception();
    }
    
    
    
    
    
    
    
    /******************************** RICEVO MESSAGGI ************************************/
    @Override
    public void run() {
        String mess = "";
        
        while(true){
            try {
                mess = inputStream.readLine();
            } catch (IOException ex) {
                Logger.getLogger(NewClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(mess.equals("/close"))
                break;
            
            System.out.println("> "+mess);
            
        }//while
        
        
        
    }//Thread
    
    
    
    
    
    
    /******************************** MAIN ***********************************+*/
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
        Thread t = null;
        
        try {
            
            chat = new NewClient(nomeUtente, IPServer);
            t = new Thread(chat);
            t.start();
            chat.send();
            
            
            
        } catch (IOException | InterruptedException ex) {
            System.out.println("> Errore nella creazione di una nuova istanza del Client, Eccezione inaspettata generata! \n"+ex);  
        } catch (Exception ex) {    
            t.stop();   
        }
        
             
    }//MAIN

}//newCient