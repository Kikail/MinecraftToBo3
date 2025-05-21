package com.example.minecraftbo3;

import fromMinecraft.SchematicExtractor;

import javafx.application.Platform;
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
import toRadiant.Map;
import toRadiant.ToRadiantPrefab;

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
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File","*.litematic","*.schem",".schematic"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            File fileIds = new File("./MinecraftIds.txt");
            if(!fileIds.exists()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Minecraft IDs file not found");
                alert.setHeaderText("Please put the minecraft IDs file in the current directory");
                alert.setContentText("");
                alert.showAndWait();
                return;
            }

            listView.getItems().clear();
            listView.getItems().add(new Label(file.getAbsolutePath()));
            // On doit deja transformer le .schem en .txt
            Map map = new Map();

            SchematicExtractor extractor = new SchematicExtractor();
            extractor.Extract(file.getAbsolutePath(), map, fileIds,this);
        }
    }

    @FXML
    void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        File selectedFile = files.get(0);

        File fileIDs = new File("./MinecraftIds.txt");
        if(!fileIDs.exists()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Minecraft IDs file not found");
            alert.setHeaderText("Please put the minecraft IDs file in the current directory");
            alert.setContentText("");
            alert.showAndWait();
            return;
        }

        String filepath = selectedFile.getAbsolutePath();
        if(filepath.endsWith(".litematic") || filepath.endsWith(".schem") || filepath.endsWith(".schematic")) {
            listView.getItems().clear();
            listView.getItems().add(new Label(filepath));
            // On doit deja transformer le .schem en .txt
            Map map = new Map();

            SchematicExtractor extractor = new SchematicExtractor();
            extractor.Extract(filepath, map, fileIDs,this);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid File Type");
            alert.setHeaderText("Please Select a  file");
            alert.setContentText("");
            alert.showAndWait();
        }
    }

    @FXML
    void test(MouseEvent event) {

    }

    public void addLabel(String text){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                listView.getItems().add(new Label(text));
            }
        };
        if(Platform.isFxApplicationThread()){
            runnable.run();
        }
        else {
            Platform.runLater(runnable);
        }
    }

}