package com.example.minecraftbo3;

import fromMinecraft.SchematicExtractor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import toRadiant.Map;
import toRadiant.ToRadiantPrefab;

import javax.swing.text.html.Option;
import java.io.File;
import java.util.List;

public class HelloController {
    public Stage stage;

    @FXML
    private CheckBox checkAutoMerge;

    @FXML
    private CheckBox checkIncludeBlocks;

    @FXML
    private CheckBox checkIncludeModels;

    @FXML
    private CheckBox checkIncludePrefabs;

    @FXML
    private CheckBox checkUselessBlocks;

    @FXML
    private VBox dragZone;

    @FXML
    private ListView<Label> listView;

    @FXML
    private ProgressBar progressBar;

    private boolean processing = false;

    public Options options;

    public void updateOptions(){
        options.autoMerge = checkAutoMerge.isSelected();
        options.includeBlocks = checkIncludeBlocks.isSelected();
        options.includeModels = checkIncludeModels.isSelected();
        options.includePrefabs = checkIncludePrefabs.isSelected();
        options.deleteUselessBlocks = checkUselessBlocks.isSelected();
        System.out.println("AutoMerge: " + options.autoMerge + " vs " +checkAutoMerge.isSelected());
        System.out.println("IncludeBlocks: " + options.includeBlocks + " vs " +checkIncludeBlocks.isSelected());
        System.out.println("IncludeModels: " + options.includeModels + " vs " +checkIncludeModels.isSelected());
        System.out.println("IncludePrefabs: " + options.includePrefabs + " vs " +checkIncludePrefabs.isSelected());
        System.out.println("UselessBlocks: " + options.deleteUselessBlocks + " vs " +checkUselessBlocks.isSelected());
    }

    @FXML
    public void initialize() {
        options = new Options();
        checkAutoMerge.setSelected(options.autoMerge);
        checkIncludeBlocks.setSelected(options.includeBlocks);
        checkIncludeModels.setSelected(options.includeModels);
        checkIncludePrefabs.setSelected(options.includePrefabs);
        checkUselessBlocks.setSelected(options.deleteUselessBlocks);
    }

    @FXML
    void handleDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML public void handleMouseClick(MouseEvent arg0) {
        Object selected = listView.getSelectionModel().getSelectedItem();

        if (selected instanceof Label) {
            Label selectedLabel = (Label) selected;

            String text = selectedLabel.getText();
            System.out.println(text);

            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            Clipboard clipboard = Clipboard.getSystemClipboard();
            clipboard.setContent(content);

            System.out.println("Copied to clipboard: " + text);
        }
    }

    public static File findFileIgnoreCase(String folderPath, String targetFileName) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().equalsIgnoreCase(targetFileName)) {
                    return file;
                }
            }
        }
        return null;
    }

    @FXML
    void lookForFile(ActionEvent event) {
        if(processing) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File","*.litematic","*.schem",".schematic"));
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            File fileIds = findFileIgnoreCase("./","MinecraftIds.txt");
            if(fileIds != null && !fileIds.exists()){
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
            Map map = new Map(options);
            setProcessing(true);
            SchematicExtractor extractor = new SchematicExtractor();
            extractor.Extract(file.getAbsolutePath(), map, fileIds,this);
        }
    }

    @FXML
    void handleDrop(DragEvent event) {
        if(processing) return;

        List<File> files = event.getDragboard().getFiles();
        File selectedFile = files.get(0);

        File fileIDs = findFileIgnoreCase("./","MinecraftIds.txt");
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
            Map map = new Map(options);
            setProcessing(true);
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

    public void updateProgress(int current, int total){
        double progress = (double) current / total;
        progressBar.setProgress(progress);
    }
    public void showProgressBar(){
        progressBar.setVisible(true);
    }
    public void hideProgressBar(){
        progressBar.setVisible(false);
    }
    public void setProcessing(boolean processing){
        this.processing = processing;
    }

    @FXML
    void handleAutoMergeOption(ActionEvent event) {
        updateOptions();
    }
    @FXML
    void handleIncludeBlocks(ActionEvent event) {
        updateOptions();
    }
    @FXML
    void handleIncludeModels(ActionEvent event) {
        updateOptions();
    }
    @FXML
    void handleIncludePrefabs(ActionEvent event) {
        updateOptions();
    }
    @FXML
    void handleUselessBlocks(ActionEvent event) {
        updateOptions();
    }

}