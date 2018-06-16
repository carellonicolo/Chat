import java.io.*;
import java.net.*;
import java.util.Scanner;

//PORCAMADONNA
public class ClientChat {
    private Socket socket;
    private Scanner input;
    private PrintStream output;
    private final String name;
    private final InetAddress IP;
    
    ClientChat(String name, InetAddress IP) throws IOException, InterruptedException{
        this.name = name;
        this.IP = IP;
        socket = new Socket(IP, 9876);
        Thread.sleep(100);
        System.out.println("> Connessione al server riuscita... ");
        Thread.sleep(100);
        System.out.println("> Collegamento dei flussi dati in corso... ");
        input = new Scanner(socket.getInputStream());
        output = new PrintStream(socket.getOutputStream());
        Thread.sleep(100);
        System.out.println("> Collegamento dei flussi dati riuscita... ");
        
    }
    
    void chatta(){
       Scanner stin = new Scanner(System.in);
       String messIN;
       String messOUT;
       while(true){
           
       }
    }
    
     public static void main(String[] args) throws IOException, InterruptedException{
        
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
        
        
        
        ClientChat chat = new ClientChat(nomeUtente, IPServer);
        
        
         try {
        Thread.sleep(10000);
        }
        catch (InterruptedException e) {   } 
        System.out.println("1111111");
        
        try {
        Thread.sleep(10000);
        }
        catch (InterruptedException e) {   }
        
        
        
        
       
        chat.chatta();
        
    }//MAIN
    
    
}//newCient