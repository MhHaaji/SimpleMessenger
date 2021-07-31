package org.View;

import org.Controller.MainMenuController;
import org.Controller.RegisterAndLoginMenuController;

import java.util.Scanner;

public class MainMenuView {
    private MainMenuController controller;
    private Scanner scanner;

    public MainMenuView(MainMenuController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        printHelp();
        while (true) {
            String input = scanner.nextLine().trim();

            this.controller.commandProcess(input);


        }
    }

    public void printString(String string) {
        System.out.println(string);
    }

    public void printHelp() {
        System.out.println("HELP:\n" +
                "to start chatting with your friends create a workSpace:" +
                "\"client-create-workspace <name>\" (name can contains any letter or digits)\n" +
                "if you already crated a workSpace try t connect it:\n" +
                "\"client-connect-workspace <name>\"\n" +
                "to disconnect current workspace enter:\n" +
                "\"client-disconnect-workspace\"\n" +
                "send message to your friend with:\n" +
                "\"client-send-message <username> -message <your message>\"\n" +
                "get a list of your new messages by:\n" +
                "\"client-get-chats\"\n" +
                "to show your messages from your friend enter:\n" +
                "\"client-get-messages <username>\"\n" +
                "logout with \"logout\"\n" +
                "to see it again enter: \"--help\"");
    }

}
