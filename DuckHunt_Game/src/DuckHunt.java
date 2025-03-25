import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * The DuckHunt class is the main class for the Duck Hunt game.
 * It extends the JavaFX Application class and serves as the entry point for the game.
 */
public class DuckHunt extends Application {
	double SCALE = 3;
	double VOLUME = 0.025;
	MediaPlayer titleMusic;
	
	/**
	 * The main method is the entry point of the application.
	 * It launches the JavaFX application by calling the launch method.
	 *
	 * @param args command line arguments (not used in this application)
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * The start method is called when the JavaFX application is started.
	 * It sets up the primary stage and initializes the game.
	 *
	 * @param primaryStage the primary stage of the application
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("HUBBM Duck Hunt");
		primaryStage.getIcons().add(new Image("assets/favicon/1.png"));
		new Title(primaryStage, titleMusic);
	}
}

