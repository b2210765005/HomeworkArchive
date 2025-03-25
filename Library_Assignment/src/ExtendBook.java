import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class ExtendBook {
	public static void extendBook(String[] words) {
		if (!Main.extendBooks.contains(words[1])) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String extendDateW = words[3];
			LocalDate extendDate = LocalDate.parse(extendDateW, formatter);
			LocalDate borrowDate = Main.borrowDates.get(words[1]);
			long time = ChronoUnit.DAYS.between(borrowDate, extendDate);
			Main.extendBooks.add(words[1]);
			switch (Main.members.get(words[2])) {
				case "A":
					if (time > 14) {
						Writer.write("You cannot extend the deadline!");
					} else {
						Writer.write("The deadline of book [" + words[1] + "] was extended by member [" + words[2] + "] at " + words[3]);
						Writer.write("New deadline of book [" + words[1] + "] is " + borrowDate.plusDays(28));
						Main.borrowDates.put(words[1], Main.borrowDates.get(words[1]).plusDays(14));
					}
					break;
				case "S":
					if (time > 7) {
						Writer.write("You cannot extend the deadline!");
					} else {
						Writer.write("The deadline of book [" + words[1] + "] was extended by member [" + words[2] + "] at " + words[3]);
						Writer.write("New deadline of book [" + words[1] + "] is " + borrowDate.plusDays(14));
						Main.borrowDates.put(words[1], Main.borrowDates.get(words[1]).plusDays(7));
					}
					break;
			}
		} else {
			Writer.write("You cannot extend the deadline!");
		}
	}
}
