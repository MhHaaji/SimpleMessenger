package org.View;

import org.Controller.RegisterAndLoginMenuController;

import java.util.Scanner;

public class RegisterAndLoginMenuView {
    private RegisterAndLoginMenuController controller;
    private Scanner scanner;

    public RegisterAndLoginMenuView(RegisterAndLoginMenuController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
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
                "for register enter this command:\n" +
                "\"client-register -username <username> -phoneNumber <phoneNumber> -password <password>\"\n" +
                "(password and username can contains any letter or digits but phoneNumber only can have digits)\n" +
                "if you have an account use this command to login:\n" +
                "\"client-login -phoneNumber <phoneNumber> -password <password>\"\n" +
                "to see it again enter: \"--help\"");
    }
}
