package fr.eni.projetEnchere.bo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {
	
	private int idBid;
	
	// not null, pk1
	private Member member;
	
	// not null, pk2
	private int idArticle;
	
	// not null
	private LocalDateTime bidDate;
	
	// not null
	private int bidPrice;
	
	public Bid() {
		super();
	}
	public Bid(int idBid, Member member, int idArticle, LocalDateTime bidDate, int bidPrice) {
		super();
		this.idBid = idBid;
		this.member = member;
		this.idArticle = idArticle;
		this.bidDate = bidDate;
		this.bidPrice = bidPrice;
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
	
	public int getIdBid() {
		return idBid;
	}
	public void setIdBid(int idBid) {
		this.idBid = idBid;
	}

	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bid [idBid=");
		builder.append(idBid);
		builder.append(", member=");
		builder.append(member);
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
		return Objects.hash(bidDate, bidPrice, idArticle, idBid, member);
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
		return Objects.equals(bidDate, other.bidDate) && bidPrice == other.bidPrice && idArticle == other.idArticle
				&& idBid == other.idBid && Objects.equals(member, other.member);
	}
	
	
	
	
	
	
	
	
	
	
}
