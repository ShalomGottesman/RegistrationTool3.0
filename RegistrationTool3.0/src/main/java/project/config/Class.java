package project.config;

public class Class {
	private String name;
	private String CRN;
	private String section;
	private String number;
	private String priority;
	private Integer priorityNumber;
	private String department;
	
	public Class(String name, String CRN, String section, String number, String priority, String department) {
		this.name = name;
		this.CRN = CRN;
		this.section = section;
		this.number = number;
		this.priority = priority;
		this.department = department;
		switch (this.priority) {
			case "High":
				this.priorityNumber = 3;
				break;
			
			case "Medium":
				this.priorityNumber = 2;
				break;
				
			case "Low":
				this.priorityNumber = 1;
				break;
			default :
				this.priorityNumber = 0;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCRN() {
		return CRN;
	}

	public void setCRN(String cRN) {
		CRN = cRN;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public Integer getPriorityNumber () {
		return this.priorityNumber;
	}
	
	public void setPriorityNumber(Integer x) {
		this.priorityNumber = x;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String toString() {
		String str = "Name=["+this.name+"]"+
					 "| CRN=["+this.CRN+"]"+
					 "| Department=["+this.department+"]"+
					 "| Number=["+this.number+"]"+
					 "| Section=["+this.section+"]"+
					 "| Prioraity=["+this.priority+"]"+
					 "\n";
		return str;
	}

}
