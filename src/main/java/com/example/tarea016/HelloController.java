package com.example.tarea016;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bson.Document;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

public class HelloController extends Application {

    private TableView<Product> table = new TableView<>();
    private MongoCollection<Document> collection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Configuración de la conexión a MongoDB
        String connectionString = "mongodb+srv://lector:lector@cluster0.lxijdu9.mongodb.net/";
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("ejemplo");
        collection = database.getCollection("productos");

        // Configurar la tabla
        TableColumn<Product, String> nameColumn = new TableColumn<>("Nombre");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Precio");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);

        // Botón para cargar los datos
        Button loadButton = new Button("Cargar Datos");
        loadButton.setOnAction(e -> loadData());

        VBox vbox = new VBox(loadButton, table);
        Scene scene = new Scene(vbox, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX y MongoDB");
        primaryStage.show();
    }

    private void loadData() {
        List<Product> products = new ArrayList<>();
        for (Document doc : collection.find()) {
            String name = doc.getString("name");
            double price = doc.getDouble("price");
            products.add(new Product(name, price));
        }
        table.getItems().setAll(products);
    }

    public static class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }
}
