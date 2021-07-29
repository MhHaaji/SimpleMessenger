import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainServer {
    private ServerSocket serverSocket;
    private ArrayList<Socket> acceptedSockets = new ArrayList<>();
    private static ArrayList<String> validityCodes = new ArrayList<>();

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
                while (true) {
                    String command = dataInputStream.readUTF();
                    if (command == "shutdown") {
                        break;
                    }

                    String respond = commandProcessor(command);
                    if (respond != null) {
                        dataOutputStream.writeUTF(respond);
                        dataOutputStream.flush();
                    }

                }
            } catch (IOException e) {
                System.out.println("sth went wrong in initialize streams!" + e.getMessage());

            }


        }).start();
    }


    private String commandProcessor(String command) {
        Matcher[] matchers = getCommandMatcher(command);
        if (matchers[0].find()) {
            String IP = matchers[1].group("ip");
            int endPort = Integer.parseInt(matchers[1].group("endPort"));
            int beginPort = Integer.parseInt(matchers[1].group("beginPort"));
            String respond = Host.hostHandler(IP, beginPort, endPort);
            if (respond != "ok") {
                return respond;
            } else {
                int randomPort;
                while (true) {
                    if (isPortAvailable((int) (Math.random() * (endPort - beginPort + 1) + beginPort))) {
                        randomPort = (int) (Math.random() * (endPort - beginPort + 1) + beginPort);
                        break;
                    }
                }
                return "OK" + randomPort;

            }
        } else if (matchers[1].find()) {
            int port = Integer.parseInt(matchers[1].group("port"));
            String ip = matchers[1].group("ip");
            try {
                Socket socket = new Socket("localhost", port);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String validityCode = generateRandomValidityCode();
                dataOutputStream.writeUTF(validityCode);
                socket.close();
                validityCodes.add(validityCode);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (matchers[2].find()) {
            String validityCode = matchers[2].group("validityCOde");
            String ip = matchers[2].group("ip");
            int beginPort = Integer.parseInt(matchers[2].group("beginPort"));
            int endPort = Integer.parseInt(matchers[2].group("endPort"));
            if (!validityCodes.contains(validityCode)) {
                return "ERROR Invalid code";
            } else {
                validityCodes.remove(validityCode);
                Host host = new Host(ip, beginPort, endPort);
                return "OK created";
            }

        }
        return null;
    }

    private Matcher[] getCommandMatcher(String command) {
        Pattern hostCreationReg = Pattern.compile("create-host (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        Pattern checkHostRandomPortReg = Pattern.compile("check (?<port>[0-9]+) for (?<ip>[0-9\\.]+)");
        Pattern validityCodeCheckReg = Pattern.compile("validityCode (?<validityCode>[0-9]+) (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        Matcher[] commandMatchers = new Matcher[10];
        commandMatchers[0] = hostCreationReg.matcher(command);
        commandMatchers[1] = checkHostRandomPortReg.matcher(command);
        commandMatchers[2] = validityCodeCheckReg.matcher(command);
        return commandMatchers;
    }

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.run();
    }

    public static boolean isPortAvailable(int port) {
        try {
            Socket ignored = new Socket("localhost", port);
            ignored.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public String generateRandomValidityCode() {
        String AlphaNumericString = "0123456789";
        StringBuilder code = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            code.append(AlphaNumericString.charAt(index));
        }
        return code.toString();
    }
}
