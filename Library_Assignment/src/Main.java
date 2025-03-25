import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static Map<String, String> books = new HashMap<>();
	public static Map<String, String> members = new HashMap<>();
	public static Map<String, List<String>> memberBorrowBooks = new HashMap<>();
	public static Map<String, List<String>> memberReadBooks = new HashMap<>();
	public static List<String> borrowedBooks = new ArrayList<>();
	public static List<String> currentlyReadBooks = new ArrayList<>();
	public static HashMap<String, LocalDate> borrowDates = new HashMap<>();
	public static HashMap<String, LocalDate> borrowDatesForWrite = new HashMap<>();
	public static List<String> extendBooks = new ArrayList<>();
	static String output;

	public static void main(String[] args) {
		output = args[1];
		String filePath = args[0];
		Writer.write();
		Reader.readFile(filePath);
	}
}