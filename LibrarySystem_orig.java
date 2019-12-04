import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class LibrarySystem {

	private JFrame frame;
	private JTextField memberName;
	private JTextField memberID;
	private JTextField memberEmail;
	private JTextField issueMemberIDField;
	private int memberTypeId;
	private ArrayList<DemoMember> memberList = new ArrayList<>();
	private ArrayList<DemoBook> bookList = new ArrayList<>();
	private ArrayList<DemoPolicy> policyList = new ArrayList<>();
	private ArrayList<DemoInvoice> invoiceList = new ArrayList<>();
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	Date returnDate = null;

	private JTextField returnMemberIDField;
	private JTextField returnBookFineField;
	private JTextField issueDateField;
	private JTextField ReturnPayFineField;
	private JPasswordField memberPassword;
	private JPasswordField issuePasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibrarySystem window = new LibrarySystem();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LibrarySystem() {

		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		populate();
		frame = new JFrame();
		frame.setBounds(0, 0, 1024, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("LlBRARY MANAGEMENT SYSTEM");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(349, 0, 306, 77);
		frame.getContentPane().add(lblNewLabel);

		JLabel Name = new JLabel("Name");
		Name.setBounds(84, 94, 46, 14);
		frame.getContentPane().add(Name);

		JLabel ID = new JLabel("Id");
		ID.setBounds(84, 141, 46, 14);
		frame.getContentPane().add(ID);

		memberName = new JTextField();
		memberName.setBounds(173, 94, 123, 20);
		frame.getContentPane().add(memberName);
		memberName.setColumns(10);

		memberID = new JTextField();
		memberID.setBounds(173, 138, 123, 20);
		frame.getContentPane().add(memberID);
		memberID.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Register");
		lblNewLabel_3.setBounds(147, 51, 64, 14);
		frame.getContentPane().add(lblNewLabel_3);

		JLabel Type = new JLabel("Type");
		Type.setBounds(84, 201, 46, 14);
		frame.getContentPane().add(Type);

		JRadioButton rdbtn_teacher = new JRadioButton("Teacher");
		rdbtn_teacher.setBounds(173, 171, 109, 23);
		frame.getContentPane().add(rdbtn_teacher);

		JRadioButton rdbtn_student = new JRadioButton("Student");
		rdbtn_student.setBounds(173, 197, 109, 23);
		frame.getContentPane().add(rdbtn_student);

		JRadioButton rdbtn_others = new JRadioButton("Others");
		rdbtn_others.setBounds(173, 223, 109, 23);
		frame.getContentPane().add(rdbtn_others);

		JButton btn_register = new JButton("Submit");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validEntry = true;
				boolean validType = true;
				boolean validEmail = true;
				boolean validPassword = true;

				memberID.getText();
				memberName.getText();

				if (!memberEmail.getText().contains("@") || !memberEmail.getText().contains(".com")) {
					validEmail = false;
				}

				if (!(memberPassword.getText().length() > 6)) {
					validPassword = false;
				}

				if ((rdbtn_teacher.isSelected() && !rdbtn_student.isSelected() && !rdbtn_others.isSelected())
						|| (!rdbtn_teacher.isSelected() && rdbtn_student.isSelected() && !rdbtn_others.isSelected())
						|| (!rdbtn_teacher.isSelected() && !rdbtn_student.isSelected() && rdbtn_others.isSelected())) {
					validType = true;
				} else {

					validType = false;
				}

				if (rdbtn_teacher.isSelected()) {
					memberTypeId = 2;
				} else if (rdbtn_student.isSelected()) {
					memberTypeId = 0;
				} else {
					memberTypeId = 1;
				}

				DemoMember member = new DemoMember(memberName.getText(), memberID.getText(), memberTypeId,
						memberPassword.getText(), memberEmail.getText());

				validEntry = member.validateEntry(memberList, member);
				// try catch implement
				if (validEntry && validType && validEmail && validPassword) {
					memberList.add(member);
					JOptionPane.showMessageDialog(null, "successfully activated");
				}

				else if (validEntry && !validType) {
					JOptionPane.showMessageDialog(null, "select one type ");

				} else if (validEntry && !validEmail) {
					memberEmail.setText("");
					JOptionPane.showMessageDialog(null, "enter a valid email ");

				} else if (validEntry && !validPassword) {
					memberPassword.setText("");
					JOptionPane.showMessageDialog(null, "enter a strong password ");

				}

			}
		});
		btn_register.setBounds(122, 360, 89, 23);
		frame.getContentPane().add(btn_register);
        
        JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(84, 275, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
        
		memberEmail = new JTextField();
		memberEmail.setBounds(173, 272, 123, 20);
		frame.getContentPane().add(memberEmail);
		memberEmail.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel ("Password");
		lblNewLabel_1.setBounds(84, 321, 64, 14);
		frame.getContentPane().add(lblNewLabel_1);

		

		JLabel lblBookIssue = new JLabel("Book Issue");
		lblBookIssue.setBounds(455, 70, 94, 63);
		frame.getContentPane().add(lblBookIssue);

		JLabel label = new JLabel("Id");
		label.setBounds(349, 128, 46, 14);
		frame.getContentPane().add(label);

		issueMemberIDField = new JTextField();
		issueMemberIDField.setColumns(10);
		issueMemberIDField.setBounds(465, 125, 86, 20);
		frame.getContentPane().add(issueMemberIDField);

		JLabel label_1 = new JLabel("Password");
		label_1.setBounds(349, 175, 64, 14);
		frame.getContentPane().add(label_1);

		JList<String> list_2 = new JList();
		list_2.setSelectedIndices(new int[] { 0 });
		list_2.setModel(new AbstractListModel() {
			String[] values = new String[] { "Book1", "Book2", "Book3", "Book4" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});

		list_2.setSelectedIndex(0);
		list_2.setBounds(465, 215, 180, 77);
		frame.getContentPane().add(list_2);

		JLabel lblBook = new JLabel("Book");
		lblBook.setBounds(349, 227, 46, 14);
		frame.getContentPane().add(lblBook);

		JButton btnNewIssue = new JButton("Issue");
		btnNewIssue.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<DemoBook> issuedBookList = new ArrayList<>();
				ArrayList<String> issuedbookNameList = (ArrayList<String>) list_2.getSelectedValuesList();
				String password = issuePasswordField.getText();
				String memberId = issueMemberIDField.getText();
				ArrayList<DemoMember> activatedMemberList = memberList;
				DemoLibrarian librarian = new DemoLibrarian();
				DemoInvoice invc = null;
				boolean checkBookAvailability = true;
				String issueDate = issueDateField.getText();

				

				DemoMember member = DemoMember.findMember(activatedMemberList, memberId);

				for (String bookName : issuedbookNameList) {
					DemoBook Dbook = DemoBook.findBook(bookList, bookName);
					issuedBookList.add(Dbook);
				}
				
				checkBookAvailability = DemoBook.checkBookAvailabilityForProcess(issuedBookList, bookList);

				if (member != null && member.verifymember(member, password) && checkBookAvailability) {

					DemoPolicy policy = DemoPolicy.findPolicy(policyList, member.getMemberType());

					if (invoiceList.isEmpty()) {
						for (DemoBook issueBook : issuedBookList) {
							try {
								if (issueDate.contains("/")) {
									invc = new DemoInvoice(member, policy, issueBook, librarian.getName(), issueDate);
								} else {
									invc = new DemoInvoice(member, policy, issueBook, librarian.getName());
								}
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							invoiceList.add(invc);
							bookList = DemoBook.updateBookQuantity(issueBook, bookList);
							JOptionPane.showMessageDialog(null, "book issued " + issueBook.getBookName());
						}

						issuedBookList.clear();
					} else {

						for (DemoBook issueBook : issuedBookList) {
							invc = DemoInvoice.checkForInvoice(invoiceList, issueBook, member);
							if (invc == null) {
								try {
									if (issueDate.contains("/")) {
										invc = new DemoInvoice(member, policy, issueBook, librarian.getName(),
												issueDate);
									} else {
										invc = new DemoInvoice(member, policy, issueBook, librarian.getName());
									}
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								invoiceList.add(invc);
								JOptionPane.showMessageDialog(null, "book issued " + issueBook.getBookName());
								bookList = DemoBook.updateBookQuantity(issueBook, bookList);

							} else {
								JOptionPane.showMessageDialog(null,
										"Book Is Already Issued " + issueBook.getBookName());
							}
						}
						issuedBookList.clear();
					}
				} else if (member == null) {
					issuedBookList.clear();
					JOptionPane.showMessageDialog(null, "Username Does Not Exist");
				} else if (member != null && (!member.verifymember(member, password))) {
					issuedBookList.clear();
					issuePasswordField.setText("");
					JOptionPane.showMessageDialog(null, "Password Is Incorrect");
				} else if (!checkBookAvailability) {
					issuedBookList.clear();
					JOptionPane.showMessageDialog(null, "Book Is Not Available");
				}

			}
		});
		btnNewIssue.setBounds(441, 360, 89, 23);
		frame.getContentPane().add(btnNewIssue);

		JLabel lblBookReturn = new JLabel("Book Return");
		lblBookReturn.setBounds(787, 33, 74, 14);
		frame.getContentPane().add(lblBookReturn);

		JLabel lblBook_1 = new JLabel("Book");
		lblBook_1.setBounds(710, 97, 46, 14);
		frame.getContentPane().add(lblBook_1);

		JLabel lblMemberId = new JLabel("Member ID");
		lblMemberId.setBounds(710, 171, 61, 14);
		frame.getContentPane().add(lblMemberId);

		JLabel lblFine = new JLabel("Fine");
		lblFine.setBounds(710, 201, 46, 14);
		frame.getContentPane().add(lblFine);

		JButton btnPayFine = new JButton("Pay Fine");
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DemoMember returnMember = DemoMember.findMember(memberList, returnMemberIDField.getText());
				Boolean fineValueCheck = Integer.parseInt(ReturnPayFineField.getText()) >= Integer
						.parseInt(returnBookFineField.getText());

				if (returnMember != null) {
					if (invoiceList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No Fine To Pay");
					} else if (!invoiceList.isEmpty() && fineValueCheck) {
						DemoPolicy policy = DemoPolicy.findPolicy(policyList, returnMember.getMemberType());
						for (DemoInvoice invc : invoiceList) {
							boolean invoicesDueForFine = (invc.dateDifferenceCalculate(invc.getIssueDate(),
									returnDate) > policy.getDaysToBorrow());
							if (invc.getMember().equals(returnMember) && invoicesDueForFine) {
								invc.setIssueDate(returnDate);
							}
						}
						ReturnPayFineField.setText("");
						returnBookFineField.setText("");
						JOptionPane.showMessageDialog(null, "Fine Paid");
					} else {
						JOptionPane.showMessageDialog(null, "Please Pay The Complete Fine");
					}
				} else {
					JOptionPane.showMessageDialog(null, "You are not registered member");
				}

			}
		});
		btnPayFine.setBounds(779, 360, 109, 23);
		frame.getContentPane().add(btnPayFine);

		returnMemberIDField = new JTextField();
		returnMemberIDField.setBounds(840, 163, 86, 20);
		frame.getContentPane().add(returnMemberIDField);
		returnMemberIDField.setColumns(10);

		returnBookFineField = new JTextField();
		returnBookFineField.setBounds(840, 200, 86, 20);
		frame.getContentPane().add(returnBookFineField);
		returnBookFineField.setColumns(10);

		JList<String> list_returnedBooks = new JList();
		list_returnedBooks.setSelectedIndices(new int[] { 0 });
		list_returnedBooks.setModel(new AbstractListModel() {
			String[] values = new String[] { "Book1", "Book2", "Book3", "Book4" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list_returnedBooks.setSelectedIndex(0);
		list_returnedBooks.setBounds(840, 68, 128, 71);
		frame.getContentPane().add(list_returnedBooks);

		JButton btnReturnBook = new JButton("Return Book");
		btnReturnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				ArrayList<String> returnedBookNameList = (ArrayList<String>) list_returnedBooks.getSelectedValuesList();
				DemoInvoice invoice = null;
				ArrayList<DemoBook> returnBookList = new ArrayList<>();

				try {
					returnDate = formatter.parse(formatter.format(new Date()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DemoMember returnMember = DemoMember.findMember(memberList, returnMemberIDField.getText());

				for (String bookName : returnedBookNameList) {
					DemoBook Dbook = DemoBook.findBook(bookList, bookName);
					returnBookList.add(Dbook);
				}

				if (returnMember != null) {
					if (invoiceList.isEmpty()) {
						JOptionPane.showMessageDialog(null, "No Book Is Issued For The Return");
						returnBookList.clear();
					} else {
						for (DemoBook book : returnBookList) {
							invoice = DemoInvoice.checkForInvoice(invoiceList, book, returnMember);

							DemoPolicy policy = DemoPolicy.findPolicy(policyList, returnMember.getMemberType());
							if (invoice != null) {
								invoice.setReturnDate(returnDate);
								long fine = invoice.calculateFine(policy, invoiceList, returnMember, returnDate);
								if (fine == 0) {

									invoiceList.remove(invoice);
									JOptionPane.showMessageDialog(null, "Book Returned Successfully " + book.getBookName());
									bookList = DemoBook.updateBookQuantityForReturn(book, bookList);

								} else {
									returnBookFineField.setText(String.valueOf(fine));
									JOptionPane.showMessageDialog(null, "Please Pay Your Pending Fine");
								}
							} else {
								JOptionPane.showMessageDialog(null,
										"You Cannot Return The Book That You Have Not Issued " + book.getBookName());
							}
						}
						returnBookList.clear();
					}
				} else {
					returnBookList.clear();
					JOptionPane.showMessageDialog(null, "You are not registered member");
				}
			}

		});
		btnReturnBook.setBounds(779, 245, 109, 23);
		frame.getContentPane().add(btnReturnBook);

		issueDateField = new JTextField();
		issueDateField.setBounds(491, 303, 86, 20);
		frame.getContentPane().add(issueDateField);
		issueDateField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Issue Date\r\n(MM/dd/yyyy)");
		lblNewLabel_4.setBounds(349, 291, 138, 47);
		frame.getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Pay Fine");
		lblNewLabel_5.setBounds(710, 318, 61, 14);
		frame.getContentPane().add(lblNewLabel_5);

		ReturnPayFineField = new JTextField();
		ReturnPayFineField.setBounds(840, 318, 86, 20);
		frame.getContentPane().add(ReturnPayFineField);
		ReturnPayFineField.setColumns(10);
		
		memberPassword = new JPasswordField();
		memberPassword.setBounds(173, 318, 123, 20);
		frame.getContentPane().add(memberPassword);
		
		issuePasswordField = new JPasswordField();
		issuePasswordField.setBounds(465, 172, 84, 20);
		frame.getContentPane().add(issuePasswordField);

	}

	private void populate() {
		DemoBook book1 = new DemoBook("Book1", 2, "Cj", 123);
		DemoBook book2 = new DemoBook("Book2", 2, "Kd", 124);
		DemoBook book3 = new DemoBook("Book3", 2, "Cr", 125);
		DemoBook book4 = new DemoBook("Book4", 2, "DD", 124);
		bookList.add(book1);
		bookList.add(book2);
		bookList.add(book3);
		bookList.add(book4);

		DemoPolicy policy1 = new DemoPolicy(0, 7, 5);
		DemoPolicy policy2 = new DemoPolicy(2, 5, 30);
		DemoPolicy policy3 = new DemoPolicy(1, 7, 5);
		policyList.add(policy1);
		policyList.add(policy2);
		policyList.add(policy3);

	}
}
