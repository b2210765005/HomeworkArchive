import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Selection class represents the selection screen of the Duck Hunt game.
 * It allows the player to choose background images and crosshair styles before starting the game.
 */
public class Selection extends Game {
	
	/**
	 * Constructs a new Selection object with the specified parameters.
	 *
	 * @param primaryStage the primary stage of the JavaFX application
	 * @param width         the width of the selection screen
	 * @param height        the height of the selection screen
	 * @param titleMusic    the MediaPlayer object representing the title music
	 */
	Selection(Stage primaryStage, double width, double height, MediaPlayer titleMusic) {
		Text selectionText1 = text("USE ARROW KEYS TO NAVIGATE", SCALE * 8, 0, SCALE * -110);
		Text selectionText2 = text("PRESS ENTER TO START", SCALE * 8, 0, SCALE * -100);
		Text selectionText3 = text("PRESS ESC TO EXIT", SCALE * 8, 0, SCALE * -90);
		
		List<String> backgroundImages = Arrays.asList(
				"assets/background/1.png",
				"assets/background/2.png",
				"assets/background/3.png",
				"assets/background/4.png",
				"assets/background/5.png",
				"assets/background/6.png"
		);
		AtomicInteger currentBackgroundIndex = new AtomicInteger(0);
		ImageView backgroundImage = new ImageView(backgroundImages.get(0));
		backgroundImage.setFitWidth(width);
		backgroundImage.setFitHeight(height);
		
		List<String> crosshairImages = Arrays.asList(
				"assets/crosshair/1.png",
				"assets/crosshair/2.png",
				"assets/crosshair/3.png",
				"assets/crosshair/4.png",
				"assets/crosshair/5.png",
				"assets/crosshair/6.png"
		);
		AtomicInteger currentCrosshairIndex = new AtomicInteger(0);
		ImageView crosshairImage = new ImageView(crosshairImages.get(0));
		crosshairImage.setScaleX(SCALE);
		crosshairImage.setScaleY(SCALE);
		crosshairImage.setMouseTransparent(true);
		
		List<String> foregroundImages = Arrays.asList(
				"assets/foreground/1.png",
				"assets/foreground/2.png",
				"assets/foreground/3.png",
				"assets/foreground/4.png",
				"assets/foreground/5.png",
				"assets/foreground/6.png"
		);
		
		StackPane root = new StackPane();
		Scene selectionScene = new Scene(root, width, height);
		root.getChildren().addAll(backgroundImage, selectionText1, selectionText2, selectionText3, crosshairImage);
		
		selectionScene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				new Title(primaryStage, titleMusic);
			} else if (event.getCode() == KeyCode.RIGHT) {
				currentBackgroundIndex.getAndIncrement();
				if (currentBackgroundIndex.get() >= backgroundImages.size()) {
					currentBackgroundIndex.set(0);
				}
				backgroundImage.setImage(new Image(backgroundImages.get(currentBackgroundIndex.get())));
			} else if (event.getCode() == KeyCode.LEFT) {
				currentBackgroundIndex.getAndDecrement();
				if (currentBackgroundIndex.get() < 0) {
					currentBackgroundIndex.set(backgroundImages.size() - 1);
				}
				backgroundImage.setImage(new Image(backgroundImages.get(currentBackgroundIndex.get())));
			} else if (event.getCode() == KeyCode.UP) {
				currentCrosshairIndex.getAndIncrement();
				if (currentCrosshairIndex.get() >= crosshairImages.size()) {
					currentCrosshairIndex.set(0);
				}
				crosshairImage.setImage(new Image(crosshairImages.get(currentCrosshairIndex.get())));
			} else if (event.getCode() == KeyCode.DOWN) {
				currentCrosshairIndex.getAndDecrement();
				if (currentCrosshairIndex.get() < 0) {
					currentCrosshairIndex.set(crosshairImages.size() - 1);
				}
				crosshairImage.setImage(new Image(crosshairImages.get(currentCrosshairIndex.get())));
			} else if (event.getCode() == KeyCode.ENTER) {
				titleMusic.stop();
				ImageView selectedBackground = new ImageView(backgroundImages.get(currentBackgroundIndex.get()));
				ImageView selectedCrosshair = new ImageView(crosshairImages.get(currentCrosshairIndex.get()));
				ImageView selectedForeground = new ImageView(foregroundImages.get(currentBackgroundIndex.get()));
				ImageView rBackground = new ImageView(backgroundImages.get(currentBackgroundIndex.get()));
				rBackground.setScaleX(SCALE);
				rBackground.setScaleY(SCALE);
				rBackground.setTranslateX(width);
				ImageView lBackground = new ImageView(backgroundImages.get(currentBackgroundIndex.get()));
				lBackground.setScaleX(SCALE);
				lBackground.setScaleY(SCALE);
				lBackground.setTranslateX(-width);
				MediaPlayer intro = new MediaPlayer(new Media(new File("assets/effects/Intro.mp3").toURI().toString()));
				intro.setVolume(VOLUME);
				intro.setOnEndOfMedia(() -> new Level1(primaryStage, selectedBackground, selectedCrosshair, selectedForeground, rBackground, lBackground, width, height));
				intro.play();
			}
		});
		
		primaryStage.setScene(selectionScene);
		primaryStage.show();
	}
}
