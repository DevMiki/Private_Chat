package it.core;

import it.core.windows.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;


public final class HomeWindow extends Application {

    private WindowManager windowManager;
    private ListView<String> listView = new ListView<>();
    private ShowTopic showTopic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {

        final ChatClient client = new ChatClient("your local IP", 8818);
        windowManager = new WindowManager(client);
        this.showTopic = new ShowTopic(client);


        stage.setMinWidth(470);
        stage.setMinHeight(350);
        stage.setTitle("Settings HQ");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(15);
        grid.setHgap(15);


        //message label
        Label messageLabel = new Label("Send a message");
        GridPane.setConstraints(messageLabel, 1, 0);

        //message button
        Button messageButton = new Button();
        messageButton.setText("message");
        messageButton.setOnAction(e -> {
            try {
                new MessageTo(client).display();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });
        messageButton.setDisable(true);
        GridPane.setConstraints(messageButton, 2, 0);
        HBox msgButtons = new HBox(20);
        Pane spacerMessage = new Pane();
        HBox.setHgrow(spacerMessage, Priority.ALWAYS);
        msgButtons.getChildren().addAll(messageLabel, spacerMessage, messageButton);

        //joinTopic Label
        Label joinTopicLabel = new Label("Join a topic");
        GridPane.setConstraints(joinTopicLabel, 1, 1);

        //joinTopic button
        Button joinTopicButton = new Button();
        joinTopicButton.setText("Join");
        joinTopicButton.setDisable(true);
        joinTopicButton.setOnAction(e -> {
            try{
                new JoinTopicWindow(client).display();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(joinTopicButton, 2, 1);
        HBox topicButtons = new HBox(20);
        Pane spacerJoinTopic = new Pane();
        HBox.setHgrow(spacerJoinTopic, Priority.ALWAYS);
        topicButtons.getChildren().addAll(joinTopicLabel, spacerJoinTopic, joinTopicButton);


        //messageTopic Label
        Label messageTopicLabel = new Label("Message");
        GridPane.setConstraints(messageTopicLabel, 1, 2);



        //messageTopic Button
        Button messageTopicButton = new Button();
        messageTopicButton.setText("Message Topic");
        GridPane.setConstraints(messageTopicButton, 2, 2);
        messageTopicButton.setDisable(true);
        messageTopicButton.setOnAction(e -> {
            new MessageTopic(client).display();
        });
        HBox msgTopic = new HBox(20);
        Pane spacerMessageTopic = new Pane();
        HBox.setHgrow(spacerMessageTopic, Priority.ALWAYS);
        msgTopic.getChildren().addAll(messageTopicLabel, spacerMessageTopic, messageTopicButton);


        //leaveTopic Label
        Label leaveLabel = new Label("Leave a Topic");
        GridPane.setConstraints(leaveLabel, 1, 3);

        //leaveTopic button
        Button leaveButton = new Button();
        leaveButton.setOnAction(e -> {
            new LeaveTopicWindow(client).display();
        });

        leaveButton.setText("Leave");
        leaveButton.setDisable(true);
        GridPane.setConstraints(leaveButton, 2, 3);
        HBox leaveTopicButtons = new HBox(20);
        Pane spacerLeaveTopic = new Pane();
        HBox.setHgrow(spacerLeaveTopic, Priority.ALWAYS);
        leaveTopicButtons.getChildren().addAll(leaveLabel, spacerLeaveTopic, leaveButton);

        //inviteTopic Label
        Label inviteLabel = new Label("Invite to Topic");
        GridPane.setConstraints(inviteLabel, 1, 4);

        //inviteTopic button
        Button inviteButton = new Button();
        inviteButton.setOnAction(e -> {
            new InviteTopicWindow(client).display();
        });

        inviteButton.setText("Invite");
        inviteButton.setDisable(true);
        GridPane.setConstraints(leaveButton, 2, 4);
        HBox inviteTopicButtons = new HBox(20);
        Pane spacerInviteTopic = new Pane();
        HBox.setHgrow(spacerInviteTopic, Priority.ALWAYS);
        inviteTopicButtons.getChildren().addAll(inviteLabel, spacerInviteTopic, inviteButton);


        //ShowTopic Label
        Label showTopicLabel = new Label("Show Topics");
        GridPane.setConstraints(showTopicLabel, 1, 6);

        //ShowTopic Button
        Button messageShowTopic = new Button();
        messageShowTopic.setText("Show Topics");
        GridPane.setConstraints(messageShowTopic, 2, 6);
        messageShowTopic.setDisable(true);
        messageShowTopic.setOnAction(e -> {
            showTopic.display();
        });
        HBox showTopic = new HBox(20);
        Pane spacerShowTopic = new Pane();
        HBox.setHgrow(spacerShowTopic, Priority.ALWAYS);
        showTopic.getChildren().addAll(showTopicLabel, spacerShowTopic, messageShowTopic);


        //Login Label
        Label loginLabel = new Label("Login");
        GridPane.setConstraints(loginLabel, 1, 7);

        //Login button
        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setOnAction(e -> {
            try {
                LoginWindow loginWindow = new LoginWindow(client);
                loginWindow.display();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        GridPane.setConstraints(loginButton, 2, 7);
        HBox loginButtons = new HBox(20);
        Pane spacerLogin = new Pane();
        HBox.setHgrow(spacerLogin, Priority.ALWAYS);
        loginButtons.getChildren().addAll(loginLabel, spacerLogin, loginButton);

        //Logout Label
        Label logoutLabel = new Label("Logout");
        GridPane.setConstraints(logoutLabel, 1, 7);

        //Logout Button
        Button logoutButton = new Button();
        logoutButton.setText("Logout");
        logoutButton.setDisable(true);
        GridPane.setConstraints(logoutButton, 2, 7);
        HBox logoutButtons = new HBox(20);
        Pane spacerLogout = new Pane();
        HBox.setHgrow(spacerLogout, Priority.ALWAYS);
        logoutButtons.getChildren().addAll(logoutLabel, spacerLogout, logoutButton);

//                                ~~~~~~~ USERS LISTVIEW ~~~~~~~~~

        VBox vboxUsers = new VBox(20);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        vboxUsers.getChildren().add(listView);
        GridPane.setConstraints(vboxUsers, 0, 0);

        VBox commands = new VBox(20);
        commands.getChildren().addAll(msgButtons, topicButtons, leaveTopicButtons, msgTopic, inviteTopicButtons,showTopic,loginButtons);
        GridPane.setConstraints(commands, 2, 0);

        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            String name = listView.getSelectionModel().getSelectedItem();
                            windowManager.getRoom(name);
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        mouseEvent.consume();
                    }
                }
            }
        });




//                                   ~~~~~~~ ADDING TO GRID ~~~~~~~~~

        grid.getChildren().addAll(commands, vboxUsers);
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();

//                                   ~~~~~~~ LISTENERS ~~~~~~~~~

        //Listener initialized once logged in in order to show Name
        client.registerListener("login", loginArgs -> {
            if (loginArgs[0].equalsIgnoreCase("successful")) {
                Platform.runLater(() -> {
                    Label homeName = new Label(String.format("Currently logged in as: %s", loginArgs[1]));
                    GridPane.setConstraints(homeName, 0, 2);
                    grid.getChildren().add(homeName);
                    stage.setTitle("Settings HQ - " + loginArgs[1]);
                    logoutButton.setDisable(false);
                    commands.getChildren().add(logoutButtons);
                    logoutButton.setOnAction(e -> {
                        try {
                            listView.getItems().clear();
                            client.logout(loginArgs[1]);
                            stage.close();
                            Platform.exit();
                            System.exit(0);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        //Logout after login if you close the window with the X and close the entire application
                        stage.setOnCloseRequest(ev -> {
                            try {
                                client.logout(loginArgs[1]);
                                stage.close();
                                Platform.exit();
                                System.exit(0);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    });
                });
            }
        });


        //Listener to show people online
        client.registerListener("Online", onlineArgs -> {
            if (onlineArgs[0].equalsIgnoreCase("there's:")) {
                Platform.runLater(() -> listView.getItems().add(onlineArgs[1]));
            }
        });



        //Listener to show people offline
        client.registerListener("Offline", offlineArgs -> {
            if (offlineArgs[0].equalsIgnoreCase("user:")) {
                Platform.runLater(() -> {

                    listView.getItems().remove(offlineArgs[1]);
                    //open a window for everyone e tells them you've just gone offline
//                    windowManager.getRoom(offlineArgs[1]).getTextArea().appendText(String.format("The user: %s has just gone offline.",offlineArgs[1]));
                });
            }
        });


        //Message received once logged in
        client.registerListener("login", loginArgs -> {
            if (loginArgs[0].equals("successful")) {
                client.registerListener("msg_received", msgArgs -> {
                    String name = msgArgs[0];
                    String msgBody = String.join(" ", Arrays.copyOfRange(msgArgs, 1, msgArgs.length));

                    Platform.runLater(() -> windowManager.getRoom(name).getTextArea().appendText(String.format("New message from %s: %s\n",name,msgBody)));
                });
            } else {
                System.err.println("Error during login)");
            }
        });

        //MessageTopic & topic_added received once logged in
        client.registerListener("login", loginArgs -> {
            if (loginArgs[0].equals("successful")) {
                client.registerListener("msg_topic", msgTopicArgs -> {
                    String topic = msgTopicArgs[0];
                    String msgBody = String.join(" ", Arrays.copyOfRange(msgTopicArgs, 1, msgTopicArgs.length));

                    Platform.runLater(() -> windowManager.getTopicRoom(topic).getTextArea().appendText(msgBody + "\n"));
                    System.out.println(msgBody);
                });
                //After login just to show, actually after joining a topic
                client.registerListener("topic_added", onlineArgs -> {
                        Platform.runLater(() -> this.showTopic.getTopicList().getItems().add(onlineArgs[0]));
                });

                client.registerListener("topic_removed", onlineArgs -> {
                    Platform.runLater(() -> this.showTopic.getTopicList().getItems().remove(onlineArgs[0]));
                });

            } else {
                System.err.println("Error during login)");
            }
        });


        //disable button after login successful
        client.registerListener("login", loginArgs -> {
            if (loginArgs[0].equalsIgnoreCase("successful")) {
                messageButton.setDisable(false);
                joinTopicButton.setDisable(false);
                messageTopicButton.setDisable(false);
                leaveButton.setDisable(false);
                loginButton.setDisable(true);
                inviteButton.setDisable(false);
                messageShowTopic.setDisable(false);

            }
        });
    }


}
