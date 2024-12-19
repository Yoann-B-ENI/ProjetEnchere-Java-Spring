package fr.eni.projetEnchere.bo;

import java.util.Objects;

public class RemovalPoint {
	
	// not null, pk
	private int idRemovalPoint;
	
	// not null
	private int roadNumber;
	
	// not null
	private String roadName;
	
	// not null
	private String zipCode;
	
	// not null
	private String townName;
	
	// not null
	private Member member;
	
	// not null
	private String pointName;
	
	
	public RemovalPoint() {
		super();
	}


	public RemovalPoint(int idRemovalPoint, int roadNumber, String roadName, String zipCode, String townName, Member member,
			String pointName) {
		super();
		this.idRemovalPoint = idRemovalPoint;
		this.roadNumber = roadNumber;
		this.roadName = roadName;
		this.zipCode = zipCode;
		this.townName = townName;
		this.member = member;
		this.pointName = pointName;
	}


	public int getIdRemovalPoint() {
		return idRemovalPoint;
	}
	public void setIdRemovalPoint(int idRemovalPoint) {
		this.idRemovalPoint = idRemovalPoint;
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

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	public int getRoadNumber() {
		return roadNumber;
	}
	public void setRoadNumber(int roadNumber) {
		this.roadNumber = roadNumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemovalPoint [idRemovalPoint=");
		builder.append(idRemovalPoint);
		builder.append(", roadNumber=");
		builder.append(roadNumber);
		builder.append(", roadName=");
		builder.append(roadName);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", townName=");
		builder.append(townName);
		builder.append(", \n  >  >  member=");
		builder.append(member);
		builder.append(", \n  >  >  pointName=");
		builder.append(pointName);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(member, roadName, roadNumber, townName, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RemovalPoint other = (RemovalPoint) obj;
		return Objects.equals(member, other.member)
				&& roadNumber == other.roadNumber
				&& Objects.equals(roadName, other.roadName)
				&& Objects.equals(zipCode, other.zipCode)
				&& Objects.equals(townName, other.townName);
	}
	// not id because some java objects don't have initialised it yet, and we need to compare
	// against objects coming up from the db (in ArticleController / create for expl)




	
	
	
	
	
	
	
}
