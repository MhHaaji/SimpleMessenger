import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainServer {
    private ServerSocket serverSocket;
    private ArrayList<Socket> acceptedSockets = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        initializeNetwork();
    }

    private void initializeNetwork() {
        try {
            serverSocket = new ServerSocket(8000);
            while (true) {
                Socket acceptedSocket = serverSocket.accept();
                acceptedSockets.add(acceptedSocket);
                System.out.println("sth connected to server; it's socket is: " + acceptedSocket.toString());
                startNewThreadForAcceptedSocket(acceptedSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startNewThreadForAcceptedSocket(Socket socket) {
        new Thread(() -> {
            try {
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("sth went wrong in initialize streams!" + e.getMessage());

            }

            while (true) {

            }
        }).start();
    }

    private void commandProcessor(String command) {

    }

    private Matcher[] getCommandMatcher(String command){
        Pattern hostCreationReg = Pattern.compile("create-host (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        Matcher[] commandMatchers = new Matcher[10];
        commandMatchers[0] = hostCreationReg.matcher(command);
        return commandMatchers;
    }

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.run();
    }

}
