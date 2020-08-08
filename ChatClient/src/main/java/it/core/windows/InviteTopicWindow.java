package it.core.windows;

import it.core.ChatClient;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InviteTopicWindow {


    private final ChatClient client;
    private final Stage window;

    public InviteTopicWindow(ChatClient client) {
        this.client = client;
        this.window = new Stage();
    }


    public void display() {

        window.setMinWidth(300);
        window.setTitle("Invite Topic Window");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);

        //Topic Name Label
        Label labelTopic = new Label("Topic Name");
        GridPane.setConstraints(labelTopic, 0, 0);

        //Topic Name input
        TextField topicInput = new TextField();
        GridPane.setConstraints(topicInput, 1, 0);

        //Name Label
        Label labelName = new Label("Username");
        GridPane.setConstraints(labelName, 0, 1);

        //Name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 1);


        //Invite Button
        Button inviteButton = new Button("Invite");
        GridPane.setConstraints(inviteButton, 1, 3);
        inviteButton.setOnAction(e -> {
            try {
                client.inviteTopic(topicInput.getText(),nameInput.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            window.close();
        });


        grid.getChildren().addAll(labelName, nameInput, inviteButton,labelTopic,topicInput);
        Scene scene = new Scene(grid, 250, 200);
        window.setScene(scene);
        window.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                try {
                    client.inviteTopic(topicInput.getText(),nameInput.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            window.close();
        });
    }


}
