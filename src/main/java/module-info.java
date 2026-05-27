module com.example.miniproyecto2sudokuedadlepr {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyecto2sudokuedadlepr to javafx.fxml;
    exports com.example.miniproyecto2sudokuedadlepr;
}