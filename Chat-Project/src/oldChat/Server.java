package oldChat;


import java.net.*;
import java.io.*;
import java.util.Scanner;
//CIAO
public class Server {

    private final ServerSocket socketServer;
    private final Socket connessione;
    private final BufferedReader dalClient;
    private final PrintStream alClient;
    private String name;

    public Server(String name) throws IOException {
        this.name = name;
        socketServer = new ServerSocket(10000);//porta 10mila 10'000
        System.out.println("> Server attivo\n> Ora è possibile far connettere i client sulla porta 10000");
        connessione = socketServer.accept();//accetto le connessioni in ingresso 
        dalClient = new BufferedReader(new InputStreamReader(connessione.getInputStream()));
        alClient = new PrintStream(connessione.getOutputStream());
    } // Server()

    public void conversazione() {
        String messaggio = "";
        BufferedReader t = new BufferedReader(new InputStreamReader(System.in));
        
        try {
            alClient.println(name);
            alClient.println("> CARELLO_SERVER --> Sei connesso al server! Digita /logout per effetuare la disconnessione.");
            while (!messaggio.equals("/logout")) {
                //leggo dallo stream in input
                messaggio = dalClient.readLine(); 
                System.out.println(messaggio);
                if (!messaggio.equals("/logout")) {
                    //leggo dallo standard input e lo stampo sullo stream in output
                    messaggio = t.readLine();
                    alClient.println(">" + name + ": " + messaggio);
                }
            } // while
        } catch (IOException e) {
            System.out.println("> E' stata generata un'eccezione inaspettata\n> connessione interrota");
        }
    } // conversazione()

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("> Inserisci il nome del Server: ");
        String serverName = input.next();

        Server s = new Server(serverName);
        s.conversazione();
    }

}//fine class
