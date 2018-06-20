import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewServer implements Runnable{
    
    private ServerSocket serverSocket;
    private final Socket socket;            
    private BufferedReader inputStream; 
    private Scanner inStream;
    private PrintStream outStream;
    private String name;
    private int porta;
 
    /************************** CONTRUCTOR ****************************************/
    NewServer(String name, int porta) throws IOException, InterruptedException{
        System.out.println("> tentativo di inizializzazione della connessione sulla porta: "+porta+"...");
        this.porta = porta;
        this.name = name;
        
        //CREO LA SOCKETSERVER
        serverSocket = new ServerSocket(10000);
        System.out.println("> Istanza di rete creata con successo...\n> in attesa di client...");
        //COLLEGO LA SERVERSOCKET AD UNA SOCKET NORMALE ED ACCETTO IN INGRESSO LE CONNESSIONEI
        socket = serverSocket.accept();//il metodo acept è bloccante
        
        System.out.println("> Tentativo di collegamento con un client in corso...");
        //Thread.sleep(100);
        
        //COLLEGO GLI STREAM DI INPUT E OUTPUT
        inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new PrintStream(socket.getOutputStream());
        System.out.println("> Inizializzazione degli stream di input e output avvennuta on sucesso...\n> Ora è possibile chattare con il client! ");
    }
    
    
    
    
    
    /********************************** INVIO MESSAGGI *******************************************/
    void send() throws IOException, InterruptedException{
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String messOUT = "";
        
        while(true){
            System.out.print("> Server di "+name + ": ");
            //leggo il testo dalla tastiera e controllo che non sia stato digitato il comando close
            messOUT = stdin.readLine();
            //stampo sullo stream di output il messaggio 
            outStream.print("Server di "+name+": "+messOUT);
                
            if(messOUT.equals("/close"))
                break;

        }//while
        
        System.out.println("> Spegnimento Server in corso...\n> E' stato notificato a tutti i client lo spegnimento del server... ");
        
        //chiudo le socket e le connessioni in arrivo
        inStream.close();
        outStream.close();
        stdin.close();
        socket.close();
        serverSocket.close();
        
        Thread.sleep(50);
        System.out.println("Server chiuso correttamente!");
        //forzo la chiusura del programma
        System.exit(0);
        
    }//SEND

    
    
    
    
    /******************************** RICEVO MESSAGGI ************************************/
    @Override
    public void run() {
        
        String mess = "";
    
        while(true){
            
            //leggo i messaggi dal bufferedReader
            try {
                mess = inputStream.readLine();
            } catch (IOException ex) {
                Logger.getLogger(NewServer.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
            System.out.println("> "+mess);
            
        }//while
        
        
    }//THREAD
    
    
    
    
    
    
    
    /******************************** MAIN ************************************/

    public static void main(String[] args){
        String nameServer;
        Scanner s = new Scanner(System.in);
        
        while(true){
            System.out.print("> Inserisci il nome del server: ");
            nameServer = s.next();
            if(!nameServer.equals(" ") && !nameServer.equals(""))
                break;
            System.err.println("> Inserire un nome del server valido!!");
        }
        
        
        int porta = 9876;//per ora la dichiaro da codice, poi la chiedo all'utente
        try{
            NewServer server = new NewServer(nameServer, porta);
            System.out.println("ciao");
            Thread t = new Thread(server);
            t.start();
        server.send();
        }catch(IOException | InterruptedException e){
            System.err.print(e);
            System.exit(0);
        }
        
        
       
    }//main
    
    
}//newServer
