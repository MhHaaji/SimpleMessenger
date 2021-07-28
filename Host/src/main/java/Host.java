import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Host {
    private ObjectOutputStream mainOutputStream;
    private ObjectInputStream mainInputStream;
    private Socket mainSocket;
    private ArrayList<Integer> portsForThisHost = new ArrayList<>();
    private ArrayList<WorkSpace> workSpaces = new ArrayList<>();
    private String IP;

    public Host(ArrayList<Integer> portsForThisHost, String IP) {
        this.portsForThisHost = portsForThisHost;
        this.IP = IP;
    }

    public void run(){
        initializeNetwork();
    }
    private void initializeNetwork(){
        try {
            mainSocket = new Socket("localhost", 8000);
            mainOutputStream = new ObjectOutputStream(mainSocket.getOutputStream());
            mainInputStream = new ObjectInputStream(mainSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectOutputStream getMainOutputStream() {
        return mainOutputStream;
    }

    public ObjectInputStream getMainInputStream() {
        return mainInputStream;
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

    public Socket getMainSocket() {
        return mainSocket;
    }
}
