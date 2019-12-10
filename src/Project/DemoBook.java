package Project;
import java.util.ArrayList;

public class DemoBook {
	private int bookId;
	private String bookName;
	private int bookQunatity;
	private String bookAuthor;

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getBookQunatity() {
		return bookQunatity;
	}

	public void setBookQunatity(int bookQunatity) {
		this.bookQunatity = bookQunatity;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public DemoBook(String bookName, int bookQunatity, String bookAuthor, int bookid) {
		super();
		this.bookName = bookName;
		this.bookQunatity = bookQunatity;
		this.bookAuthor = bookAuthor;
		this.bookId = bookid;
	}

	public static DemoBook findBook(ArrayList<DemoBook> bookList, String bookName) {

		for (DemoBook book : bookList) {
			if (book.getBookName().equals(bookName)) {
				return book;
			}
		}
		return null;
	}

	public static boolean checkBookAvailabilityForProcess(ArrayList<DemoBook> issuedbookList,
			ArrayList<DemoBook> bookList) {
		boolean check = true;

		for (DemoBook book : issuedbookList) {

			for (DemoBook globalbook : bookList) {
				if (globalbook.getBookName().equals(book.getBookName()))

				{
					if (globalbook.getBookQunatity() <= 0) {
						check = false;
					}

				}

			}

		}
		return check;
	}

	public static ArrayList<DemoBook> updateBookQuantity(DemoBook issuedBook, ArrayList<DemoBook> bookList) {

		for (DemoBook globalbook : bookList) {
			if (globalbook.getBookName().equals(issuedBook.getBookName()))

			{
				globalbook.setBookQunatity(globalbook.getBookQunatity() - 1);
			}

		}

		return bookList;
	}

	public static ArrayList<DemoBook> updateBookQuantityForReturn(DemoBook returnBook, ArrayList<DemoBook> bookList) {

		for (DemoBook globalbook : bookList) {
			if (globalbook.getBookName().equals(returnBook.getBookName()))

			{
				globalbook.setBookQunatity(globalbook.getBookQunatity() + 1);

			}

		}

		return bookList;
	}

}
