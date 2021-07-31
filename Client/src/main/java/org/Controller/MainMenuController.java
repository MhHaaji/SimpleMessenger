package org.Controller;

import org.Model.MainMenu;
import org.Model.User;
import org.View.MainMenuView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenuController {
    private MainMenuView view;
    private MainMenu model;
    private User user;

    public MainMenuController(User user) {
        this.user = user;
        this.view = new MainMenuView(this);
        this.model = new MainMenu(this);
    }

    public void run(){
        this.view.run();
    }

    public User getUser() {
        return user;
    }
    public void commandProcess(String command) {
        Matcher[] matchers = getCommandMatchers(command);
        if (matchers[0].find()){

        } else if (matchers[1].find()){

        } else if (matchers[2].find()){

        } else if (matchers[3].find()){

        } else if (matchers[4].find()){

        } else if (matchers[5].find()){

        } else if (matchers[6].find()){

        } else {
            this.view.printString("invalid command");
        }
    }

    private Matcher[] getCommandMatchers(String command) {
        Matcher[] matchers = new Matcher[10];
        //0
        Pattern createWorkSpaceReg = Pattern.compile("client-create-workspace (?<name>)[0-9A-Za-z]+");
        //1
        Pattern connectWorkSpaceReg = Pattern.compile("client-connect-workspace (?<name>)[0-9A-Za-z]+");
        //2
        Pattern disconnectWorkSpaceReg = Pattern.compile("client-disconnect-workspace");
        //3
        Pattern sendMessageReg = Pattern.compile("client-send-message (?<username>)[0-9A-Za-z]+ -message (?<message>.+)");
        //4
        Pattern getChatsReg = Pattern.compile("client-get-chats");
        //5
        Pattern getMessagesReg = Pattern.compile("client-get-messages (?<username>)[0-9A-Za-z]+");
        //6
        Pattern logoutReg = Pattern.compile("logout");

        matchers[0] = createWorkSpaceReg.matcher(command);
        matchers[1] = connectWorkSpaceReg.matcher(command);
        matchers[2] = disconnectWorkSpaceReg.matcher(command);
        matchers[3] = sendMessageReg.matcher(command);
        matchers[4] = getChatsReg.matcher(command);
        matchers[5] = getMessagesReg.matcher(command);
        matchers[6] = logoutReg.matcher(command);
        return matchers;
    }
}
