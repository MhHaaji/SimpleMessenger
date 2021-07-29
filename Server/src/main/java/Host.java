import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Host {
    private String IP;
    private int beginPort;
    private int endPort;
    private String validityCode;
    private ArrayList<WorkSpace> workSpaces = new ArrayList<>();
    public static ArrayList<Host> allHosts = new ArrayList<>();

    public Host(String IP, Integer beginPort, Integer endPort) {
        this.IP = IP;
        this.beginPort = beginPort;
        this.endPort = endPort;
        allHosts.add(this);
        System.out.println("success!!");
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
        return workSpaces;
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


}
