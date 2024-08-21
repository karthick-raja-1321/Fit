
import javafx.fxml.FXML;

public class HelloController {

    @FXML
    void handleGetStarted(){
        Main.stage_var.setTitle("Login");
        Main.stage_var.setScene(Main.login);
    }
}