import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	public static void write(String text) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Main.output, true))) {
			writer.write(text);
			writer.newLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void write() {
		try {
			new BufferedWriter(new FileWriter(Main.output, false));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
