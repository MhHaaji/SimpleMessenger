import javax.sound.sampled.*;
import java.util.ArrayList;

public class Host {
    private ArrayList<Integer> portsForThisHost = new ArrayList<>();
    private ArrayList<WorkSpace> workSpaces = new ArrayList<>();
    String IP;

    public Host(ArrayList<Integer> portsForThisHost, String IP) {
        this.portsForThisHost = portsForThisHost;
        this.IP = IP;
    }

    public void run(){

    }
    private void initializeNetwork(){
        
    }
}
