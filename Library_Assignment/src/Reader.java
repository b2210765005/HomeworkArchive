import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	public static void readFile(String filePath) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String line = reader.readLine();
			while (line != null) {
				String[] words = line.split("\t");
				switch (words[0]) {
					case "addBook":
						AddBook.addBook(words);
						break;
					case "addMember":
						AddMember.addMember(words);
						break;
					case "borrowBook":
						BorrowBook.borrowBook(words);
						break;
					case "readInLibrary":
						ReadInLibrary.readInLibrary(words);
						break;
					case "returnBook":
						ReturnBook.returnBook(words);
						break;
					case "extendBook":
						ExtendBook.extendBook(words);
						break;
					case "getTheHistory":
						GetHistory.getHistory();
						break;
					default:
						Writer.write("Invalid command.");
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
