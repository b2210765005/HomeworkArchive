import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * The Title class represents the title screen of the Duck Hunt game.
 * It extends the Game class and displays the title screen with options to play or exit the game.
 */
public class Title extends Game {
	
	/**
	 * Constructs a new Title object with the specified primaryStage and titleMusic.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 * @param titleMusic   the MediaPlayer object representing the title music
	 */
	Title(Stage primaryStage, MediaPlayer titleMusic) {
		if (titleMusic == null) {
			titleMusic = new MediaPlayer(new Media(new File("assets/effects/Title.mp3").toURI().toString()));
			titleMusic.setVolume(VOLUME);
			titleMusic.setCycleCount(-1);
			titleMusic.play();
		}
		
		Text text1 = text("PRESS ENTER TO PLAY", SCALE * 15, 0, SCALE * 20);
		Text text2 = text("PRESS ESC TO EXIT", SCALE * 15, 0, SCALE * 50);
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(1), event -> {
					text1.setVisible(!text1.isVisible());
					text2.setVisible(!text2.isVisible());
				})
		);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		ImageView titleBackground = new ImageView(new Image("assets/welcome/1.png"));
		titleBackground.setPreserveRatio(true);
		titleBackground.setScaleX(SCALE);
		titleBackground.setScaleY(SCALE);
		double width = SCALE * titleBackground.getBoundsInLocal().getWidth();
		double height = SCALE * titleBackground.getBoundsInLocal().getHeight();
		
		StackPane root = new StackPane();
		root.getChildren().addAll(titleBackground, text1, text2);
		Scene welcomeScene = new Scene(root, width, height);
		
		MediaPlayer finalTitleMusic = titleMusic;
		welcomeScene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				new Selection(primaryStage, width, height, finalTitleMusic);
			} else if (e.getCode() == KeyCode.ESCAPE) {
				primaryStage.close();
			}
		});
		
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
	}
}
