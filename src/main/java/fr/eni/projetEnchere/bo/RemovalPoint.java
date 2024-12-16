package fr.eni.projetEnchere.bo;

import java.util.Objects;

public class RemovalPoint {
	
	// not null, pk
	private int idRemovalPoint;
	
	// not null
	private String roadName;
	
	// not null
	private String zipCode;
	
	// not null
	private String townName;
	
	
	public RemovalPoint() {
		super();
	}
	public RemovalPoint(int idRemovalPoint, String roadName, String zipCode, String townName) {
		super();
		this.idRemovalPoint = idRemovalPoint;
		this.roadName = roadName;
		this.zipCode = zipCode;
		this.townName = townName;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RemovalPoint [idRemovalPoint=");
		builder.append(idRemovalPoint);
		builder.append(", roadName=");
		builder.append(roadName);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", townName=");
		builder.append(townName);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idRemovalPoint, roadName, townName, zipCode);
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
		return idRemovalPoint == other.idRemovalPoint && Objects.equals(roadName, other.roadName)
				&& Objects.equals(townName, other.townName) && Objects.equals(zipCode, other.zipCode);
	}
	
	
	
	
}
