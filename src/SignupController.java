
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;

public class SignupController {

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    void handelLogin(){
        Main.stage_var.setTitle("Login");
        Main.stage_var.setScene(Main.login);
    }

    @FXML
    void handelSignup() throws SQLException {
        String User = username.getText();
        String Pass = password.getText();
        if(User.isEmpty() || Pass.isEmpty()){
            System.out.println("Enter the Credentilas correctly");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Enter the Credentilas correctly");
            alert.show();
        }
        else {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fit", "root", "root");
            String sql = "select * from acc where user_name=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, User);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                System.out.println("User Already Exist");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User Name Already Exist");
                alert.show();
            } else {
                String sql1 = "insert into acc values (?,?)";
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                statement1.setString(1, User);
                statement1.setString(2, Pass);
                statement1.execute();
                Main.stage_var.setTitle("User Page");
                Main.stage_var.setScene(Main.user);
                username.clear();
                password.clear();
            }
        }
    }
}