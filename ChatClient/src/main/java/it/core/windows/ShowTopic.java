package it.core.windows;

import it.core.ChatClient;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ShowTopic {

    private WindowManager windowManager;
    private ListView<String> topicList = new ListView<>();
    private ChatClient client;

    public ShowTopic(ChatClient client){
        this.client = client;
    }

    public ListView<String> getTopicList() {
        return topicList;
    }

    public void display() {
        Stage window = new Stage();
        window.setMinWidth(300);
        window.setMinHeight(300);
        window.setTitle("Active Topics");

        VBox vboxUsers = new VBox(20);
        topicList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        vboxUsers.getChildren().add(topicList);
        GridPane.setConstraints(vboxUsers, 0, 0);


        topicList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        try {
                            String name = topicList.getSelectionModel().getSelectedItem();
                            windowManager = new WindowManager(client);
                            windowManager.getTopicRoom(name);
                        } catch (Exception exc) {
                            exc.printStackTrace();
                        }
                        mouseEvent.consume();
                    }
                }
            }
        });

        Scene scene = new Scene(vboxUsers, 200, 300);
        window.setScene(scene);
        window.show();
    }
}
