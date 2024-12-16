package fr.eni.projetEnchere.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//import fr.eni.projetEnchere.bo.Article;
//import fr.eni.projetEnchere.bo.Bid;
//import fr.eni.projetEnchere.bo.RemovalPoint;


public class Member {
	private int idMember;
	private String userName;
	private String password;
	private String name;
	private String firstName;
	private String email;
	private String phoneNumber;
	private String roadName;
	private String zipCode;
	private String townName;
	private String credits;
	private Boolean admin;
	
//	private List<Article> articles = new ArrayList();
//	private List<Bid> bids = new ArrayList(); 
//	private List<RemovalPoint> removalPoints = new ArrayList();
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public Member(int idMember, String userName, String password, String name, String firstName, String email,
			String roadName, String zipCode, String townName, String credits, Boolean admin) {
		super();
		this.idMember = idMember;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.roadName = roadName;
		this.zipCode = zipCode;
		this.townName = townName;
		this.credits = credits;
		this.admin = admin;
	}



	public Member(int idMember, String userName, String password, String name, String firstName, String email,
			String phoneNumber, String roadName, String zipCode, String townName, String credits, Boolean admin) {
		super();
		this.idMember = idMember;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.roadName = roadName;
		this.zipCode = zipCode;
		this.townName = townName;
		this.credits = credits;
		this.admin = admin;
	}


//	public Member(int idMember, String userName, String password, String name, String firstName, String email,
//			String phoneNumber, String roadName, String zipCode, String townName, String credits, Boolean admin,
//			List<Article> articles, List<Bid> bids, List<RemovalPoint> removalPoints) {
//		super();
//		this.idMember = idMember;
//		this.userName = userName;
//		this.password = password;
//		this.name = name;
//		this.firstName = firstName;
//		this.email = email;
//		this.phoneNumber = phoneNumber;
//		this.roadName = roadName;
//		this.zipCode = zipCode;
//		this.townName = townName;
//		this.credits = credits;
//		this.admin = admin;
//		this.articles = articles;
//		this.bids = bids;
//		this.removalPoints = removalPoints;
//	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Members [idMember=");
		builder.append(idMember);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", name=");
		builder.append(name);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", roadName=");
		builder.append(roadName);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", townName=");
		builder.append(townName);
		builder.append(", credits=");
		builder.append(credits);
		builder.append(", admin=");
		builder.append(admin);
		builder.append("]");
		return builder.toString();
	}


	@Override
	public int hashCode() {
		return Objects.hash(idMember);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		return idMember == other.idMember;
	}


	public int getIdMember() {
		return idMember;
	}


	public void setIdMember(int idMember) {
		this.idMember = idMember;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getRoadName() {
		return roadName;
	}


	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}


	public String getZipCode() {
		return zipCode;
	}


	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}


	public String getTownName() {
		return townName;
	}


	public void setTownName(String townName) {
		this.townName = townName;
	}


	public String getCredits() {
		return credits;
	}


	public void setCredits(String credits) {
		this.credits = credits;
	}


	public Boolean getAdmin() {
		return admin;
	}


	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}


//	public List<RemovalPoint> getRemovalPoints() {
//		return removalPoints;
//	}
//
//
//	public void setRemovalPoints(List<RemovalPoint> removalPoints) {
//		this.removalPoints = removalPoints;
//	}
//
//
//	public List<Article> getArticles() {
//		return articles;
//	}
//
//
//	public void setArticles(List<Article> articles) {
//		this.articles = articles;
//	}
//
//
//	public List<Bid> getBids() {
//		return bids;
//	}
//
//
//	public void setBids(List<Bid> bids) {
//		this.bids = bids;
//	}
//	
	
	
}
