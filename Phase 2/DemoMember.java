import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.util.List;
import java.nio.file.Paths;
import java.io.*;

import javax.swing.JOptionPane;

public class DemoMember {

	private String memberName;
	private String memberId;
	private int memberType;
	private String memberPassword;
	private String memberEmail;
	private boolean validEntry = true;
	private int invoiceId;
	 //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    //Student attributes index
    private static final int MEMBER_NAME_IDX = 0;
    private static final int MEMBER_ID_IDX = 1;
    private static final int MEMBER_TYPE_IDX = 2;
    private static final int MEMBER_PWD_IDX = 3; 
    private static final int MEMBER_EMAIL_IDX = 4;
    private static final String data_path = Paths.get("").toAbsolutePath().toString() + "\\db\\members.dat";
    
     
    //CSV file header
    private static final String FILE_HEADER = "membername,memberid,role, password,email";

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public DemoMember(String memberName, String memberId, int memberType, String memberPassword, String memberEmail) {
		this.memberName = memberName;
		this.memberId = memberId;
		this.memberType = memberType;
		this.memberPassword = memberPassword;
		this.memberEmail = memberEmail;

	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public String getMemberPassword() {
		return memberPassword;
	}

	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public static DemoMember findMember(ArrayList<DemoMember> memberList, String studentID) {

		for (DemoMember member : memberList) {
			if (member.getMemberId().equalsIgnoreCase(studentID)) {
				return member;
			}
		}
		return null;
	}
	
	public static DemoMember findMemberFromDB( String memberID) {
		ArrayList<DemoMember> members  = readMemberFromDB();
		for (DemoMember member : members) {
			if (member.getMemberId().equalsIgnoreCase(memberID)) {
				return member;
			}
		}
		return null;
	}

	boolean validateEntry(ArrayList<DemoMember> memberList, DemoMember newMember) {
		validEntry = true;
		for (DemoMember member : memberList) {

			if (!newMember.getMemberId().isEmpty() && !newMember.getMemberName().isEmpty()
					&& member.getMemberId().equalsIgnoreCase(newMember.getMemberId()) && validEntry) {

				if (member.getMemberName().equalsIgnoreCase(newMember.getMemberName()) && validEntry) {
					JOptionPane.showMessageDialog(null, "Your account already exits");
					validEntry = false;
					break;
				}
				JOptionPane.showMessageDialog(null, "ID already exits");
				validEntry = false;
				return validEntry;
			}

			else if (newMember.getMemberId().isEmpty() && newMember.getMemberId().length() < 6) {
				JOptionPane.showMessageDialog(null, "Please Enter A Valid 6 Digit  ID");
				validEntry = false;
			} else if (newMember.getMemberName().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Enter A Valid Name");
				validEntry = false;
			}
		}
		return validEntry;

	}
	
	boolean validateEntryfromDB(DemoMember newMember) {
		validEntry = true;
		ArrayList<DemoMember> members = readMemberFromDB();
		for (DemoMember member: members)
		{
			if (!newMember.getMemberId().isEmpty() && !newMember.getMemberName().isEmpty()
					&& member.getMemberId().equalsIgnoreCase(newMember.getMemberId()) && validEntry) {
				JOptionPane.showMessageDialog(null, "ID already exits");
				validEntry = false;
				return validEntry;
			}

			else if (newMember.getMemberId().isEmpty() && newMember.getMemberId().length() < 6) {
				JOptionPane.showMessageDialog(null, "Please Enter A Valid 6 Digit  ID");
				validEntry = false;
			} else if (newMember.getMemberName().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Enter A Valid Name");
				validEntry = false;
			}
			
		}
		return validEntry;
	}

	boolean verifymember(DemoMember member, String password) {
		if (member.getMemberPassword().equals(password)) {
			return true;
		}
		return false;

	}
	
	
	
	public static void updateMember2DB(DemoMember member) {
        
        //Create new students objects
       // DemoMember member1 = new DemoMember("Ahmed", "ahmed123", 1,   "1234", "abc@g");
		FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter(data_path, true);
 
            //Write the CSV file header
            //fileWriter.append(FILE_HEADER.toString());
             
            //Add a new line separator after the header
            //fileWriter.append(NEW_LINE_SEPARATOR);
             
            //Write a new student object list to the CSV file
           
            fileWriter.append(member.getMemberName());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(member.getMemberId());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(String.valueOf(member.getMemberType()));
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(member.getMemberPassword());
            fileWriter.append(COMMA_DELIMITER);
            fileWriter.append(member.getMemberEmail());
            fileWriter.append(NEW_LINE_SEPARATOR);
             
            System.out.println("Updated member to CSV file was successfully !!!");
             
        } catch (Exception e) {
            System.out.println("Error in update members to csv file !!!");
            e.printStackTrace();
        } finally {
             
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
             
        }
    }
	
	public static  ArrayList<DemoMember> readMemberFromDB() {
		BufferedReader fileReader = null;
        ArrayList<DemoMember> members = new ArrayList<DemoMember>();
        try {
             
           //Create the file reader
            fileReader = new BufferedReader(new FileReader(data_path));
            
            BufferedReader br = new BufferedReader(new FileReader(data_path));
            
        	String line = null;
        	while ((line = br.readLine()) != null) {
                //Get all tokens available in line
            	String[] tokens = line.split(COMMA_DELIMITER);
                if (tokens.length > 0) {
                    //Create a new student object and fill his  data
                	DemoMember member = new DemoMember(tokens[MEMBER_NAME_IDX], tokens[MEMBER_ID_IDX], Integer.parseInt(tokens[MEMBER_TYPE_IDX]), tokens[MEMBER_PWD_IDX], tokens[MEMBER_EMAIL_IDX]);
                	members.add(member);
                }
            }
        } 
        catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
		return members;

	}
	
	public static  boolean validateExistingUser (String username) {
		ArrayList<DemoMember> members = readMemberFromDB();
		boolean isUserExisting = false;
		for (DemoMember member: members)
		{
			if (member.memberId.equals(username))
			{
				isUserExisting = true;
				break;
			}
		}
		return isUserExisting;

	}
}
