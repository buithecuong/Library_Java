import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.nio.file.Paths;
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
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class LibrarySystem {

	
	private static int memberTypeId;
	private static ArrayList<DemoMember> memberList = new ArrayList<>();
	private static ArrayList<DemoBook> bookList = new ArrayList<>();
	private static ArrayList<DemoPolicy> policyList = new ArrayList<>();
	private static ArrayList<DemoInvoice> invoiceList = new ArrayList<>();
	static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
	static Date returnDate = null;
	public static boolean status= false;

	

	private static final String data_path = Paths.get("").toAbsolutePath().toString() + "\\db\\members.dat";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					File file = new File(data_path);
					file.delete();
					DemoMember admin = new DemoMember("Admin1", "admin", 0,   "admin", "admin.library@cestarcollege.com");
					DemoMember.updateMember2DB(admin);
					DemoMember student = new DemoMember("Student1", "student", 3,   "student", "s.library@cestarcollege.com");
					DemoMember.updateMember2DB(student);
					DemoMember instructor = new DemoMember("Instructor 1", "instructor", 2,   "instructor", "i.library@cestarcollege.com");
					DemoMember.updateMember2DB(instructor);
					DemoMember librarian = new DemoMember("Librarian 1", "librarian", 1,   "librarian", "l.library@cestarcollege.com");
					DemoMember.updateMember2DB(librarian);
					populate();
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
             
            if(userID.equals("")||password.equals("")) //If username is null
            {
                JOptionPane.showMessageDialog(null,"Missing username/password"); //Display dialog box with the message
            } 
            else { //If both the fields are present then to login the user, check wether the user exists already
                //System.out.println("Login connect");
                try
                {
                	DemoMember user = DemoMember.findMemberFromDB(userID) ;
                	if(!user.getMemberId().equalsIgnoreCase(userID) || ! user.getMemberPassword().equals(password)) { //Move pointer below
                      JOptionPane.showMessageDialog(null,"Wrong Username/Password!"); //Display Message
                     }
                     else 
                     {
                    	 status = true;
                    	 f.dispose();
                    	 main_menu(user);
 						
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
	private static void main_menu(DemoMember user) {
		
		JFrame frame;
		// JMenu 
	    JMenu main, admin, student, instructor, librarian,  help; 
	  
	    // Menu items 
	    JMenuItem inv_menu, reg_menu, issue_men, return_men, logout_men; 
		
		int user_role = user.getMemberType();
		String role_str;
		switch(user_role) 
	    { 
	        case 0: 
	        	role_str = "Admin";
	            break; 
	        case 1: 
	        	role_str = "Librarian"; 
	            break; 
	        case 2: 
	        	role_str = "Instructor"; 
	            break; 
	        case 3: 
	        	role_str = "Student"; 
	            break; 
	        default: 
	        	role_str = "Others"; 
	    }
		
		
		frame = new JFrame("Cestar LMS");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lbl = new JLabel("Library Management System"); 
		JMenuBar mb = new JMenuBar(); 
		
	
	    // create menuitems  
	    inv_menu = new JMenuItem("Inventory"); 
	    reg_menu = new JMenuItem("Registration");
	    issue_men = new JMenuItem("Issue Book"); 
	    return_men = new JMenuItem("Return");
	
	 // add ActionListener to menuItems 
	    inv_menu.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            //JOptionPane.showMessageDialog(null,"Inventory menu!"); //Open a dialog box and display the message
	        	inventory();
	             
	        }
	    });
	    reg_menu.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            register();
	             
	        }
	    });
	    issue_men.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            issue_book(user);
	             
	        }
	    });
	    return_men.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            return_book(user);
	             
	        }
	    });
	    logout_men = new JMenuItem("Logout");
	    logout_men.addActionListener(new ActionListener() { //Perform action
	        public void actionPerformed(ActionEvent e){
	             
	            JOptionPane.showMessageDialog(null,"Are you sure to logout existing user!"); //Open a dialog box and display the message
	            frame.dispose();
	            login();
	             
	        }
	    });
	
	    main = new JMenu("Main");
	    main.add(logout_men);
	    help = new JMenu("Help");
	    // add menu to menu bar 
	    mb.add(main); 
	    mb.add(help); 
	    if(user_role == 0) {
	    	admin = new JMenu("Admin");
	        admin.add(reg_menu);
	        mb.add(admin); 
	    }
	    if(user_role == 1) { 
	    	librarian = new JMenu("Librarian");
	    	librarian.add(inv_menu);
	    	librarian.add(issue_men);
	    	librarian.add(return_men);
	    	mb.add(librarian); 
	    }
	    if(user_role == 3) { 
		    student = new JMenu("Student"); 
		    student.add(issue_men);
		    student.add(return_men);
		    mb.add(student); 
	    }
	    
	    if(user_role == 2) { 
		    instructor = new JMenu("Instructor"); 
		    instructor.add(issue_men);
		    instructor.add(return_men);
		    mb.add(instructor); 
	    }
	    // add menubar to frame 
	    frame.setJMenuBar(mb); 
	    
	    frame.setSize(500, 500); 
	
		frame.setVisible(true);
		frame.setBounds(0, 0, 1024, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		/*JLabel lblNewLabel = new JLabel("Welome " + role_str + " " + user.getMemberName().toUpperCase() + " TO LIBRARY!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(349, 0, 600, 77);
		frame.getContentPane().add(lblNewLabel);*/
		if(user_role != 0) {
			String [] columns = {"ID","Title","Author", "Qty"};
		    JButton btn_refesh = new JButton("Refresh");
		    btn_refesh.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent arg0) {
		            
		        	List<String[]> values = new ArrayList<String[]>();
		    		
		    	    
		    	    for (DemoBook book : bookList) {
		    			values.add(new String[] {String.valueOf(book.getBookId()), book.getBookName(),book.getBookAuthor(), String.valueOf(book.getBookQuantity())});
		    		}
		    		
		    		
		    	    TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns);
		    	    JTable table = new JTable(tableModel);
		    	    frame.setLayout(new BorderLayout());
		    	    table.setBounds(0, 100, 300, 300);
		    	    
		    	    frame.add(new JScrollPane(table), BorderLayout.CENTER);
		    	
		    	    frame.add(table.getTableHeader(), BorderLayout.NORTH);
		    	
		    	    frame.setVisible(true);     
	
		            
		        }
		    });
		    btn_refesh.setBounds(122, 360, 89, 23);
		    frame.getContentPane().add(btn_refesh);
		}
		
	
	}

	private static void inventory() {
		JFrame inv_frame;
		inv_frame = new JFrame("Book Inventory");
	    inv_frame.setBounds(0, 0, 512, 720);
	    inv_frame.setVisible(true);
	    inv_frame.setSize(340, 350); 
	    inv_frame.getContentPane().setLayout(null);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setBounds(10, 30, 46, 14);
	    inv_frame.getContentPane().add(lblTitle);
	    
	    JLabel lblID = new JLabel("Book ID");
	    lblID.setBounds(10, 60, 80, 14);
	    inv_frame.getContentPane().add(lblID);
	    
	    
	    JLabel lblAuthor = new JLabel("Author");
	    lblAuthor.setBounds(10, 90, 46, 14);
	    inv_frame.getContentPane().add(lblAuthor);
	    
	    JLabel lblqty = new JLabel("Qty");
	    lblqty.setBounds(10, 120, 46, 14);
	    inv_frame.getContentPane().add(lblqty);
	    
	    JLabel lblaction = new JLabel("Action Type");
	    lblaction.setBounds(10, 180, 46, 14);
	    inv_frame.getContentPane().add(lblaction);
	    
	    JTextField bookTitle = new JTextField();
	    bookTitle.setBounds(90, 40, 123, 20);
	    inv_frame.getContentPane().add(bookTitle);
	    bookTitle.setColumns(10);
	    JTextField bookID = new JTextField();
	    bookID.setBounds(90, 70, 123, 20);
	    inv_frame.getContentPane().add(bookID);
	    bookID.setColumns(10);
	
	    JTextField author = new JTextField();
	    author.setBounds(90, 95, 123, 20);
	    inv_frame.getContentPane().add(author);
	    author.setColumns(10);
	    
	    JTextField qty = new JTextField();
	    qty.setBounds(90, 130, 123, 20);
	    inv_frame.getContentPane().add(qty);
	    qty.setColumns(10);
	
	    String[] actions = { "Add", "Update", "Remove" };
	
	    
	    final JComboBox<String> actionList = new JComboBox<String>(actions);
	    actionList.setBounds(90, 180, 123, 20);
	
	    actionList.setVisible(true);
	    inv_frame.getContentPane().add(actionList);
	    
	    
	    JButton btn_inv = new JButton("Inventory");
	    btn_inv.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	            
	        	//DemoBook DemoBook = new DemoBook("Business", 2, "Cj", 100);
	        	String action = (String) actionList.getSelectedItem();
	        	DemoBook existingBook =DemoBook.findBook(bookList, bookTitle.getText());
	        	DemoBook updateBook = null;
	        	DemoBook removeBook = null;
	        	if (existingBook!= null && existingBook.getBookAuthor().equalsIgnoreCase(author.getText()) ) 	{
	        		updateBook = new DemoBook( existingBook.getBookName(), Integer.parseInt(qty.getText()) + existingBook.getBookQuantity() ,  existingBook.getBookAuthor(), existingBook.getBookId());
	        		if (existingBook.getBookQuantity()> Integer.parseInt(qty.getText()))
	        				removeBook = new DemoBook( existingBook.getBookName(),  existingBook.getBookQuantity() - Integer.parseInt(qty.getText()) ,  existingBook.getBookAuthor(), existingBook.getBookId());
	        	}
	        	DemoBook newBook = new DemoBook( bookTitle.getText(), Integer.parseInt(qty.getText())  ,  author.getText(), Integer.parseInt(bookID.getText()));
	        	
	            switch(action) 
	            { 
	                case "Add": 
	                	if (existingBook == null) {
	                		bookList.add(newBook);
	                	} 
	                	else {
	                		bookList.add(updateBook); 
	                	}
	                	
	                	JOptionPane.showMessageDialog(null,"Add books successfully!"); //Open a dialog box and display the message
	                    break; 
	                case "Remove": 
	                	bookList.remove(existingBook);
	                	if (removeBook != null)
	                		bookList.add(removeBook);
	                	JOptionPane.showMessageDialog(null,"Remove books successfully!"); //Open a dialog box and display the message
	                    break; 
	                case "Update": 
	                	if (updateBook != null) {
	                		bookList.remove(existingBook);
	                	}
	                		bookList.add(newBook);
	                	JOptionPane.showMessageDialog(null,"Update books successfully!"); //Open a dialog box and display the message
	                    break; 
	                default: 
	                	JOptionPane.showMessageDialog(null,"Action " + action + " is not ready for use!"); //Open a dialog box and display the message
	            } 
	        	      
	
	            
	        }
	    });
	    btn_inv.setBounds(122, 220, 89, 23);
	    inv_frame.getContentPane().add(btn_inv);
	    
	    
	    
	    
	}


	private static void register() {
		JFrame r_frame = new JFrame("Register member");
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
	
	    JTextField memberName = new JTextField();
	    memberName.setBounds(90, 94, 123, 20);
	    r_frame.getContentPane().add(memberName);
	    memberName.setColumns(10);
	
	    JTextField memberID = new JTextField();
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
	    
	    JLabel lblEmail = new JLabel("Email");
	    lblEmail.setBounds(30, 275, 46, 14);
	    r_frame.getContentPane().add(lblEmail);
	    
	    
	    JTextField txtFieldRegEmail = new JTextField();
	    txtFieldRegEmail.setBounds(90, 272, 200, 20);
	    r_frame.getContentPane().add(txtFieldRegEmail);
	    txtFieldRegEmail.setColumns(10);
	
	    JLabel lblRegPwd = new JLabel ("Password");
	    lblRegPwd.setBounds(30, 321, 64, 14);
	    r_frame.getContentPane().add(lblRegPwd);
	    
	    JPasswordField regPwdField = new JPasswordField();
		regPwdField.setBounds(90, 318, 124, 20);
		r_frame.getContentPane().add(regPwdField);
	
	    JButton btn_register = new JButton("Submit");
	    btn_register.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent arg0) {
	            boolean validEntry = true;
	            boolean validType = true;
	            boolean validEmail = true;
	            boolean validPassword = true;
	           
	            
	            String select_role = (String) roleList.getSelectedItem();
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
	           
	            
	    		 if (!txtFieldRegEmail.getText().contains("@") || !txtFieldRegEmail.getText().contains(".")) {
		                validEmail = false;
		            }
		
		            if (!(regPwdField.getText().length() > 6)) {
		                validPassword = false;
		            }
	            DemoMember member = new DemoMember( memberName.getText(), memberID.getText(), memberTypeId,
	            regPwdField.getText(), txtFieldRegEmail.getText());
	
	            
	
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
	    
	}
	
	private static void issue_book(DemoMember returnMember) {
		
		//JOptionPane.showMessageDialog(null,"Issue book menu!"); //Open a dialog box and display the message
		JFrame i_frame = new JFrame("Issue Book");
	    i_frame.setVisible(true);
		i_frame.setBounds(0, 0, 1024, 720);
		i_frame.setSize(400, 450); 
		i_frame.getContentPane().setLayout(null);
		
		JLabel lblBookIssue = new JLabel("Welcome to Library Book Issue!");
		lblBookIssue.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblBookIssue.setBounds(10, 30, 400, 63);
		i_frame.getContentPane().add(lblBookIssue);
	    
		String[] values = convertArrList2Arr(DemoBook.getBookNameArrList(bookList));
		
		
		
		
		JPanel panel = new JPanel(new BorderLayout());
	    final JList<String> list = new JList<String>(values);
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(list);
	    list.setLayoutOrientation(JList.VERTICAL);
	    panel.add(scrollPane);
	    panel.setBounds(10, 80, 350, 180);
	    i_frame.add(panel);
	
	    JTextField issueDateField = new JTextField();
		issueDateField.setBounds(90, 290, 86, 20);
		i_frame.getContentPane().add(issueDateField);
		issueDateField.setColumns(10);
		issueDateField.setText("MM/dd/yyyy");
	
		JLabel lblNewLabel_4 = new JLabel("Issue Date");
		lblNewLabel_4.setBounds(10, 280, 138, 47);
		i_frame.getContentPane().add(lblNewLabel_4);
	
		/*JLabel lblBook = new JLabel("Book");
		lblBook.setBounds(10, 140, 46, 14);
		i_frame.getContentPane().add(lblBook);*/
	
		JButton btnNewIssue = new JButton("Issue");
		btnNewIssue.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent arg0) {
	
				ArrayList<DemoBook> issuedBookList = new ArrayList<>();
				//ArrayList<String> issuedbookNameList = (ArrayList<String>) list_2.getSelectedValuesList();
				ArrayList<String> issuedbookNameList = (ArrayList<String>) list.getSelectedValuesList();
				
				ArrayList<DemoMember> activatedMemberList = memberList;
				DemoLibrarian librarian = new DemoLibrarian();
				DemoInvoice invc = null;
				boolean checkBookAvailability = true;
				String issueDate = issueDateField.getText();
	
				
	
				for (String bookName : issuedbookNameList) {
					DemoBook Dbook = DemoBook.findBook(bookList, bookName);
					issuedBookList.add(Dbook);
				}
				
				checkBookAvailability = DemoBook.checkBookAvailabilityForProcess(issuedBookList, bookList);
	
				if (checkBookAvailability) {
	
						DemoPolicy policy = DemoPolicy.findPolicy(policyList, returnMember.getMemberType());
		
						if (invoiceList.isEmpty()) {
							for (DemoBook issueBook : issuedBookList) {
								try {
										if (issueDate.toString().equals("MM/dd/yyyy")) {
											invc = new DemoInvoice(returnMember, policy, issueBook, librarian.getName());
											
										} else {
											invc = new DemoInvoice(returnMember, policy, issueBook, librarian.getName(), issueDate);
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
						} 
						else {
		
								for (DemoBook issueBook : issuedBookList) {
									invc = DemoInvoice.checkForInvoice(invoiceList, issueBook, returnMember);
									if (invc == null) {
										try {
											if (issueDate.toString().equals("MM/dd/yyyy")) {
												invc = new DemoInvoice(returnMember, policy, issueBook, librarian.getName());
												
											} else {
												invc = new DemoInvoice(returnMember, policy, issueBook, librarian.getName(), issueDate);
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
					} 
					else{
						issuedBookList.clear();
						JOptionPane.showMessageDialog(null, "Book Is Not Available");
					}
				}
		});
		btnNewIssue.setBounds(90, 320, 89, 23);
		i_frame.getContentPane().add(btnNewIssue);
		
		
	    
		
		
		
	}

	private static void return_book(DemoMember returnMember) {
		JFrame return_frame = new JFrame("Return Book");
		return_frame.setVisible(true);
		return_frame.setBounds(0, 0, 1024, 720);
		return_frame.setSize(400, 500); 
		//return_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return_frame.getContentPane().setLayout(null);
	    
		JLabel lblBookReturn = new JLabel("Book Return");
		lblBookReturn.setBounds(50, 33, 74, 14);
		return_frame.getContentPane().add(lblBookReturn);
	
		/*JLabel lblBook_1 = new JLabel("Book");
		lblBook_1.setBounds(10, 97, 46, 14);
		return_frame.getContentPane().add(lblBook_1);*/
		String[] values = convertArrList2Arr(DemoBook.getBookNameArrList(bookList));
		
		
		
		JPanel panel = new JPanel(new BorderLayout());
	    JList<String> list_returnedBooks = new JList<String>(values);
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(list_returnedBooks);
	    list_returnedBooks.setLayoutOrientation(JList.VERTICAL);
	    panel.add(scrollPane);
	    panel.setBounds(10, 80, 350, 180);
	    return_frame.add(panel);
	
		JLabel lblFine = new JLabel("Fine");
		lblFine.setBounds(10, 290, 46, 14);
		return_frame.getContentPane().add(lblFine);
	    
		JTextField returnBookFineField = new JTextField();
		returnBookFineField.setBounds(150, 290, 100, 20);
		return_frame.getContentPane().add(returnBookFineField);
		returnBookFineField.setColumns(10);
		returnBookFineField.setText("0");
	    
	    JLabel lblNewLabel_5 = new JLabel("Pay Fine");
		lblNewLabel_5.setBounds(10, 320, 100, 14);
		return_frame.getContentPane().add(lblNewLabel_5);
	
		JTextField ReturnPayFineField = new JTextField();
		ReturnPayFineField.setBounds(150, 320, 100, 20);
		return_frame.getContentPane().add(ReturnPayFineField);
		ReturnPayFineField.setColumns(10);
		ReturnPayFineField.setText("0");
	
		JButton btnPayFine = new JButton("Pay Fine");
		btnPayFine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	
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
							if (invc.getMember().getMemberId().equals(returnMember.getMemberId()) && invoicesDueForFine) {
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
		btnPayFine.setBounds(150, 340, 100, 23);
		return_frame.getContentPane().add(btnPayFine);
	
		
	
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
		btnReturnBook.setBounds(150, 260, 100, 23);
		return_frame.getContentPane().add(btnReturnBook);
		
	}

	private static void populate() {
		
		
		DemoBook book1 = new DemoBook("Big Data Fundamentals - Data Storage Networking", 2, "Cj", 100);
		DemoBook book2 = new DemoBook("Database Design", 2, "Kd", 101);
		DemoBook book3 = new DemoBook("Networking Foundations", 2, "Cr", 102);
		DemoBook book4 = new DemoBook("Virtualization", 2, "DD", 103);
		DemoBook book5 = new DemoBook("Programming Java", 2, "DD", 104);
		DemoBook book6 = new DemoBook("Operating Systems Foundations", 2, "DD", 105);
		DemoBook book7 = new DemoBook("Big Data Strategies", 2, "DD", 106);
		DemoBook book8 = new DemoBook("Server Admin 1", 2, "DD", 107);
		bookList.add(book1);
		bookList.add(book2);
		bookList.add(book3);
		bookList.add(book4);
		bookList.add(book5);
		bookList.add(book6);
		bookList.add(book7);
		bookList.add(book8);
	
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
	
	public static String[] convertArrList2Arr(ArrayList<String> bookNameArrList) {
		String arr[] = new String[bookNameArrList.size()]; 
		  
	    // ArrayList to Array Conversion 
	    for (int j = 0; j < bookNameArrList.size(); j++) { 
	
	        // Assign each value to String array 
	        arr[j] = bookNameArrList.get(j); 
	    } 
	
	    return arr; 
	}
}



