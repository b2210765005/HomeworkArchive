import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReturnBook {
	public static void returnBook(String[] words) {
		if (Main.currentlyReadBooks.contains(words[1])) {
			Main.currentlyReadBooks.remove(words[1]);
			List<String> BookList = Main.memberReadBooks.get(words[2]);
			BookList.remove(words[1]);
			Writer.write("The book [" + words[1] + "] was returned by member [" + words[2] + "] at " + words[3] + " Fee: 0");
		} else if (Main.borrowedBooks.contains(words[1])) {
			Main.extendBooks.remove(words[1]);
			Main.borrowedBooks.remove(words[1]);
			List<String> BookList = Main.memberBorrowBooks.get(words[2]);
			BookList.remove(words[1]);
			LocalDate borrowDate = Main.borrowDates.get(words[1]);
			String initialBorrowDate = words[3];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate returnDate = LocalDate.parse(initialBorrowDate, formatter);
			long fee = ChronoUnit.DAYS.between(borrowDate, returnDate);
			switch (Main.members.get(words[2])) {
				case "A":
					if (fee > 14) {
						fee = fee - 14;
					} else {
						fee = 0;
					}
					Writer.write("The book [" + words[1] + "] was returned by member [" + words[2] + "] at " + words[3] + " Fee: " + fee);
					break;
				case "S":
					if (fee > 7) {
						fee = fee - 7;
					} else {
						fee = 0;
					}
					Writer.write("The book [" + words[1] + "] was returned by member [" + words[2] + "] at " + words[3] + " Fee: " + fee);
					break;
			}
		}
	}
}
