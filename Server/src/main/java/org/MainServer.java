package org;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnixDomainSocketAddress;
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
                    if (command == "disconnect") {
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
            String IP = matchers[0].group("ip");
            int endPort = Integer.parseInt(matchers[0].group("endPort"));
            int beginPort = Integer.parseInt(matchers[0].group("beginPort"));
            String respond = Host.hostHandler(IP, beginPort, endPort);
            if (respond != "OK") {
                return respond;
            } else {
                int randomPort;
                while (true) {
                    if (isPortAvailable((int) (Math.random() * (endPort - beginPort + 1) + beginPort))) {
                        randomPort = (int) (Math.random() * (endPort - beginPort + 1) + beginPort);
                        break;
                    }
                }
                return "OK " + randomPort;

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
            String validityCode = matchers[2].group("validityCode");
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

        } else if (matchers[3].find()) {
            String username = matchers[3].group("username");
            String phoneNumber = matchers[3].group("phoneNumber");
            String password = matchers[3].group("password");

            if (User.getUserByUserName(username) != null) {
                return "ERROR: user with this username exists";
            } else if (User.getUserByPhoneNumber(phoneNumber) != null) {
                return "ERROR: user with this phoneNumber exists";
            } else {
                String id = generateRandomUserID();
                User user = new User(phoneNumber, username, password, id);
                return "OK id: " + id;
            }
        } else if (matchers[4].find()) {
            String phoneNumber = matchers[4].group("phoneNumber");
            String password = matchers[4].group("password");
            if (User.getUserByPhoneNumber(phoneNumber) == null) {
                return "ERROR: phoneNumber and password did not match";
            } else if (!User.getUserByPhoneNumber(phoneNumber).getPassword().equals(password)) {
                return "ERROR: phoneNumber and password did not match";
            } else {
                return User.getUserByPhoneNumber(phoneNumber).getSerializedUser();
            }
        } else if (matchers[5].find()) {
            String workSpaceName = matchers[0].group("name");
            if (Host.allHosts.size() == 0){
                return "ERROR: there is no available host";
            } else {
                Collections.shuffle(Host.getAllHosts());
                Host host = Host.getAllHosts().get(0);

            }


        } else if (matchers[6].find()) {

        }
        return null;
    }

    private Matcher[] getCommandMatcher(String command) {
        //0
        Pattern hostCreationReg = Pattern.compile("create-host (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        //1
        Pattern checkHostRandomPortReg = Pattern.compile("check (?<port>[0-9]+) for (?<ip>[0-9\\.]+)");
        //2
        Pattern validityCodeCheckReg = Pattern.compile("validityCode (?<validityCode>[0-9]+) (?<ip>[0-9\\.]+) (?<beginPort>[0-9]+) (?<endPort>[0-9]+)");
        //3
        Pattern registerReg = Pattern.compile("client-register -username (?<username>[0-9A-Za-z]+) " +
                "-phoneNumber (?<phoneNumber>[0-9]+) -password (?<password>[0-9A-Za-z]+)");
        //4
        Pattern loginReg = Pattern.compile("client-login -phoneNumber (?<phoneNumber>[0-9]+) -password (?<password>[0-9A-Za-z]+)");
        //5
        Pattern createWorkSpaceReg = Pattern.compile("client-create-workspace (?<name>)[0-9A-Za-z]+");
        //6
        Pattern connectWorkSpaceReg = Pattern.compile("client-connect-workspace (?<name>)[0-9A-Za-z]+");


        Matcher[] commandMatchers = new Matcher[10];

        commandMatchers[0] = hostCreationReg.matcher(command);
        commandMatchers[1] = checkHostRandomPortReg.matcher(command);
        commandMatchers[2] = validityCodeCheckReg.matcher(command);
        commandMatchers[3] = registerReg.matcher(command);
        commandMatchers[4] = loginReg.matcher(command);
        commandMatchers[5] = createWorkSpaceReg.matcher(command);
        commandMatchers[6] = connectWorkSpaceReg.matcher(command);

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

    public String generateRandomUserID() {
        String AlphaNumericString = "0123456789";
        StringBuilder code = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            code.append(AlphaNumericString.charAt(index));
        }
        return code.toString();
    }


}
