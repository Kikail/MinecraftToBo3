package com.example.minecraftbo3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent p = (Parent) root.load();
        Scene scene = new Scene(p);
        HelloController controller = (HelloController)root.getController();
        controller.stage = stage;
        stage.setTitle("MinecraftToBo3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}