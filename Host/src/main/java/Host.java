import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ArrayList<Integer> portsForThisHost = new ArrayList<>();
    private ArrayList<WorkSpace> workSpaces = new ArrayList<>();
    private String IP;

    public Host(String IP, String beginPort, String endPort) {
        this.beginPort = Integer.parseInt(beginPort);
        this.endPort = Integer.parseInt(endPort);
        this.IP = IP;
    }

    public void run(){

        runInputCommand();


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


    public void commandProcess(String command){
        Matcher[] matchers = getCommandMatcher(command);


    }
    public Matcher[] getCommandMatcher(String command){
        Matcher[] commandMatchers = new Matcher[10];
        Pattern shutdownReg = Pattern.compile("shutdown");

        commandMatchers[0] = shutdownReg.matcher(command);
        return commandMatchers;
    }

    private void runInputCommand(){
        new Thread(()->{
            while (true){
                String input = scanner.nextLine().trim();
                if (input == "shutdown"){
                    //TODO: prevent unwanted error
                    break;
                }
                commandProcess(input);
            }
        }).start();
    }
}
