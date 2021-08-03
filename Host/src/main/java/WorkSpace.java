import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class WorkSpace {
    private String name;
    private String ownHostIP;
    private String ownerUsername;
    private ArrayList<String> membersUsername = new ArrayList<>();
    private int port;


    public WorkSpace(String name, String ownHostIP) {
        this.name = name;
        this.ownHostIP = ownHostIP;

    }

    public void run(){

    }
}
