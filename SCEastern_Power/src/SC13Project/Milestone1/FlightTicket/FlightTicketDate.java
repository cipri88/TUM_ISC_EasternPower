package SC13Project.Milestone1.FlightTicket;

//Please do not change the name of the package or this class
public class FlightTicketDate {
    private int year;
    private int month;
    private int day;
	
    public int getYear() {
		return year;
	}
	
    public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int date) {
		this.day = date;
	}
	
	public int getDate() {
		return day;
	}

	public void setDate(int date) {
		this.day = date;
	}
	
	public String toString(){
		String msg = "";
		msg += year+"-"+month+"-"+day;
		return msg;
	}
}

