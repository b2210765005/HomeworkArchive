import java.util.ArrayList;
import java.util.List;

public class AddMember {
	public static void addMember(String[] words) {
		int id = Main.members.size() + 1;
		String format = words[1];
		Main.members.put(String.valueOf(id), format);
		List<String> MemberBookList1 = new ArrayList<>();
		List<String> MemberBookList2 = new ArrayList<>();
		Main.memberBorrowBooks.put(String.valueOf(id), MemberBookList1);
		Main.memberReadBooks.put(String.valueOf(id), MemberBookList2);
		if (format.equals("S")) {
			Writer.write("Created new member: Student [id: " + id + "]");
		}
		if (format.equals("A")) {
			Writer.write("Created new member: Academic [id: " + id + "]");
		}
	}
}
