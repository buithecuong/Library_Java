import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class LibrarySystem {

	private JFrame frame, i_frame, r_frame,  return_frame;
	
	// a label 
    private JLabel lbl; 
	// menubar 
    private JMenuBar mb; 
  
    // JMenu 
    private JMenu main, admin, student, librarian, sub_student, help; 
  
    // Menu items 
    static JMenuItem inv_menu, reg_menu, issue_men, return_men; 
	private JTextField memberName;
	private JTextField memberID;
	private JTextField txtFieldRegEmail;
	private JTextField issueMemberIDField;
	private int memberTypeId;
	private ArrayList<DemoMember> memberList = new ArrayList<>();
	private ArrayList<DemoBook> bookList = new ArrayList<>();
	private ArrayList<DemoPolicy> policyList = new ArrayList<>();
	private ArrayList<DemoInvoice> invoiceList = new ArrayList<>();
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	Date returnDate = null;
	public static boolean status= false;

	private JTextField returnMemberIDField;
	private JTextField returnBookFineField;
	private JTextField issueDateField;
	private JTextField ReturnPayFineField;
	private JPasswordField regPwdField;
	private JPasswordField issuePasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemoMember admin = new DemoMember("admin", "Asdf$1234", 0,   "1234", "admin.library@cestarcollege.com");
					DemoMember.updateMember2DB(admin);
			        login();	
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public LibrarySystem(DemoMember user) {

		main_menu( user);
		

	}
	
	public static void login() {
        
		JFrame f=new JFrame("Cestar Library Authentication");//cr eating instance of JFrame
        JLabel l1,l2;  
        l1=new JLabel("Username");  //Create label Username
        l1.setBounds(30,15, 100,30); //x axis, y axis, width, height 
         
        l2=new JLabel("Password");  //Create label Password
        l2.setBounds(30,50, 100,30);    
         
        JTextField F_user = new JTextField(); //Create text field for username
        F_user.setBounds(110, 15, 200, 30);
             
        JPasswordField F_pass=new JPasswordField(); //Create text field for password
        F_pass.setBounds(110, 50, 200, 30);
           
        JButton login_but=new JButton("Login");//creating instance of JButton for Login Button
        login_but.setBounds(130,90,80,25);//Dimensions for button
        login_but.addActionListener(new ActionListener() {  //Perform action
             
            public void actionPerformed(ActionEvent e){ 
     
            String userID = F_user.getText(); //Store username entered by the user in the variable "username"
            String password = F_pass.getText(); //Store password entered by the user in the variable "password"
             
            if(userID.equals("")) //If username is null
            {
                JOptionPane.showMessageDialog(null,"Please enter username"); //Display dialog box with the message
            } 
            else if(password.equals("")) //If password is null
            {
                JOptionPane.showMessageDialog(null,"Please enter password"); //Display dialog box with the message
            }
            else { //If both the fields are present then to login the user, check wether the user exists already
                //System.out.println("Login connect");
                try
                {
                	DemoMember user = DemoMember.findMemberFromDB(userID);
                	if(!user.getMemberId().equals(userID) || ! user.getMemberPassword().equals(password)) { //Move pointer below
                      JOptionPane.showMessageDialog(null,"Wrong Username/Password!"); //Display Message
     
                     }
                     else 
                     {
                    	 status = true;
                    	 f.dispose();
                    	 LibrarySystem window = new LibrarySystem(user);
 						window.frame.setVisible(true);
                     }
                	       
                	       //admin_menu(); //redirect to admin menu
                          //user_menu(UID); //redirect to user menu for that user ID
                     
                  }
                catch (Exception ex) {
                     ex.printStackTrace();
            }
            }
        }               
        });
        
     
         
        f.add(F_pass); //add password
        f.add(login_but);//adding button in JFrame  
        f.add(F_user);  //add user
        f.add(l1);  // add label1 i.e. for username
        f.add(l2); // add label2 i.e. for password
         
        f.setSize(400,180);//400 width and 500 height  
        f.setLayout(null);//using no layout managers  
        f.setVisible(true);//making the frame visible 
        f.setLocationRelativeTo(null);
         
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void main_menu(DemoMember user) {
		
		int user_role = user.getMemberType();
		populate();
		frame = new JFrame("Cestar LMS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lbl = new JLabel("Library Management System"); 
		mb = new JMenuBar(); 
	
	    main = new JMenu("Main"); 
	    admin = new JMenu("Admin");
	    help = new JMenu("Help");
	    student = new JMenu("Student"); 
	
	    // create menuitems  
	    inv_menu = new JMenuItem("Inventory"); 
	    reg_menu = new JMenuItem("Registration");
	    issue_men = new JMenuItem("Issue Book"); 
	    return_men = new JMenuItem("Return");
	
	 // add ActionListener to menuItems 
	    inv_menu.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            JOptionPane.showMessageDialog(null,"Inventory menu!"); //Open a dialog box and display the message
	             
	        }
	    });
	    reg_menu.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            JOptionPane.showMessageDialog(null,"Registration menu!"); //Open a dialog box and display the message
	            register();
	             
	        }
	    });
	    issue_men.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            issue_book();
	             
	        }
	    });
	    return_men.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            JOptionPane.showMessageDialog(null,"Return book menu!"); //Open a dialog box and display the message
	            return_book();
	             
	        }
	    });
	    admin.add(inv_menu);
	    admin.add(reg_menu);
	    student.add(issue_men);
	
	    student.add(return_men);
	
	    // add menu to menu bar 
	    mb.add(main); 
	
	    mb.add(admin); 
	
	    mb.add(help); 
	
	    mb.add(student); 
	
	    // add menubar to frame 
	    frame.setJMenuBar(mb); 
	    
	    frame.setSize(500, 500); 
	
		frame.setVisible(true);
		frame.setBounds(0, 0, 1024, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		JLabel lblNewLabel = new JLabel("Welome " + username.toUpperCase() + " TO LIBRARY!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(349, 0, 306, 77);
		frame.getContentPane().add(lblNewLabel);
	
		
	
	}
	
	private void register() {
	    r_frame = new JFrame("Register member");
		r_frame.setBounds(0, 0, 512, 720);
	    r_frame.setVisible(true);
	    r_frame.setSize(340, 450); 
		r_frame.getContentPane().setLayout(null);
		
	    JLabel Name = new JLabel("Name");
	    Name.setBounds(30, 94, 46, 14);
	    r_frame.getContentPane().add(Name);
	
	    JLabel ID = new JLabel("Id");
	    ID.setBounds(30, 141, 46, 14);
	    r_frame.getContentPane().add(ID);
	
	    memberName = new JTextField();
	    memberName.setBounds(90, 94, 123, 20);
	    r_frame.getContentPane().add(memberName);
	    memberName.setColumns(10);
	
	    memberID = new JTextField();
	    memberID.setBounds(90, 138, 123, 20);
	    r_frame.getContentPane().add(memberID);
	    memberID.setColumns(10);
	
	    JLabel lblNewLabel_3 = new JLabel("Register");
	    lblNewLabel_3.setBounds(147, 51, 64, 14);
	    r_frame.getContentPane().add(lblNewLabel_3);
	
	    JLabel Type = new JLabel("Role");
	    Type.setBounds(30, 201, 46, 14);
	    r_frame.getContentPane().add(Type);
	
	    String[] user_roles = { "Admin", "Librarian", "Instructor", "Student", "Others" };
	
	    
	    final JComboBox<String> roleList = new JComboBox<String>(user_roles);
	    roleList.setBounds(90, 201, 123, 20);
	
	    roleList.setVisible(true);
	    r_frame.getContentPane().add(roleList);
	
	    JButton btn_register = new JButton("Submit");
	    btn_register.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	            boolean validEntry = true;
	            boolean validType = true;
	            boolean validEmail = true;
	            boolean validPassword = true;
	
	            memberID.getText();
	            memberName.getText();
	
	            if (!txtFieldRegEmail.getText().contains("@") || !txtFieldRegEmail.getText().contains(".")) {
	                validEmail = false;
	            }
	
	            if (!(regPwdField.getText().length() > 6)) {
	                validPassword = false;
	            }
	            
	            String select_role = (String) roleList.getSelectedItem();
	            System.out.println(select_role);
	            switch(select_role) 
	            { 
	                case "Admin": 
	                	memberTypeId = 0;
	                    break; 
	                case "Librarian": 
	                	memberTypeId = 1; 
	                    break; 
	                case "Instructor": 
	                	memberTypeId = 2; 
	                    break; 
	                case "Student": 
	                	memberTypeId = 3; 
	                    break; 
	                default: 
	                	memberTypeId = 4; 
	            } 
	            
	            DemoMember member = new DemoMember("ABC", memberID.getText(), memberTypeId,
	            regPwdField.getText(), "ABC");
	
	            
	
	            validEntry = member.validateEntry(memberList, member);
	            // try catch implement
	            if (validEntry && validType && validEmail && validPassword) {
	            	if(!DemoMember.validateExistingUser(memberID.getText()))
					{
						memberList.add(member);
						DemoMember.updateMember2DB(member);
						JOptionPane.showMessageDialog(null, "successfully activated");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "User" + memberID.getText() + "already existed in DB!");
					}
	            } else if (validEntry && !validEmail) {
	                //txtFieldRegEmail.setText("");
	                JOptionPane.showMessageDialog(null, "enter a valid email ");
	
	            } else if (validEntry && !validPassword) {
	                regPwdField.setText("");
	                JOptionPane.showMessageDialog(null, "enter a strong password ");
	
	            }
	
	        }
	    });
	    btn_register.setBounds(122, 360, 89, 23);
	    r_frame.getContentPane().add(btn_register);
	
	    
	
	    JLabel lblEmail = new JLabel("Email");
	    lblEmail.setBounds(30, 275, 46, 14);
	    r_frame.getContentPane().add(lblEmail);
	    
	    
	    txtFieldRegEmail = new JTextField();
	    txtFieldRegEmail.setBounds(90, 272, 200, 20);
	    r_frame.getContentPane().add(txtFieldRegEmail);
	    txtFieldRegEmail.setColumns(10);
	
	    JLabel lblRegPwd = new JLabel ("Password");
	    lblRegPwd.setBounds(30, 321, 64, 14);
	    r_frame.getContentPane().add(lblRegPwd);
	    
	    regPwdField = new JPasswordField();
		regPwdField.setBounds(90, 318, 124, 20);
		r_frame.getContentPane().add(regPwdField);
	}
	private void issue_book() {
		
		//JOptionPane.showMessageDialog(null,"Issue book menu!"); //Open a dialog box and display the message
	    i_frame = new JFrame("Issue Book");
	    i_frame.setVisible(true);
		i_frame.setBounds(0, 0, 1024, 720);
		i_frame.setSize(400, 400); 
		i_frame.getContentPane().setLayout(null);
		
		JLabel lblBookIssue = new JLabel("Welcome to Library Book Issue!");
		lblBookIssue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblBookIssue.setBounds(10, 30, 400, 63);
		i_frame.getContentPane().add(lblBookIssue);
	
		JLabel label = new JLabel("User ID");
		label.setBounds(10, 128, 46, 14);
		i_frame.getContentPane().add(label);
	
		issueMemberIDField = new JTextField();
		issueMemberIDField.setColumns(10);
		issueMemberIDField.setBounds(90, 125, 86, 20);
		i_frame.getContentPane().add(issueMemberIDField);
	
		JLabel label_1 = new JLabel("Password");
		label_1.setBounds(10, 175, 64, 14);
		i_frame.getContentPane().add(label_1);
	    
	    issuePasswordField = new JPasswordField();
		issuePasswordField.setBounds(90, 172, 84, 20);
		i_frame.getContentPane().add(issuePasswordField);
	
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
		list_2.setBounds(90, 215, 180, 77);
		i_frame.getContentPane().add(list_2);
	
		JLabel lblBook = new JLabel("Book");
		lblBook.setBounds(10, 227, 46, 14);
		i_frame.getContentPane().add(lblBook);
	
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
		btnNewIssue.setBounds(90, 360, 89, 23);
		i_frame.getContentPane().add(btnNewIssue);
		
		issueDateField = new JTextField();
		issueDateField.setBounds(90, 303, 86, 20);
		i_frame.getContentPane().add(issueDateField);
		issueDateField.setColumns(10);
		issueDateField.setText("MM/dd/yyyy");
	
		JLabel lblNewLabel_4 = new JLabel("Issue Date");
		lblNewLabel_4.setBounds(10, 291, 138, 47);
		i_frame.getContentPane().add(lblNewLabel_4);
	    
		
		
		
	}

	private void return_book() {
		return_frame = new JFrame("Return Book");
		return_frame.setVisible(true);
		return_frame.setBounds(0, 0, 1024, 720);
		return_frame.pack();
		return_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return_frame.getContentPane().setLayout(null);
	    
		JLabel lblBookReturn = new JLabel("Book Return");
		lblBookReturn.setBounds(50, 33, 74, 14);
		return_frame.getContentPane().add(lblBookReturn);
	
		JLabel lblBook_1 = new JLabel("Book");
		lblBook_1.setBounds(10, 97, 46, 14);
		return_frame.getContentPane().add(lblBook_1);
	
		JLabel lblMemberId = new JLabel("Member ID");
		lblMemberId.setBounds(10, 171, 61, 14);
		return_frame.getContentPane().add(lblMemberId);
	    
	    returnMemberIDField = new JTextField();
		returnMemberIDField.setBounds(90, 163, 86, 20);
		return_frame.getContentPane().add(returnMemberIDField);
		returnMemberIDField.setColumns(10);
	
		JLabel lblFine = new JLabel("Fine");
		lblFine.setBounds(10, 201, 46, 14);
		return_frame.getContentPane().add(lblFine);
	    
	    returnBookFineField = new JTextField();
		returnBookFineField.setBounds(150, 200, 86, 20);
		return_frame.getContentPane().add(returnBookFineField);
		returnBookFineField.setColumns(10);
	    
	    JLabel lblNewLabel_5 = new JLabel("Pay Fine");
		lblNewLabel_5.setBounds(10, 318, 61, 14);
		return_frame.getContentPane().add(lblNewLabel_5);
	
		ReturnPayFineField = new JTextField();
		ReturnPayFineField.setBounds(150, 318, 86, 20);
		return_frame.getContentPane().add(ReturnPayFineField);
		ReturnPayFineField.setColumns(10);
	
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
		btnPayFine.setBounds(150, 360, 109, 23);
		return_frame.getContentPane().add(btnPayFine);
	
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
		list_returnedBooks.setBounds(90, 68, 128, 71);
		return_frame.getContentPane().add(list_returnedBooks);
	
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
		btnReturnBook.setBounds(150, 245, 109, 23);
		return_frame.getContentPane().add(btnReturnBook);
		
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
	
		DemoPolicy policy1 = new DemoPolicy(0, 0, 0);
		DemoPolicy policy2 = new DemoPolicy(1, 0, 0);
		DemoPolicy policy3 = new DemoPolicy(2, 30, 2);
		DemoPolicy policy4 = new DemoPolicy(3, 5, 5);
		DemoPolicy policy5 = new DemoPolicy(4, 1, 5);
		DemoPolicy policy6 = new DemoPolicy(5, 0, 0);
		policyList.add(policy1);
		policyList.add(policy2);
		policyList.add(policy3);
		policyList.add(policy4);
		policyList.add(policy5);
		policyList.add(policy6);
	
	}
}


