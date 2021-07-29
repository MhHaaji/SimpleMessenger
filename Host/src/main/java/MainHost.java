import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainHost {
    private Scanner scanner = new Scanner(System.in);
    private static DataOutputStream mainOutputStream;
    private static DataInputStream mainInputStream;
    private static Socket mainSocket;


    public static Socket getMainSocket() {
        return mainSocket;
    }

    private void initializeNetwork() {
        try {
            mainSocket = new Socket("localhost", 8000);
            mainOutputStream = new DataOutputStream(mainSocket.getOutputStream());
            mainInputStream = new DataInputStream(mainSocket.getInputStream());
        } catch (IOException e) {
            System.out.println("SERVER CONNECTION ERROR" +
                    "\ntry again later");
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        MainHost mainHost = new MainHost();
        mainHost.initializeNetwork();
        mainHost.commandProcess();


    }

    public void commandProcess() {
        helpPrint();
        while (true) {
            String command = scanner.nextLine().trim();
            Matcher[] matchers = getCommandMatcher(command);
            if (matchers[0].find()) {
                //TODO prevent unwanted error
                break;
            } else if (matchers[1].find()) {
                hstCreationProcess(matchers[1].group("ip"), matchers[1].group("beginPort"), matchers[1].group("endPort"));
            } else if (matchers[2].find()) {
                helpPrint();
            } else {
                System.out.println("invalid command");
                helpPrint();
            }
        }


    }

    public Matcher[] getCommandMatcher(String command) {
        Matcher[] commandMatchers = new Matcher[10];
        Pattern shutdownReg = Pattern.compile("shutdown");
        Pattern createHostReg = Pattern.compile("create-host (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        Pattern helpReg = Pattern.compile("--help");
        Pattern checkReg = Pattern.compile("check (?<port>[0-9]+) for (?<ip>[0-9\\.]+)");

        commandMatchers[0] = shutdownReg.matcher(command);
        commandMatchers[1] = createHostReg.matcher(command);
        commandMatchers[2] = helpReg.matcher(command);
        commandMatchers[3] = checkReg.matcher(command);
        return commandMatchers;
    }

    private void helpPrint() {
        System.out.println("to create a host enter this command:" +
                "\n\"create-host <IP> <beginPort> <endPort>\"\n" +
                "to end this program enter this command:" +
                "\"shutdown\"" +
                "to see this again enter:" +
                "\"--help\"");
    }

    private void hstCreationProcess(String ip, String beginPort, String endPort) {
        try {
            mainOutputStream.writeUTF("create-host " + ip + " " + beginPort + " " + endPort);
            mainOutputStream.flush();
            String respond = mainInputStream.readUTF().trim();

            if (respond.charAt(0) == 'E') {
                System.out.println(respond);
                return;
            } else {
                System.out.println(respond);

                Pattern checkPort = Pattern.compile("OK (?<port>[0-9]+)");
                Matcher checkMatcher = checkPort.matcher(respond);
                int randomPort = 0;
                if (checkMatcher.find()) {
                    randomPort = Integer.parseInt(checkMatcher.group("port"));
                }


                ServerSocket serverSocket = new ServerSocket(randomPort);
                mainOutputStream.writeUTF("check " + randomPort + " for " + ip);
                mainOutputStream.flush();
                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                String validityCode = dataInputStream.readUTF();

                serverSocket.close();
                socket.close();
                System.out.println(validityCode);
                mainOutputStream.writeUTF("validityCode " + validityCode + " " + ip + " " + beginPort + " " + endPort);
                respond = mainInputStream.readUTF().trim();
                if (respond.equals("ERROR Invalid code")) {
                    System.out.println(respond + "\ntry again later");
                } else {
                    Host host = new Host(ip, beginPort, endPort);
                    System.out.println(respond);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
