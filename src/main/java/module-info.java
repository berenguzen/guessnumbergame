module com.example.gameproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameproject to javafx.fxml;
    exports com.example.gameproject;
}