
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    @FXML
    private TableColumn<Category, String> category;

    @FXML
    private TableView<Category> categoryTable;

    @FXML
    private TableColumn<Category, Integer> id;

    @FXML
    private TextField name;

    private Category selectedCategory;

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        loadData();
        categoryTable.setOnMouseClicked(this::handleRowSelect);
    }

    private void handleRowSelect(MouseEvent mouseEvent) {
        selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            id.setText(String.valueOf(selectedCategory.getId()));
            name.setText(selectedCategory.getCategory());
        }
    }

    private void loadData(){
        try {
            List<Category> categories = getCategory();
            ObservableList<Category> data = FXCollections.observableArrayList(categories);
            categoryTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategory() throws SQLException {
        List<Category> category = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "select * from category";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
//            System.out.println(name);
            category.add(new Category(id, name));
        }
        return category;
    }

    @FXML
    void handelCategory(ActionEvent event) {
        Main.stage_var.setTitle("Category Page");
        Main.stage_var.setScene(Main.category);
    }

    @FXML
    void handelDelete(ActionEvent event) throws SQLException {
        if(selectedCategory != null){
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "delete from category where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedCategory.getId());
            statement.execute();
            name.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User Deleted Successfully");
            alert.show();
            loadData();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a particular field");
            alert.show();
        }
    }

    @FXML
    void handelInsert(ActionEvent event) throws SQLException {
        String categ = name.getText();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "insert into category (name) values (?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, categ);
        statement.execute();
        name.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("User Added Successfully");
        alert.show();
        loadData();
    }

    @FXML
    void handelUpdate(ActionEvent event) throws SQLException {
        if(selectedCategory != null){
            String categ = name.getText();
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "update category set name=? where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, categ);
            statement.setInt(2, selectedCategory.getId());
            statement.execute();
            name.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User Updated Successfully");
            alert.show();
            loadData();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a particular field");
            alert.show();
        }
    }

    @FXML
    void handelUser(ActionEvent event) {
        Main.stage_var.setTitle("User Page");
        Main.stage_var.setScene(Main.user);
    }

    @FXML
    void handelWorkout(ActionEvent event) {
    	Main.stage_var.setTitle("Workout Page");
    	Main.stage_var.setScene(Main.workout);
    }

    @FXML
    void handelLogout(ActionEvent event) {
    	Main.stage_var.setTitle("login");
    	Main.stage_var.setScene(Main.login);
    }

}