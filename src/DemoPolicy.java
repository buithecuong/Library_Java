import java.util.ArrayList;

public class DemoPolicy {

	private int memberType;
	private int finePerDay;
	private int daysToBorrow;

	public DemoPolicy(int memberType, int finePerDay, int daysToBorrow) {
		super();
		this.memberType = memberType;
		this.finePerDay = finePerDay;
		this.daysToBorrow = daysToBorrow;
	}

	public int getMemberType() {
		return memberType;
	}

	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}

	public int getFinePerDay() {
		return finePerDay;
	}

	public void setFinePerDay(int finePerDay) {
		this.finePerDay = finePerDay;
	}

	public int getDaysToBorrow() {
		return daysToBorrow;
	}

	public void setDaysToBorrow(int daysToBorrow) {
		this.daysToBorrow = daysToBorrow;
	}

	public static DemoPolicy findPolicy(ArrayList<DemoPolicy> policyList, int type) {

		for (DemoPolicy policy : policyList) {
			if (policy.getMemberType() == type) {
				return policy;
			}
		}
		return null;
	}

}
