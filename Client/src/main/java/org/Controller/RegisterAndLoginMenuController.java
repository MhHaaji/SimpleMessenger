package org.Controller;
import org.Model.RegisterAndLoginMenu;
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
