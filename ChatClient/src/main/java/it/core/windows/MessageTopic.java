package it.core.windows;

import it.core.ChatClient;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MessageTopic {

    private ChatClient client;

    public MessageTopic(ChatClient client){
        this.client = client;
    }

    public void display(){
        Stage window = new Stage();
        window.setMinWidth(350);
        window.setMinHeight(300);
        window.setTitle("Topic window");

        TextArea textArea = new TextArea();
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        //Field recipient
        Label recipientLabel = new Label("Topic Recipient");
        TextField recipientField = new TextField();
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().addAll(recipientLabel,recipientField);

        //Button send
        Button messageButton = new Button();
        messageButton.setText("Send");
        messageButton.setOnAction(e -> {
            String topic = recipientField.getText();
            String msgBody = textArea.getText();
            try {
                client.getSocket().getOutputStream().write((String.format("msgt %s %s\n", topic, msgBody)).getBytes());
                WindowManager windowManager = new WindowManager(client);
                windowManager.getTopicRoom(topic);
                windowManager.getTopicRoom(topic).getTextArea().appendText(String.format("You: %s\n",msgBody));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            window.close();
        });

        vbox.getChildren().addAll(textArea,hBox, messageButton);
        Scene scene = new Scene(vbox, 350, 250);
        window.setScene(scene);
        window.show();
    }
}
