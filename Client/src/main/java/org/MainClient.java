package org;

import org.Controller.RegisterAndLoginMenuController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainClient {
    public static DataInputStream mainInputStream;
    public static DataOutputStream mainOutputStream;
    public static Socket mainSocket;

    public static DataInputStream getMainInputStream() {
        return mainInputStream;
    }


    public static DataOutputStream getMainOutputStream() {
        return mainOutputStream;
    }


    public static Socket getMainSocket() {
        return mainSocket;
    }


    public static void initializeNetwork() {
        try {
            mainSocket = new Socket("localhost", 8000);
            mainOutputStream = new DataOutputStream(mainSocket.getOutputStream());
            mainInputStream = new DataInputStream(mainSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void disconnectServer(){
        try {
            mainSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendAndReceiveServer(String command){
        try {
            mainOutputStream.writeUTF(command);
            mainOutputStream.flush();
            String respond = mainInputStream.readUTF();
            return respond;
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }

    public static void main(String[] args) {

//        initializeNetwork();
        RegisterAndLoginMenuController registerAndLoginMenuController = new RegisterAndLoginMenuController();
        registerAndLoginMenuController.run();

    }
}
