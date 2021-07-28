import java.util.ArrayList;

public class WorkSpace {
    String name;
    String ownHostIP;
    String ownerUsername;
    ArrayList<String> membersUserName = new ArrayList<>();

    public WorkSpace(String name, String ownHostIP, String ownerUsername) {
        this.name = name;
        this.ownHostIP = ownHostIP;
        this.ownerUsername = ownerUsername;
    }

    public void run(){

    }
}
