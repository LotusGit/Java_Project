module com.example.controleyassinemchimech {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;


    opens com.example.controleyassinemchimech to javafx.fxml;
    exports com.example.controleyassinemchimech;
}