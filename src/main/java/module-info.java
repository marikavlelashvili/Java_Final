module com.example.java_mariam_kavlelashvili {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.java_mariam_kavlelashvili to javafx.fxml;
    exports com.example.java_mariam_kavlelashvili;
}