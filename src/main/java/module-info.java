module com.example.minecraftbo3 {

    requires NBT;
    requires java.desktop;
    requires schematic4j;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;


    opens com.example.minecraftbo3 to javafx.fxml;
    exports com.example.minecraftbo3;
}