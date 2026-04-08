package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

import Exceptions.InvalidBirthDateException;
import enums.Gender;
import enums.Specialization;
import enums.Status;

public class Lawyer extends Person implements CasesInterface, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashSet<Case> casesHandled ;
	private Specialization specialization ;
	private int licenseNumber ;
	private double salary ;
	private Department department ;
	private String password;
	
	public Lawyer(int id, String firstName, String lastName, Date birthDate, String address, String phoneNumber,
			String email, Gender gender, Specialization specialization, int licenseNumber,
			double salary, String password) throws InvalidBirthDateException {
		super(id, firstName, lastName, birthDate, address, phoneNumber, email, gender);
		this.casesHandled = new HashSet<Case>();
		this.specialization = specialization;
		this.licenseNumber = licenseNumber;
		this.salary = salary;
		this.department = null;
		this.password = password;
	}
	
	public boolean addCase(Case newCase) {
		if( newCase == null) return false;
		if( casesHandled.contains(newCase)) return false;
		casesHandled.add(newCase);
		return true;
	}
	
	public boolean removeCase(Case caseToRemove) {
		if( caseToRemove == null) return false;
		if( !casesHandled.contains(caseToRemove)) return false;
		casesHandled.remove(caseToRemove);
		return true;
	}
	
	public boolean closeCase(Case caseToClose) {
		if( caseToClose == null) return false;
		if( !casesHandled.contains(caseToClose)) return false;
		casesHandled.remove(caseToClose);
		caseToClose.setCaseStatus(Status.finished);
		casesHandled.add(caseToClose);
		return true;
	}

	public HashSet<Case> getCasesHandled() {
		return casesHandled;
	}

	public void setCasesHandled(HashSet<Case> casesHandled) {
		this.casesHandled = casesHandled;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

	public int getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(int licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	

	@Override
	public String toString() {
		return "Lawyer [id =" + getId()
				+ ", FirstName=" + getFirstName() + ", LastName=" + getLastName() + ", BirthDate="
				+ getBirthDate() + ", Address=" + getAddress() + ", PhoneNumber=" + getPhoneNumber()
				+ ", Email=" + getEmail() + ", Gender=" + getGender() +"licenseNumber= " + licenseNumber + ", salary=" + salary +  "]";
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(department, licenseNumber, salary, specialization);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lawyer other = (Lawyer) obj;
		return Objects.equals(casesHandled, other.casesHandled) && Objects.equals(department, other.department)
				&& licenseNumber == other.licenseNumber
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary)
				&& specialization == other.specialization;
	}

	@Override
	public Case getRequiredCase() {
		Case toRet=null;
		int maxLen=0;
		
		for(Case c : casesHandled)
			if(c.getCaseType()==specialization ) {
				if(c.gettestimoniesList().size()>maxLen) {
					maxLen=c.gettestimoniesList().size();
					toRet=c;
				}
			}
		return toRet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	
	
	
	
	

}
