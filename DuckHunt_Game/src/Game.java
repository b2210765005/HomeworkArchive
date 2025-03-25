import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The Game class is an abstract class that provides common functionality and utility methods for the Duck Hunt game.
 * It extends the DuckHunt class.
 */
public abstract class Game extends DuckHunt {
	
	/**
	 * Creates a new Text object with the specified message, size, x-axis translation, and y-axis translation.
	 *
	 * @param message the text message to display
	 * @param size    the font size of the text
	 * @param xAxis   the x-axis translation of the text
	 * @param yAxis   the y-axis translation of the text
	 * @return a new Text object with the specified properties
	 */
	Text text(String message, double size, double xAxis, double yAxis) {
		Text text = new Text(message);
		text.setFont(Font.font("Arial", FontWeight.BOLD, size));
		text.setFill(Color.ORANGE);
		text.setTranslateX(xAxis);
		text.setTranslateY(yAxis);
		return text;
	}
	
	/**
	 * Scrolls the background, foreground, and crosshair images by the specified shift amount.
	 * The scrolling continues until the crosshair reaches the edges of the screen or the background images.
	 *
	 * @param shiftAmount   the amount to shift the images by
	 * @param crosshair     the ImageView representing the crosshair
	 * @param background    the ImageView representing the background image
	 * @param foreground    the ImageView representing the foreground image
	 * @param rBackground   the ImageView representing the right side of the background image
	 * @param lBackground   the ImageView representing the left side of the background image
	 * @param width         the width of the screen
	 */
	void Scrolling(double shiftAmount, ImageView crosshair, ImageView background, ImageView foreground, ImageView rBackground, ImageView lBackground, double width) {
		boolean[] stopTimeline = {false};
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
			if ((crosshair.getTranslateX() > -width / 2 + 30 && crosshair.getTranslateX() < width / 2 - 30) ||
					(lBackground.getTranslateX() >= 0 && shiftAmount == 1 || rBackground.getTranslateX() <= 0 && shiftAmount == -1)) {
				stopTimeline[0] = true;
				return;
			}
			background.setTranslateX(background.getTranslateX() + shiftAmount);
			foreground.setTranslateX(foreground.getTranslateX() + shiftAmount);
			lBackground.setTranslateX(lBackground.getTranslateX() + shiftAmount);
			rBackground.setTranslateX(rBackground.getTranslateX() + shiftAmount);
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		
		AnimationTimer animationTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (stopTimeline[0]) {
					timeline.stop();
					stop();
				}
			}
		};
		
		timeline.setOnFinished(event -> animationTimer.stop());
		
		animationTimer.start();
		timeline.play();
	}
	
	/**
	 * Checks if there is a collision between two ImageView objects.
	 *
	 * @param imageView1 the first ImageView
	 * @param imageView2 the second ImageView
	 * @return true if a collision is detected, false otherwise
	 */
	boolean checkCollision(ImageView imageView1, ImageView imageView2) {
		double x1 = imageView1.getTranslateX();
		double y1 = imageView1.getTranslateY();
		double width1 = imageView1.getImage().getWidth() * SCALE;
		double height1 = imageView1.getImage().getHeight() * SCALE;
		
		double x2 = imageView2.getTranslateX();
		double y2 = imageView2.getTranslateY();
		double width2 = imageView2.getImage().getWidth() * SCALE;
		double height2 = imageView2.getImage().getHeight() * SCALE;
		
		return x1 < x2 + width2 / 2 &&
				x1 + width1 / 2 > x2 &&
				y1 < y2 + height2 / 2 &&
				y1 + height1 / 2 > y2;
	}
}
