module com.example.minecraftbo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires NBT;
    requires java.desktop;


    opens com.example.minecraftbo3 to javafx.fxml;
    exports com.example.minecraftbo3;
}