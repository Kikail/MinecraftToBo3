package com.example.minecraftbo3;

import fromMinecraft.SchematicExtractor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import toRadiant.ToRadiantPrefab;

import java.awt.*;
import java.io.File;
import java.util.List;

public class HelloController {
    public Stage stage;

    @FXML
    private HBox dragZone;

    @FXML
    private ListView<Label> listView;

    @FXML
    void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void lookForFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Litematic File","*.litematic"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            listView.getSelectionModel().clearSelection();
            listView.getItems().add(new Label(file.getAbsolutePath()));
            // On doit deja transformer le .schem en .txt
            ToRadiantPrefab.createPrefab(SchematicExtractor.Extract(file.getAbsolutePath()),this);
        }
    }

    @FXML
    void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        File selectedFile = files.get(0);

        String filepath = selectedFile.getAbsolutePath();
        if(filepath.endsWith(".litematic")) {
            listView.getSelectionModel().clearSelection();
            listView.getItems().add(new Label(filepath));
            // On doit deja transformer le .schem en .txt
            ToRadiantPrefab.createPrefab(SchematicExtractor.Extract(filepath),this);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid File Type");
            alert.setHeaderText("Please Select a .litematic file");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    @FXML
    void test(MouseEvent event) {

    }

    public void addLabel(String text){
        listView.getItems().add(new Label(text));
    }

}