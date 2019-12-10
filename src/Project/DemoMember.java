package Project;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class DemoMember {

	private String memberName;
	private String memberId;
	private int memberType;
	private String memberPassword;
	private String memberEmail;
	private boolean validEntry = true;
	private int invoiceId;

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

	boolean validateEntry(ArrayList<DemoMember> memberList, DemoMember newMember) {

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

	boolean verifymember(DemoMember member, String password) {
		if (member.getMemberPassword().equals(password)) {
			return true;
		}
		return false;

	}

}
