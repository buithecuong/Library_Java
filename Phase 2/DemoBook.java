
import java.util.ArrayList;
import java.util.List;

public class DemoBook {
	private int bookId;
	private String bookName;
	private int bookQuantity;
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

	public int getBookQuantity() {
		return bookQuantity;
	}

	public void setBookQuantity(int bookQunatity) {
		this.bookQuantity = bookQunatity;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	
	

	public DemoBook(String bookName, int bookQuantity, String bookAuthor, int bookid) {
		super();
		this.bookName = bookName;
		this.bookQuantity = bookQuantity;
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
	
	public static ArrayList<String> getBookNameArrList(ArrayList<DemoBook> demoBookList) {
		ArrayList<String> bookNameArrList = new ArrayList<String>();
		for (DemoBook book : demoBookList) {
			bookNameArrList.add(book.getBookName());
		}
		
		return bookNameArrList; 
	}
	
	
	public static ArrayList<DemoBook> updateBookList(ArrayList<DemoBook> bookList, String[] bookNames, boolean is_remove) {

		for (String name : bookNames) {
			for (DemoBook book : bookList) {
				if (book.getBookName().equals(name)) {
					if (is_remove)
						bookList.remove(bookList.indexOf(book));
					else
						bookList.add(book);						
				}
			}
		}
		return bookList;
	}

	public static boolean checkBookAvailabilityForProcess(ArrayList<DemoBook> issuedbookList,
			ArrayList<DemoBook> bookList) {
		boolean check = true;

		for (DemoBook book : issuedbookList) {

			for (DemoBook globalbook : bookList) {
				if (globalbook.getBookName().equals(book.getBookName()))

				{
					if (globalbook.getBookQuantity() <= 0) {
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
				globalbook.setBookQuantity(globalbook.getBookQuantity() - 1);
			}

		}

		return bookList;
	}

	public static ArrayList<DemoBook> updateBookQuantityForReturn(DemoBook returnBook, ArrayList<DemoBook> bookList) {

		for (DemoBook globalbook : bookList) {
			if (globalbook.getBookName().equals(returnBook.getBookName()))

			{
				globalbook.setBookQuantity(globalbook.getBookQuantity() + 1);

			}

		}

		return bookList;
	}

}
