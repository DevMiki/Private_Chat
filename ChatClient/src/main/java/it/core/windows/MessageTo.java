package it.core.windows;

import it.core.ChatClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MessageTo {

    private ChatClient client;

    public MessageTo(ChatClient client) {

        this.client = client;
    }

    public void display() {
        Stage window = new Stage();
        window.setMinWidth(300);
        window.setMinHeight(300);
        window.setTitle("Message window");

        TextArea textArea = new TextArea();
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        //Field recipient
        Label recipientLabel = new Label("Recipient");
        TextField recipientField = new TextField();
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(recipientLabel, recipientField);
        //Button send
        Button messageButton = new Button();
        messageButton.setText("Send");
        messageButton.setOnAction(e -> {
            sendMessageTo(window, textArea, recipientField);
        });


        vbox.getChildren().addAll(textArea, hBox, messageButton);
        Scene scene = new Scene(vbox, 300, 250);
        window.setScene(scene);
        window.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                sendMessageTo(window, textArea, recipientField);
            }
        });
    }

    private void sendMessageTo(Stage window, TextArea textArea, TextField recipientField) {
        String recipient = recipientField.getText();
        String msgBody = textArea.getText();
        if (!recipient.equals(client.getName())) {
            try {
                client.getSocket().getOutputStream().write(String.format("msg %s %s\n",recipient,msgBody).getBytes());
                WindowManager windowManager = new WindowManager(client);
                windowManager.getRoom(recipient);
                windowManager.getRoom(recipient).getTextArea().appendText(String.format("You: %s\n",msgBody));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        window.close();
    }
}