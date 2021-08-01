package org.Controller;

import org.MainClient;
import org.Model.RegisterAndLoginMenu;
import org.Model.User;
import org.View.RegisterAndLoginMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterAndLoginMenuController {
    private RegisterAndLoginMenu model;
    private RegisterAndLoginMenuView view;


    public RegisterAndLoginMenuController() {
        model = new RegisterAndLoginMenu(this);
        view = new RegisterAndLoginMenuView(this);


    }


    public void run() {

        this.view.run();
    }

    public void commandProcess(String command) {
        Matcher[] matchers = getCommandMatchers(command);
        if (matchers[0].find()) {
            String username = matchers[0].group("username");
            String phoneNumber = matchers[0].group("phoneNumber");
            String password = matchers[0].group("password");
            MainClient.initializeNetwork();
            String respond = MainClient.sendAndReceiveServer(command);
            if (respond.charAt(0) != 'E') {
                Pattern idReg = Pattern.compile("OK id: (?<id>[0-9]+)");
                Matcher idMatcher = idReg.matcher(respond);
                if (idMatcher.find()) {
                    User user = new User(username, phoneNumber, password, idMatcher.group("id"));
                    System.out.println("user created successfully");
                } else {
                    System.out.println(respond);
                }

            }

        } else if (matchers[1].find()) {

        } else {
            this.view.printString("invalid command");

        }
    }

    private Matcher[] getCommandMatchers(String command) {
        Matcher[] matchers = new Matcher[10];
        //0
        Pattern userRegisterReg = Pattern.compile("client-register -username (?<username>[0-9A-Za-z]+) " +
                "-phoneNumber (?<phoneNumber>[0-9]+) -password (?<password>[0-9A-Za-z]+)");
        //1
        Pattern userLoginReg = Pattern.compile("client-login -phoneNumber (?<phoneNumber>[0-9]+) -password (?<password>[0-9A-Za-z]+)");


        matchers[0] = userRegisterReg.matcher(command);
        matchers[1] = userLoginReg.matcher(command);


        return matchers;
    }
}
