
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

public class UserController {

    @FXML
    private TableColumn<?, ?> ag;

    @FXML
    private TextField age;

    @FXML
    private TableColumn<?, ?> he;

    @FXML
    private TextField height;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> nm;

    @FXML
    private TextField user;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<?, ?> we;

    @FXML
    private TextField weight;

    private User selectedUser;

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nm.setCellValueFactory(new PropertyValueFactory<>("name"));
        ag.setCellValueFactory(new PropertyValueFactory<>("age"));
        he.setCellValueFactory(new PropertyValueFactory<>("height"));
        we.setCellValueFactory(new PropertyValueFactory<>("weight"));
        loadData();
        userTable.setOnMouseClicked(this::handleRowSelect);
    }

    private void loadData(){
        try {
            List<User> users = getUsers();
            ObservableList<User> data = FXCollections.observableArrayList(users);
            userTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleRowSelect(MouseEvent mouseEvent) {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            user.setText(selectedUser.getName());
            age.setText(String.valueOf(selectedUser.getAge()));
            height.setText(String.valueOf(selectedUser.getHeight()));
            weight.setText(String.valueOf(selectedUser.getWeight()));
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "select * from users";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            int height = resultSet.getInt("height");
            int weight = resultSet.getInt("weight");
            users.add(new User(id, name, age, height, weight));
        }
        return users;
    }

    @FXML
    void handelCategory(ActionEvent event) {
        Main.stage_var.setTitle("Category Page");
        Main.stage_var.setScene(Main.category);
    }

    @FXML
    void handelDelete(ActionEvent event) throws SQLException {
        if(selectedUser != null){
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "delete from users where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedUser.getId());
            statement.execute();
            user.clear();
            age.clear();
            height.clear();
            weight.clear();
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
        String Name = user.getText();
        int Age = Integer.parseInt(age.getText());
        int Height = Integer.parseInt(height.getText());
        int Weight = Integer.parseInt(weight.getText());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "insert into users (name, age, height, weight) values (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Name);
        statement.setInt(2, Age);
        statement.setInt(3, Height);
        statement.setInt(4, Weight);
        statement.execute();
        user.clear();
        age.clear();
        height.clear();
        weight.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("User Added Successfully");
        alert.show();
        loadData();
    }

    @FXML
    void handelUpdate(ActionEvent event) throws SQLException {
        if (selectedUser != null){
            String Name = user.getText();
            int Age = Integer.parseInt(age.getText());
            int Height = Integer.parseInt(height.getText());
            int Weight = Integer.parseInt(weight.getText());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "update users set name=?, age=?, height=?, weight=? where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Name);
            statement.setInt(2, Age);
            statement.setInt(3, Height);
            statement.setInt(4, Weight);
            statement.setInt(5, selectedUser.getId());
            statement.execute();
            user.clear();
            age.clear();
            height.clear();
            weight.clear();
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
        Main.stage_var.setTitle("Login");
        Main.stage_var.setScene(Main.login);
    }

}