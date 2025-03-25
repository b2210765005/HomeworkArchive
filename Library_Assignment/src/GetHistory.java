public class GetHistory {
	public static void getHistory() {
		Writer.write("History of library:\n");
		int sNumber = 0;
		int aNumber = 0;
		for (String i : Main.members.values()) {
			if (i.equals("S")) {
				sNumber++;
			}
			if (i.equals("A")) {
				aNumber++;
			}
		}
		Writer.write("Number of students: " + sNumber);
		for (String i : Main.members.keySet()) {
			if (Main.members.get(i).equals("S")) {
				Writer.write("Student [id: " + i + "]");
			}
		}
		Writer.write("\nNumber of academics: " + aNumber);
		for (String i : Main.members.keySet()) {
			if (Main.members.get(i).equals("A")) {
				Writer.write("Academic [id: " + i + "]");
			}
		}
		int pNumber = 0;
		int hNumber = 0;
		for (String i : Main.books.values()) {
			if (i.equals("P")) {
				pNumber++;
			}
			if (i.equals("H")) {
				hNumber++;
			}
		}
		Writer.write("\nNumber of printed books: " + pNumber);
		for (String i : Main.books.keySet()) {
			if (Main.books.get(i).equals("P")) {
				Writer.write("Printed [id: " + i + "]");
			}
		}
		Writer.write("\nNumber of handwritten books: " + hNumber);
		for (String i : Main.books.keySet()) {
			if (Main.books.get(i).equals("H")) {
				Writer.write("Handwritten [id: " + i + "]");
			}
		}
		Writer.write("\nNumber of borrowed books: " + Main.borrowedBooks.size());
		for (String i : Main.borrowedBooks) {
			for (String id : Main.memberBorrowBooks.keySet()) {
				if (Main.memberBorrowBooks.get(id).contains(i)) {
					Writer.write("The book [" + i + "] was borrowed by member [" + id + "] at " + Main.borrowDatesForWrite.get(i));

				}
			}
		}
		Writer.write("\nNumber of books read in library: " + Main.currentlyReadBooks.size());
		for (String i : Main.currentlyReadBooks) {
			for (String id : Main.memberReadBooks.keySet()) {
				if (Main.memberReadBooks.get(id).contains(i)) {
					Writer.write("The book [" + i + "] was read in library by member [" + id + "] at " + Main.borrowDates.get(i));

				}
			}
		}
	}
}
