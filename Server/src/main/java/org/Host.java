package org;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Host {
    private String IP;
    private int beginPort;
    private int endPort;
    private String validityCode;
    private int connectionPort;
    private ServerSocket serverSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public static ArrayList<WorkSpace> allWorkSpaces = new ArrayList<>();
    public static ArrayList<Host> allHosts = new ArrayList<>();

    public Host(String IP, Integer beginPort, Integer endPort) {
        this.IP = IP;
        this.beginPort = beginPort;
        this.endPort = endPort;
        allHosts.add(this);
        System.out.println("a new host created: " + this.toString());
    }

    public String getIP() {
        return IP;
    }

    public int getBeginPort() {
        return beginPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public ArrayList<WorkSpace> getWorkSpaces() {
        return allWorkSpaces;
    }

    public static ArrayList<Host> getAllHosts() {
        return allHosts;
    }

    public static String hostHandler(String IP, int beginPort, int endPort) {

        if (beginPort < 10000) {
            return "ERROR Port number must be at least 10000";
        } else if ((endPort - beginPort) > 1000) {
            return "ERROR At most 1000 ports is allowed";
        }
        for (Host host : allHosts) {
            if (host.getIP().equals(IP)) {
                return "ERROR Repeated IP";
            } else {
                for (int i = beginPort; i <= endPort; i++) {
                    if (i <= host.getEndPort() && i >= host.getBeginPort()) {
                        return "ERROR Port interval in use by another host";
                    }
                }


            }
        }
        return "OK";
    }

    public static Host getHOstByIP(String ip) {
        for (Host host : allHosts) {
            if (host.getIP().equals(ip))
                return host;
        }
        return null;
    }

    public int getConnectionPort() {
        return connectionPort;
    }

    public void setConnectionPort(int connectionPort) {
        this.connectionPort = connectionPort;
    }

    public void runThisHostServer() {
        try {
            serverSocket = new ServerSocket(connectionPort);
            Socket socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                while (true){
                    try {
                        dataInputStream.readUTF();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void commandProcess(String command){

    }
    private Matcher[] getCommandMatchers(String command){
        Matcher[] commandMatchers = new Matcher[10];

        return commandMatchers;
    }
}
