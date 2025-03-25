import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents level 2 of the game.
 * Extends the Game class and provides the implementation for level 2 gameplay.
 */
public class Level2 extends Game {
	int mermi = 3;
	
	/**
	 * Constructs a new Level2 object.
	 *
	 * @param primaryStage       The primary stage of the JavaFX application.
	 * @param selectedBackground The ImageView representing the selected background.
	 * @param selectedCrosshair  The ImageView representing the selected crosshair.
	 * @param selectedForeground The ImageView representing the selected foreground.
	 * @param rBackground        The ImageView representing the right background.
	 * @param lBackground        The ImageView representing the left background.
	 * @param width              The width of the scene.
	 * @param height             The height of the scene.
	 */
	Level2(Stage primaryStage, ImageView selectedBackground, ImageView selectedCrosshair, ImageView selectedForeground, ImageView rBackground, ImageView lBackground, double width, double height) {
		StackPane level2Root = new StackPane();
		Scene level2Scene = new Scene(level2Root, width, height);
		
		selectedBackground.setFitWidth(level2Scene.getWidth());
		selectedBackground.setFitHeight(level2Scene.getHeight());
		selectedForeground.setFitWidth(level2Scene.getWidth());
		selectedForeground.setFitHeight(level2Scene.getHeight());
		selectedCrosshair.setScaleX(SCALE);
		selectedCrosshair.setScaleY(SCALE);
		
		primaryStage.setScene(level2Scene);
		Text levelText = text("LEVEL 2/6", SCALE * 7, 0, SCALE * -110);
		Text ammoText = text("Ammo Left: " + mermi, SCALE * 7, SCALE * 100, SCALE * -110);
		Text gameText = text("", SCALE * 12, 0, SCALE * -12);
		Text text1 = text("", SCALE * 12, 0, SCALE * 0);
		Text text2 = text("", SCALE * 12, 0, SCALE * 12);
		
		List<String> duckPaths = Arrays.asList("assets/duck_black/1.png", "assets/duck_black/2.png", "assets/duck_black/3.png");
		
		List<Image> ducks = new ArrayList<>();
		for (String imagePath : duckPaths) {
			ducks.add(new Image(imagePath));
		}
		ImageView duck = new ImageView(ducks.get(0));
		duck.setScaleX(SCALE);
		duck.setScaleY(SCALE);
		final double[] movex = {SCALE * 10};
		final double[] movey = {-SCALE * 10};
		duck.setTranslateX(SCALE * 50);
		duck.setTranslateY(-SCALE * 60);
		
		Timeline duckAnimation = new Timeline();
		duckAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			int currentIndex = ducks.indexOf(duck.getImage());
			int nextIndex = (currentIndex + 1) % ducks.size();
			double newPositionX = duck.getTranslateX() + movex[0];
			double newPositionY = duck.getTranslateY() + movey[0];
			duck.setImage(ducks.get(nextIndex));
			
			if (newPositionX >= selectedBackground.getTranslateX() + width / 2 && movex[0] > 0 || newPositionX <= selectedBackground.getTranslateX() - width / 2 && movex[0] < 0) {
				duck.setScaleX(-duck.getScaleX());
				movex[0] = -1 * movex[0];
			}
			if (newPositionY >= level2Scene.getHeight() / 2 || newPositionY <= -level2Scene.getHeight() / 2) {
				duck.setScaleY(-duck.getScaleY());
				movey[0] = -1 * movey[0];
			}
			duck.setTranslateX(newPositionX);
			duck.setTranslateY(newPositionY);
			
		}));
		
		Media media = new Media(new File("assets/effects/Gunshot.mp3").toURI().toString());
		Media media2 = new Media(new File("assets/effects/DuckFalls.mp3").toURI().toString());
		MediaPlayer mediaPlayer2 = new MediaPlayer(new Media(new File("assets/effects/Levelcompleted.mp3").toURI().toString()));
		MediaPlayer mediaPlayer3 = new MediaPlayer(new Media(new File("assets/effects/GameOver.mp3").toURI().toString()));
		
		level2Scene.setOnMouseClicked(e -> {
			if (mermi != 0) {
				MediaPlayer mediaPlayer = new MediaPlayer(media);
				mediaPlayer.setCycleCount(1);
				mediaPlayer.setVolume(VOLUME);
				mediaPlayer.play();
				
				double mouseX = e.getX() - width / 2;
				double mouseY = e.getY() - height / 2;
				double duckX = duck.getTranslateX();
				double duckY = duck.getTranslateY();
				mermi--;
				ammoText.setText("Ammo Left: " + mermi);
				
				double distance = Math.sqrt(Math.pow(mouseX - duckX, 2) + Math.pow(mouseY - duckY, 2));
				if (distance <= SCALE * 15) {
					MediaPlayer mediaPlayerr = new MediaPlayer(media2);
					mediaPlayerr.setCycleCount(1);
					mediaPlayerr.setVolume(VOLUME);
					mediaPlayerr.play();
					
					duckAnimation.stop();
					Image huntedImage = new Image("assets/duck_black/7.png");
					duck.setImage(huntedImage);
					
					Timeline timeline1 = new Timeline();
					KeyFrame keyFrame = new KeyFrame(Duration.millis(150), e1 -> {
						Image fallingImage = new Image("assets/duck_black/8.png");
						duck.setScaleY(SCALE);
						duck.setImage(fallingImage);
					});
					timeline1.getKeyFrames().add(keyFrame);
					timeline1.play();
					
					Timeline timeline2 = new Timeline();
					KeyFrame keyFrame1 = new KeyFrame(Duration.millis(150), e1 -> {
						double currentY = duck.getTranslateY();
						duck.setTranslateY(currentY + SCALE * 7.5);
					});
					timeline2.getKeyFrames().add(keyFrame1);
					timeline2.setCycleCount(Timeline.INDEFINITE);
					timeline2.play();
					gameText.setText("YOU WIN");
					text1.setText("Press ENTER to play next level");
					Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(0.8), e1 -> text1.setVisible(!text1.isVisible())));
					timeline3.setCycleCount(Animation.INDEFINITE);
					timeline3.play();
					mermi = 0;
					mediaPlayer2.setVolume(VOLUME);
					mediaPlayer2.play();
					level2Scene.setOnKeyPressed(e1 -> {
						if (e1.getCode() == KeyCode.ENTER) {
							mediaPlayerr.stop();
							mediaPlayer2.stop();
							new Level3(primaryStage, selectedBackground, selectedCrosshair, selectedForeground, rBackground, lBackground, width, height);
						}
					});
					
				} else if (mermi == 0) {
					gameText.setText("GAME OVER");
					text1.setText("Press ENTER to play again");
					text2.setText("Press ESC to exit");
					Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(0.8), e1 -> {
						text1.setVisible(!text1.isVisible());
						text2.setVisible(!text2.isVisible());
					}));
					
					timeline4.setCycleCount(Animation.INDEFINITE);
					timeline4.play();
					mediaPlayer3.setVolume(VOLUME);
					mediaPlayer3.play();
					level2Scene.setOnKeyPressed(e1 -> {
						if (e1.getCode() == KeyCode.ENTER) {
							mediaPlayer3.stop();
							new Level1(primaryStage, selectedBackground, selectedCrosshair, selectedForeground, rBackground, lBackground, width, height);
						}
						if (e1.getCode() == KeyCode.ESCAPE) {
							mediaPlayer3.stop();
							new Title(primaryStage, null);
						}
					});
				}
			}
		});
		
		level2Scene.setCursor(Cursor.NONE);
		level2Scene.setOnMouseMoved(e -> {
			double mouseX = e.getX();
			double mouseY = e.getY();
			selectedCrosshair.setTranslateX(mouseX - width / 2);
			selectedCrosshair.setTranslateY(mouseY - height / 2);
			if (selectedCrosshair.getTranslateX() > width / 2 - 40) {
				Scrolling(-1, selectedCrosshair, selectedBackground, selectedForeground, rBackground, lBackground, width);
			} else if (selectedCrosshair.getTranslateX() < -width / 2 + 40) {
				Scrolling(1, selectedCrosshair, selectedBackground, selectedForeground, rBackground, lBackground, width);
			}
		});
		
		level2Root.getChildren().addAll(selectedBackground, rBackground, lBackground, duck, selectedForeground, gameText, ammoText, levelText, text1, text2, selectedCrosshair);
		duckAnimation.setCycleCount(Timeline.INDEFINITE);
		duckAnimation.play();
	}
}
