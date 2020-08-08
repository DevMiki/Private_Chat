package it.core.windows;

import it.core.ChatClient;

import java.util.HashMap;
import java.util.Map;

public class WindowManager {

    public static Map<String,MessageWindow> msgWindows = new HashMap<>();
    public static Map<String,MessageTopicWindow> msgTopicWindows = new HashMap<>();
    private ChatClient client;


    public WindowManager(ChatClient client) {
        this.client = client;
    }


    public MessageWindow getRoom(String name) {
        if(msgWindows.containsKey(name)){
            final MessageWindow messageWindow = msgWindows.get(name);
            messageWindow.getWindow().show();
            return messageWindow;

        } else {
            MessageWindow messageWindow = new MessageWindow(client,name);
            msgWindows.put(name, messageWindow);
            return messageWindow;
        }
    }

    public MessageTopicWindow getTopicRoom(String topic) {
        if(msgTopicWindows.containsKey(topic)){
            final MessageTopicWindow messageTopicWindow = msgTopicWindows.get(topic);
            messageTopicWindow.getWindow().show();
            return messageTopicWindow;
        } else {
            MessageTopicWindow messageTopicWindow = new MessageTopicWindow(client,topic);
            msgTopicWindows.put(topic, messageTopicWindow);
            return messageTopicWindow;
        }
    }
}
