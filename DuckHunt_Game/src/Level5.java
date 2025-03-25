import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
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
 * Represents level 5 of the game.
 * Extends the Game class and provides the implementation for level 5 gameplay.
 */
public class Level5 extends Game {
	int mermi = 27;
	int liveducks = 3;
	ImageView selectedBackground;
	ImageView selectedCrosshair;
	ImageView selectedForeground;
	ImageView rBackground;
	ImageView lBackground;
	
	/**
	 * Constructs a new Level5 object.
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
	Level5(Stage primaryStage, ImageView selectedBackground, ImageView selectedCrosshair, ImageView selectedForeground, ImageView rBackground, ImageView lBackground, double width, double height) {
		this.selectedBackground=selectedBackground;
		this.selectedCrosshair=selectedCrosshair;
		this.selectedForeground=selectedForeground;
		this.rBackground=rBackground;
		this.lBackground=lBackground;
		
		StackPane level5Root = new StackPane();
		Scene level5Scene = new Scene(level5Root, width, height);
		
		selectedBackground.setFitWidth(level5Scene.getWidth());
		selectedBackground.setFitHeight(level5Scene.getHeight());
		selectedForeground.setFitWidth(level5Scene.getWidth());
		selectedForeground.setFitHeight(level5Scene.getHeight());
		selectedCrosshair.setScaleX(SCALE);
		selectedCrosshair.setScaleY(SCALE);
		
		primaryStage.setScene(level5Scene);
		Text levelText = text("LEVEL 5/6", SCALE * 7, 0, SCALE * -110);
		Text ammoText = text("Ammo Left: " + (mermi / 3), SCALE * 7, SCALE * 100, SCALE * -110);
		Text gameText = text("", SCALE * 12, 0, SCALE * -12);
		Text text1 = text("", SCALE * 12, 0, SCALE * 0);
		Text text2 = text("", SCALE * 12, 0, SCALE * 12);
		
		
		List<String> duckPaths1 = Arrays.asList("assets/duck_black/1.png", "assets/duck_black/2.png", "assets/duck_black/3.png", "assets/duck_black/7.png", "assets/duck_black/8.png");
		List<String> duckPaths2 = Arrays.asList("assets/duck_red/4.png", "assets/duck_red/5.png", "assets/duck_red/6.png", "assets/duck_red/7.png", "assets/duck_red/8.png");
		List<String> duckPaths3 = Arrays.asList("assets/duck_blue/4.png", "assets/duck_blue/5.png", "assets/duck_blue/6.png", "assets/duck_blue/7.png", "assets/duck_blue/8.png");
		
		List<Image> ducks1 = new ArrayList<>();
		for (String imagePath : duckPaths1) {
			ducks1.add(new Image(imagePath));
		}
		ImageView duck1 = new ImageView(ducks1.get(0));
		duck1.setScaleX(SCALE);
		duck1.setScaleY(SCALE);
		final double[] movex1 = {SCALE * 15};
		final double[] movey1 = {-SCALE * 15};
		
		List<Image> ducks2 = new ArrayList<>();
		for (String imagePath : duckPaths2) {
			ducks2.add(new Image(imagePath));
		}
		ImageView duck2 = new ImageView(ducks2.get(0));
		duck2.setScaleX(SCALE);
		duck2.setScaleY(SCALE);
		final double[] movex2 = {SCALE * 15};
		duck2.setTranslateY(-SCALE * 60);
		duck2.setTranslateX(-SCALE * 50);
		
		List<Image> ducks3 = new ArrayList<>();
		for (String imagePath : duckPaths3) {
			ducks3.add(new Image(imagePath));
		}
		ImageView duck3 = new ImageView(ducks3.get(0));
		duck3.setScaleX(SCALE);
		duck3.setScaleY(SCALE);
		final double[] movex3 = {SCALE * 15};
		duck3.setTranslateY(-SCALE*30);
		duck3.setTranslateX(SCALE*60);
		
		Timeline duckAnimation1 = new Timeline();
		duckAnimation1.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			int currentIndex = ducks1.indexOf(duck1.getImage());
			int nextIndex = (currentIndex + 1) % ducks1.size();
			if (nextIndex == 3) nextIndex = 0;
			double newPositionX = duck1.getTranslateX() + movex1[0];
			double newPositionY = duck1.getTranslateY() + movey1[0];
			duck1.setImage(ducks1.get(nextIndex));
			
			if (newPositionX >= selectedBackground.getTranslateX() + width / 2 && movex1[0] > 0 || newPositionX <= selectedBackground.getTranslateX() - width / 2 && movex1[0] < 0) {
				duck1.setScaleX(-duck1.getScaleX());
				movex1[0] = -1 * movex1[0];
			}
			if (newPositionY >= level5Scene.getHeight() / 2 || newPositionY <= -level5Scene.getHeight() / 2 || checkCollision(duck1, duck2) || checkCollision(duck1, duck3)) {
				duck1.setScaleY(-duck1.getScaleY());
				movey1[0] = -1 * movey1[0];
			}
			
			duck1.setTranslateX(newPositionX);
			duck1.setTranslateY(newPositionY);
		}));
		
		Timeline duckAnimation2 = new Timeline();
		duckAnimation2.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			int currentIndex = ducks2.indexOf(duck2.getImage());
			int nextIndex = (currentIndex + 1) % ducks2.size();
			if (nextIndex == 3) nextIndex = 0;
			double newPositionX = duck2.getTranslateX() + movex2[0];
			duck2.setImage(ducks2.get(nextIndex));
			
			if (newPositionX >= selectedBackground.getTranslateX() + width / 2 && movex2[0] > 0 || newPositionX <= selectedBackground.getTranslateX() - width / 2 && movex2[0] < 0) {
				duck2.setScaleX(-duck2.getScaleX());
				movex2[0] = -1 * movex2[0];
			}
			
			duck2.setTranslateX(newPositionX);
		}));
		
		Timeline duckAnimation3 = new Timeline();
		duckAnimation3.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
			int currentIndex = ducks3.indexOf(duck3.getImage());
			int nextIndex = (currentIndex + 1) % ducks3.size();
			if (nextIndex == 3) nextIndex = 0;
			double newPositionX = duck3.getTranslateX() + movex3[0];
			duck3.setImage(ducks3.get(nextIndex));
			
			if (newPositionX >= selectedBackground.getTranslateX() + width / 2 && movex3[0] > 0 || newPositionX <= selectedBackground.getTranslateX() - width / 2 && movex3[0] < 0) {
				duck3.setScaleX(-duck3.getScaleX());
				movex3[0] = -1 * movex3[0];
			}
			
			duck3.setTranslateX(newPositionX);
		}));
		
		Media media = new Media(new File("assets/effects/Gunshot.mp3").toURI().toString());
		level5Scene.setOnMouseClicked(e -> {
			MediaPlayer mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setCycleCount(1);
			mediaPlayer.setVolume(VOLUME);
			if (mermi != 0) mediaPlayer.play();
			shot(primaryStage, e, level5Scene, duck1, ammoText, gameText, text1, text2, ducks1, duckAnimation1, width, height);
			shot(primaryStage, e, level5Scene, duck2, ammoText, gameText, text1, text2, ducks2, duckAnimation2, width, height);
			shot(primaryStage, e, level5Scene, duck3, ammoText, gameText, text1, text2, ducks3, duckAnimation3, width, height);
		});
		
		level5Scene.setCursor(Cursor.NONE);
		level5Scene.setOnMouseMoved(e -> {
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
		
		level5Root.getChildren().addAll(selectedBackground, rBackground, lBackground, duck1, duck2, duck3, selectedForeground, gameText, ammoText, levelText, text1, text2, selectedCrosshair);
		duckAnimation1.setCycleCount(Timeline.INDEFINITE);
		duckAnimation2.setCycleCount(Timeline.INDEFINITE);
		duckAnimation3.setCycleCount(Timeline.INDEFINITE);
		duckAnimation1.play();
		duckAnimation2.play();
		duckAnimation3.play();
		
	}
	
	/**
	 * Handles the shot event when the user clicks the mouse.
	 *
	 * @param primaryStage The primary stage of the JavaFX application.
	 * @param event         The MouseEvent representing the mouse click event.
	 * @param level5Scene   The Scene of level 5.
	 * @param duck          The ImageView representing the duck.
	 * @param ammoText      The Text representing the ammo counter.
	 * @param gameText      The Text representing the game status.
	 * @param text1         The Text representing additional text 1.
	 * @param text2         The Text representing additional text 2.
	 * @param ducks         The list of Images representing the duck animation frames.
	 * @param timeline      The Timeline representing the duck animation timeline.
	 * @param width         The width of the scene.
	 * @param height        The height of the scene.
	 */
	private void shot(Stage primaryStage, MouseEvent event, Scene level5Scene, ImageView duck, Text ammoText, Text gameText, Text text1, Text text2, List<Image> ducks, Timeline timeline, double width, double height) {
		Media media2 = new Media(new File("assets/effects/DuckFalls.mp3").toURI().toString());
		MediaPlayer mediaPlayer2 = new MediaPlayer(new Media(new File("assets/effects/Levelcompleted.mp3").toURI().toString()));
		MediaPlayer mediaPlayer3 = new MediaPlayer(new Media(new File("assets/effects/GameOver.mp3").toURI().toString()));
		mediaPlayer2.setVolume(VOLUME);
		mediaPlayer3.setVolume(VOLUME);
		
		if (mermi != 0) {
			double mouseX = event.getX() - width / 2;
			double mouseY = event.getY() - height / 2;
			double duckX = duck.getTranslateX();
			double duckY = duck.getTranslateY();
			mermi--;
			ammoText.setText("Ammo Left: " + (mermi / 3));
			
			double distance = Math.sqrt(Math.pow(mouseX - duckX, 2) + Math.pow(mouseY - duckY, 2));
			if (distance <= SCALE * 10) {
				MediaPlayer mediaPlayerr = new MediaPlayer(media2);
				mediaPlayerr.setCycleCount(1);
				mediaPlayerr.setVolume(VOLUME);
				mediaPlayerr.play();
				
				timeline.stop();
				duck.setImage(ducks.get(3));
				liveducks--;
				Timeline timeline1 = new Timeline();
				KeyFrame keyFrame = new KeyFrame(Duration.millis(150), e -> {
					duck.setImage(ducks.get(4));
					duck.setScaleY(SCALE);
				});
				timeline1.getKeyFrames().add(keyFrame);
				timeline1.play();
				
				Timeline timeline2 = new Timeline();
				KeyFrame keyFrame1 = new KeyFrame(Duration.millis(150), e -> {
					double currentY = duck.getTranslateY();
					duck.setTranslateY(currentY + SCALE * 7.5);
				});
				timeline2.getKeyFrames().add(keyFrame1);
				timeline2.setCycleCount(Timeline.INDEFINITE);
				timeline2.play();
				if (liveducks == 0) {
					gameText.setText("YOU WIN");
					text1.setText("Press ENTER to play next level");
					Timeline timeline3 = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> text1.setVisible(!text1.isVisible())));
					timeline3.setCycleCount(Animation.INDEFINITE);
					timeline3.play();
					mermi = 0;
					mediaPlayer2.play();
					level5Scene.setOnKeyPressed(event1 -> {
						if (event1.getCode() == KeyCode.ENTER) {
							new Level6(primaryStage, selectedBackground, selectedCrosshair, selectedForeground, rBackground, lBackground, width, height);
							mediaPlayer2.stop();
							mediaPlayerr.stop();
						}
					});
				}
				
			} else if (mermi == 0) {
				gameText.setText("GAME OVER");
				text1.setText("Press ENTER to play again");
				text2.setText("Press ESC to exit");
				Timeline timeline4 = new Timeline(new KeyFrame(Duration.seconds(0.8), e -> {
					text1.setVisible(!text1.isVisible());
					text2.setVisible(!text2.isVisible());
				}));
				timeline4.setCycleCount(Animation.INDEFINITE);
				timeline4.play();
				
				mediaPlayer3.play();
				level5Scene.setOnKeyPressed(e -> {
					if (e.getCode() == KeyCode.ENTER) {
						mediaPlayer3.stop();
						new Level1(primaryStage, selectedBackground, selectedCrosshair, selectedForeground, rBackground, lBackground, width, height);
					}
					if (e.getCode() == KeyCode.ESCAPE) {
						new Title(primaryStage, null);
						mediaPlayer3.stop();
						
					}
				});
			}
		}
	}
}

