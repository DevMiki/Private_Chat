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

public class JoinTopicWindow {

    private final ChatClient client;
    private final Stage window;

    public JoinTopicWindow(ChatClient client) {
        this.client = client;
        this.window = new Stage();
    }


    public void display() {

        window.setMinWidth(300);
        window.setTitle("Join Topic Window");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);

        //Topic Name Label
        Label labelName = new Label("Topic Name");
        GridPane.setConstraints(labelName, 0, 0);

        //Topic Name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        //Join Button
        Button joinButton = new Button("Join");
        GridPane.setConstraints(joinButton, 1, 2);
        joinButton.setOnAction(e -> {
            try {
                client.joinTopic(nameInput.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            window.close();
        });


        grid.getChildren().addAll(labelName, nameInput, joinButton);
        Scene scene = new Scene(grid, 250, 200);
        window.setScene(scene);
        window.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                try {
                    client.joinTopic(nameInput.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            window.close();
        });
    }

}

