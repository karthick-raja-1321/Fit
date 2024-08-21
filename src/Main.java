import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage stage_var;
    public static Scene login;
    public static Scene signup;
    public static Scene table;
    public static Scene user;
    public static Scene workout;
    public static Scene category;

    @Override
    public void start(Stage stage) throws IOException {
    	
    	Parent root = FXMLLoader.load(getClass().getResource("./getStarted.fxml"));
    	Parent root1 = FXMLLoader.load(getClass().getResource("./login.fxml"));
    	Parent root2 = FXMLLoader.load(getClass().getResource("./signup.fxml"));
    	Parent root3 = FXMLLoader.load(getClass().getResource("./user.fxml"));
    	Parent root4 = FXMLLoader.load(getClass().getResource("./workout.fxml"));
    	Parent root5 = FXMLLoader.load(getClass().getResource("./category.fxml"));


    	Scene scene = new Scene(root);
    	Scene scene1 = new Scene(root1);
    	Scene scene2 = new Scene(root2);
    	Scene scene3 = new Scene(root3);
    	Scene scene4 = new Scene(root4);
    	Scene scene5 = new Scene(root5);
   	
        stage_var = stage;
        login = scene1;
        signup = scene2;
        user = scene3;
        workout = scene4;
        category = scene5;

        stage.setTitle("Fitness Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
