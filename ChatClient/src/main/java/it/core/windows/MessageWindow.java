package it.core.windows;

import it.core.ChatClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class MessageWindow {

    private TextArea textArea;
    private TextArea textAreaSend;
    private Stage window;


    public Stage getWindow() {
        return window;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public MessageWindow(ChatClient client, String name) {

        window = new Stage();
        window.setMinWidth(400);
        window.setMinHeight(550);
        window.setTitle(String.format("Message window of '%s' with '%s'", client.getName(), name));

        textArea = new TextArea();
        textArea.setEditable(false);
        textAreaSend = new TextArea();
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        //Button
        Button messageButton = new Button();
        messageButton.setText("Send");
        messageButton.setOnAction(e -> {
            sendMessageTo(client, name);
        });

        textAreaSend.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                sendMessageTo(client, name);
            }
        });

        //Adding
        vbox.getChildren().addAll(textArea, textAreaSend, messageButton);
        Scene scene = new Scene(vbox, 250, 250);
        window.setScene(scene);
        window.show();
    }

    private void sendMessageTo(ChatClient client, String name) {
        try {
            String textToSend = textAreaSend.getText();
            client.getSocket().getOutputStream().write((String.format("msg %s %s\n", name, textToSend)).getBytes());
            textArea.appendText(String.format("You: %s",textToSend));
            textAreaSend.clear();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
