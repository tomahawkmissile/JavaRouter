package ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Elijah Reed
 *
 */
public class UI extends Application {

	public static void initialize(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Javarouter");
		stage.show();
	}
}
