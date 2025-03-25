public class AddBook {
	public static void addBook(String[] words) {
		int id = Main.books.size() + 1;
		String format = words[1];
		Main.books.put(String.valueOf(id), format);
		if (format.equals("P")) {
			Writer.write("Created new book: Printed [id: " + id + "]");
		} else if (format.equals("H")) {
			Writer.write("Created new book: Handwritten [id: " + id + "]");
		}
	}
}
