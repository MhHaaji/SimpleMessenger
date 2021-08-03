import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Host {
    private Scanner scanner = new Scanner(System.in);
    private int beginPort;
    private int endPort;
    private String validityCode;
    private int connectionPort;
    private ArrayList<Integer> portsForThisHost = new ArrayList<>();
    private ArrayList<WorkSpace> workSpaces = new ArrayList<>();
    private String IP;
    private Socket connectionSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Host(String IP, String beginPort, String endPort) {
        this.beginPort = Integer.parseInt(beginPort);
        this.endPort = Integer.parseInt(endPort);
        this.IP = IP;
    }

    public void disconnectServer() {
        try {
            MainHost.getMainOutputStream().writeUTF("disconnect -host " + IP);
            MainHost.getMainOutputStream().flush();
            connectionPort = Integer.parseInt(MainHost.getMainInputStream().readUTF());
            MainHost.getMainSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        disconnectServer();
        try {
            connectionSocket = new Socket("localhost", connectionPort);
            dataInputStream = new DataInputStream(connectionSocket.getInputStream());
            dataOutputStream = new DataOutputStream(connectionSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                String command = dataInputStream.readUTF();
                if (command == "shutdown")
                    break;
                commandProcess(command);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    public ArrayList<Integer> getPortsForThisHost() {
        return portsForThisHost;
    }

    public ArrayList<WorkSpace> getWorkSpaces() {
        return workSpaces;
    }

    public String getIP() {
        return IP;
    }


    public void commandProcess(String command) {
        Matcher[] matchers = getCommandMatcher(command);


    }

    public Matcher[] getCommandMatcher(String command) {
        Matcher[] commandMatchers = new Matcher[10];
        //0
        Pattern creatWorkspaceReg = Pattern.compile("create-workspace (?<port>[0-9]+) (?<userID>[0-9]+) " + this.IP);


        commandMatchers[0] = creatWorkspaceReg.matcher(command);
        return commandMatchers;
    }

}
