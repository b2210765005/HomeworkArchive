import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReadInLibrary {
	public static void readInLibrary(String[] words) {
		String initialBorrowDate = words[3];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate initialDate = LocalDate.parse(initialBorrowDate, formatter);
		if (Main.borrowedBooks.contains(words[1]) || Main.currentlyReadBooks.contains(words[1])) {
			Writer.write("You can not read this book!");
		} else if (Main.members.get(words[2]).equals("S")) {
			if (Main.books.get(words[1]).equals("H")) {
				Writer.write("Students can not read handwritten books!");
			} else {
				Writer.write("The book [" + words[1] + "] was read in library by member [" + words[2] + "] at " + words[3]);
				List<String> BookList = Main.memberReadBooks.get(words[2]);
				BookList.add(words[1]);
				Main.currentlyReadBooks.add(words[1]);
				Main.borrowDates.put(words[1], initialDate);
			}
		} else if (Main.members.get(words[2]).equals("A")) {
			Writer.write("The book [" + words[1] + "] was read in library by member [" + words[2] + "] at " + words[3]);
			List<String> BookList = Main.memberReadBooks.get(words[2]);
			BookList.add(words[1]);
			Main.currentlyReadBooks.add(words[1]);
			Main.borrowDates.put(words[1], initialDate);
		}
	}
}
