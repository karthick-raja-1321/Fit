
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

public class WorkoutController {

    @FXML
    private TableColumn<?, ?> calorie;

    @FXML
    private TextField calories;

    @FXML
    private TextField duration;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TextField name;

    @FXML
    private TableColumn<?, ?> time;

    @FXML
    private TableColumn<?, ?> user;

    @FXML
    private TableView<Workout> workoutTable;

    private Workout selectedWorkout;

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user.setCellValueFactory(new PropertyValueFactory<>("workout"));
        time.setCellValueFactory(new PropertyValueFactory<>("duration"));
        calorie.setCellValueFactory(new PropertyValueFactory<>("calories"));
        loadData();
        workoutTable.setOnMouseClicked(this::handleRowSelect);
    }

    private void loadData(){
        try {
            List<Workout> workout = getWorkout();
            ObservableList<Workout> data = FXCollections.observableArrayList(workout);
            workoutTable.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Workout> getWorkout() throws SQLException {
        List<Workout> workout = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "select * from workout";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int duration = resultSet.getInt("duration");
            int calories = resultSet.getInt("calories");
            System.out.println(name);
            workout.add(new Workout(id, name, duration, calories));
        }
        return workout;
    }

    private void handleRowSelect(MouseEvent mouseEvent) {
        selectedWorkout = workoutTable.getSelectionModel().getSelectedItem();
        if (selectedWorkout != null) {
            name.setText(selectedWorkout.getWorkout());
            duration.setText(String.valueOf(selectedWorkout.getDuration()));
            calories.setText(String.valueOf(selectedWorkout.getCalories()));
        }
    }

    @FXML
    void handelCategory(ActionEvent event) {
        Main.stage_var.setTitle("Category Page");
        Main.stage_var.setScene(Main.category);
    }

    @FXML
    void handelDelete(ActionEvent event) throws SQLException {
        if(selectedWorkout != null){
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "delete from workout where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, selectedWorkout.getId());
            statement.execute();
            name.clear();
            duration.clear();
            calories.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User Deleted Successfully");
            alert.show();
            loadData();
        }
    }

    @FXML
    void handelInsert(ActionEvent event) throws SQLException {
        String Name = name.getText();
        int dur = Integer.parseInt(duration.getText());
        int cal = Integer.parseInt(calories.getText());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
        String sql = "insert into workout (name, duration, calories) values (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Name);
        statement.setInt(2, dur);
        statement.setInt(3, cal);
        statement.execute();
        name.clear();
        duration.clear();
        calories.clear();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("User Added Successfully");
        alert.show();
        loadData();
    }

    @FXML
    void handelUpdate(ActionEvent event) throws SQLException {
        if (selectedWorkout != null){
            String Name = name.getText();
            int dur = Integer.parseInt(duration.getText());
            int cal = Integer.parseInt(calories.getText());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "update workout set name=?, duration=?, calories=? where id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, Name);
            statement.setInt(2, dur);
            statement.setInt(3, cal);
            statement.setInt(4, selectedWorkout.getId());
            statement.execute();
            name.clear();
            duration.clear();
            calories.clear();
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