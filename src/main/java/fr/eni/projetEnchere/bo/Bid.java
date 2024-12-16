package fr.eni.projetEnchere.bo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {
	
	// not null, pk1
	private int idMember;
	
	// not null, pk2
	private int idArticle;
	
	// not null
	private LocalDateTime bidDate;
	
	// not null
	private int bidPrice;
	
	public Bid() {
		super();
	}
	public Bid(int idMember, int idArticle, LocalDateTime bidDate, int bidPrice) {
		super();
		this.idMember = idMember;
		this.idArticle = idArticle;
		this.bidDate = bidDate;
		this.bidPrice = bidPrice;
	}
	public int getIdMember() {
		return idMember;
	}
	public void setIdMember(int idMember) {
		this.idMember = idMember;
	}
	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public LocalDateTime getBidDate() {
		return bidDate;
	}
	public void setBidDate(LocalDateTime bidDate) {
		this.bidDate = bidDate;
	}
	public int getBidPrice() {
		return bidPrice;
	}
	public void setBidPrice(int bidPrice) {
		this.bidPrice = bidPrice;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bid [idMember=");
		builder.append(idMember);
		builder.append(", idArticle=");
		builder.append(idArticle);
		builder.append(", bidDate=");
		builder.append(bidDate);
		builder.append(", bidPrice=");
		builder.append(bidPrice);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idArticle, idMember);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		return idArticle == other.idArticle && idMember == other.idMember;
	}
	
	
}
