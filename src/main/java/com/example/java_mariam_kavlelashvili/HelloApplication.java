package com.example.java_mariam_kavlelashvili;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    private static final String URL = "jdbc:mysql://localhost:3306/btu";
    private static final String USER = "root";
    private static final String PASSWORD = null;
    @Override
    public void start(Stage stage) throws IOException {

        VBox root = new VBox();
        Label info = new Label("Enter Product Info");
        Label name = new Label("Enter Product Name:");
        TextField nameTextfield = new TextField();
        Label quantity = new Label("Enter Product quantity:");
        TextField quantityTextfield = new TextField();
        Label Owner = new Label("Enter Product owner:");
        TextField OwnerTextfield = new TextField();
        Button AddProductButton = new Button("Add Product");
        Label AddInfo = new Label();
        Button getAllProductInfo = new Button("Get All Product Info");
        AddProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(URL,USER,PASSWORD);
                    PreparedStatement statement = connection.prepareStatement(
                            "insert into products(name,quantity,owner) values(?,?,?)");
                    statement.setString(1,nameTextfield.getText());
                    statement.setInt(2,Integer.parseInt(quantityTextfield.getText()));
                    statement.setString(3,OwnerTextfield.getText());
                    statement.executeUpdate();
                    AddInfo.setText("Info was Added Successfully");
                    nameTextfield.clear();
                    quantityTextfield.clear();
                    OwnerTextfield.clear();
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


            }
        });
        getAllProductInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(URL,USER,PASSWORD);
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from products");
                    List<Product> products = new ArrayList<Product>();
                    while (resultSet.next()) {
                        Product product = new Product();
                        product.setName(resultSet.getString("name"));
                        product.setQuantity(resultSet.getInt("quantity"));
                        product.setOwner(resultSet.getString("owner"));
                        products.add(product);
                    }
                    // Print the result
                    Map<String, Integer> collect = products.stream().collect(Collectors.groupingBy(Product::getName,Collectors.summingInt(Product::getQuantity)));
                    PieChart pieChart = new PieChart();
                    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
                    for(String s:collect.keySet()){
                        data.add(new PieChart.Data(s,collect.get(s)));
                    }
                    pieChart.setData(data);
                    root.getChildren().add(pieChart);
                    statement.close();
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        root.getChildren().addAll(info,name,nameTextfield,quantity,quantityTextfield,Owner,OwnerTextfield,AddProductButton,AddInfo,getAllProductInfo);
        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}