import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowBook {

	public static void borrowBook(String[] words) {
		if (Main.currentlyReadBooks.contains(words[1]) || Main.borrowedBooks.contains(words[1])) {
			Writer.write("You cannot borrow this book!");
		} else if (Main.books.get(words[1]).equals("H")) {
			Writer.write("You cannot borrow this book!");
		} else if (Main.members.get(words[2]).equals("S")) {
			String initialBorrowDate = words[3];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate initialDate = LocalDate.parse(initialBorrowDate, formatter);
			List<String> studentBookList = Main.memberBorrowBooks.get(words[2]);
			if (studentBookList.size() < 2) {
				if (!Main.borrowedBooks.contains(words[1])) {
					studentBookList.add(words[1]);
					Main.borrowedBooks.add(words[1]);
					Writer.write("The book [" + words[1] + "] was borrowed by member [" + words[2] + "] at " + words[3]);
					Main.borrowDates.put(words[1], initialDate);
					Main.borrowDatesForWrite.put(words[1], initialDate);
				} else {
					Writer.write("This book is already borrowed by someone else!");
				}
			} else {
				Writer.write("You have exceeded the borrowing limit!");
			}
		} else if (Main.members.get(words[2]).equals("A")) {
			String initialBorrowDate = words[3];
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate initialDate = LocalDate.parse(initialBorrowDate, formatter);
			List<String> facultyBookList = Main.memberBorrowBooks.get(words[2]);
			if (facultyBookList.size() < 4) {
				if (!Main.borrowedBooks.contains(words[1])) {
					facultyBookList.add(words[1]);
					Main.borrowedBooks.add(words[1]);
					Writer.write("The book [" + words[1] + "] was borrowed by member [" + words[2] + "] at " + words[3]);
					Main.borrowDates.put(words[1], initialDate);
					Main.borrowDatesForWrite.put(words[1], initialDate);
				} else {
					Writer.write("This book is already borrowed by someone else!");
				}
			} else {
				Writer.write("You have exceeded the borrowing limit!");
			}
		}
	}
}
