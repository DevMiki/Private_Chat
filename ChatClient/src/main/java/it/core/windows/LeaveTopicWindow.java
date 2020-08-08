package it.core.windows;

import it.core.ChatClient;
import it.core.HomeWindow;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaveTopicWindow {

    private final ChatClient client;
    private final Stage window;

    public LeaveTopicWindow(ChatClient client) {
        this.client = client;
        this.window = new Stage();
    }


    public void display() {

        window.setMinWidth(300);
        window.setTitle("Leave Topic Window");

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

        //Leave Button
        Button leaveButton = new Button("Leave");
        GridPane.setConstraints(leaveButton, 1, 2);
        leaveButton.setOnAction(e -> {
            try {
                client.leaveTopic(nameInput.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            window.close();
        });


        grid.getChildren().addAll(labelName, nameInput, leaveButton);
        Scene scene = new Scene(grid, 250, 200);
        window.setScene(scene);
        window.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                try {
                    client.leaveTopic(nameInput.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            window.close();
        });
    }

}



