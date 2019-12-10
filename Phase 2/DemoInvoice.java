import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DemoInvoice {

	private int id;
	private DemoMember member;
	private DemoPolicy policy;
	private DemoBook issuedBook;
	private Date issueDate;
	private Date returnDate;
	private String lib;
	private static int number;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

	public static int sequence() {
		number = number + 1;
		return number;
	}

	public DemoMember getMember() {
		return member;
	}

	public void setMember(DemoMember member) {
		this.member = member;
	}

	public DemoPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(DemoPolicy policy) {
		this.policy = policy;
	}

	public DemoBook getBook() {
		return issuedBook;
	}

	public void setBook(DemoBook issuedBook) {
		this.issuedBook = issuedBook;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public DemoInvoice(DemoMember member, DemoPolicy policy, DemoBook issuedBook, String lib) throws ParseException {
		super();
		this.id = sequence();
		this.member = member;
		this.policy = policy;
		this.issuedBook = issuedBook;
		this.issueDate = formatter.parse(formatter.format(new Date()));
		this.lib = lib;

	}

	public DemoInvoice(DemoMember member, DemoPolicy policy, DemoBook issuedBook, String lib, String issueDate)
			throws ParseException {
		super();
		this.id = sequence();
		this.member = member;
		this.policy = policy;
		this.issuedBook = issuedBook;
		this.issueDate = formatter.parse(issueDate);
		this.lib = lib;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		String invoice = "\ninvoice id " + id + "\nMember name " + member.getMemberName() + "\nMember type "
				+ member.getMemberType() + "\nBook name " + "\nPolicy " + policy.getMemberType() + "\nIssued Date "
				+ formatter.format(issueDate) + "\nLibrarian " + lib;
		return invoice;

	}

	public static ArrayList<DemoInvoice> findMembersRecord(ArrayList<DemoInvoice> invoiceList, String memberId) {

		ArrayList<DemoInvoice> reqInvoices = new ArrayList<DemoInvoice>();

		for (DemoInvoice invoice : invoiceList) {
			if (invoice != null && invoice.getMember().getMemberId().equalsIgnoreCase(memberId)) {
				reqInvoices.add(invoice);

			}
		}
		return reqInvoices;
	}

	public static DemoInvoice checkForInvoice(ArrayList<DemoInvoice> invoiceList, DemoBook issuedBook,
			DemoMember member2) {

		ArrayList<DemoInvoice> reqInvoices = new ArrayList<DemoInvoice>();
		reqInvoices = findMembersRecord(invoiceList, member2.getMemberId());
		if (reqInvoices.isEmpty()) {
			return null;
		} else {
			for (DemoInvoice invoice : reqInvoices) {

				if (invoice.getBook().equals(issuedBook)) {
					return invoice;
				}

			}
			return null;
		}

	}

	public long calculateFine(DemoPolicy policy2, ArrayList<DemoInvoice> invoiceList, DemoMember returnMember,
			Date returnDate) {

		ArrayList<DemoInvoice> reqInvoices = new ArrayList<DemoInvoice>();
		reqInvoices = findMembersRecord(invoiceList, returnMember.getMemberId());
		long daysToBorrow = policy2.getDaysToBorrow();
		long dateDifference;
		long fine = 0;

		if (reqInvoices.isEmpty()) {
			return 0;
		} else {
			for (DemoInvoice invoice : reqInvoices) {
				dateDifference = dateDifferenceCalculate(invoice.getIssueDate(), returnDate);
				if (dateDifference > daysToBorrow) {
					fine = fine + (dateDifference - daysToBorrow) * policy2.getFinePerDay();
				}

			}
			return fine;
		}

	}

	public long dateDifferenceCalculate(Date d1, Date d2) {

		long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return diff;

	}

}
