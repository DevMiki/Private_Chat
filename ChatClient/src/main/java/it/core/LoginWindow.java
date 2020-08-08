package it.core;


import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindow {

    private final ChatClient client;
    private final Stage window;

    public LoginWindow(ChatClient client) {
        this.client = client;
        this.window = new Stage();
        initializeListener(client);
    }


    public void display() {

        window.setMinWidth(250);
        window.setTitle("Login Window");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);

        //Name Label
        Label labelName = new Label("Name");
        GridPane.setConstraints(labelName, 0, 0);

        //Name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        //Username Label
        Label labelUsername = new Label("Username");
        GridPane.setConstraints(labelUsername, 0, 1);

        //Username input
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 1);

        //Password Label
        Label psw = new Label("Password");
        GridPane.setConstraints(psw, 0, 2);

        //Password input
        TextField pswInput = new TextField();
        pswInput.setPromptText("password");
        GridPane.setConstraints(pswInput, 1, 2);


        //Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 3);
        loginButton.setOnAction(e ->{
            tryConnection(nameInput, usernameInput, pswInput);
        });


        grid.getChildren().addAll(labelName, nameInput,labelUsername,usernameInput, psw, pswInput, loginButton);
        Scene scene = new Scene(grid, 300, 300);
        window.setScene(scene);
        window.show();

        scene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode.equals(KeyCode.ENTER)) {
                tryConnection(nameInput, usernameInput, pswInput);
            }
        });
    }

    private void tryConnection(TextField nameInput, TextField usernameInput, TextField pswInput) {
        if (client.connect()) {
            try {
                client.login(nameInput.getText(), usernameInput.getText(), pswInput.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("Connection not working or refused");
        }
    }

    private void initializeListener(ChatClient client) {
        client.registerListener("login", strings -> {
            if(strings[0].equalsIgnoreCase("successful")){
                //verrà eseguito nel thread della GUI
                Platform.runLater(window::close);
                return;
            }
            System.err.println("Something went wrong");
        });

    }

}
