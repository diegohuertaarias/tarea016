module com.example.tarea016 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;

    opens com.example.tarea016 to javafx.fxml;
    exports com.example.tarea016;
}