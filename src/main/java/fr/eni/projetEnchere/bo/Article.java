package fr.eni.projetEnchere.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Article {
	
	// not null, pk
	private int idArticle;
	
	// not null
	private String name;
	
	// not null
	private String description;
	
	// not null
	private LocalDate auctionStartDate;
	
	// not null
	private LocalDate auctionEndDate;
	
	// .
	private int startingPrice;
	
	// .
	private int salePrice;
	
	// not null, fk members
	private int idVendor;
	
	// not null, fk categories
	private int idCategory;
	
	// not null, fk removalpoints
	private int idRemovalPoint;

	public Article() {
		super();
	}
	// only not nulls
	public Article(int idArticle, String name, String description, LocalDate auctionStartDate, LocalDate auctionEndDate,
			int idVendor, int idCategory, int idRemovalPoint) {
		super();
		this.idArticle = idArticle;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.idVendor = idVendor;
		this.idCategory = idCategory;
		this.idRemovalPoint = idRemovalPoint;
	}
	// all attributes
	public Article(int idArticle, String name, String description, LocalDate auctionStartDate, LocalDate auctionEndDate,
			int startingPrice, int salePrice, int idVendor, int idCategory, int idRemovalPoint) {
		super();
		this.idArticle = idArticle;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.startingPrice = startingPrice;
		this.salePrice = salePrice;
		this.idVendor = idVendor;
		this.idCategory = idCategory;
		this.idRemovalPoint = idRemovalPoint;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Article [idArticle=");
		builder.append(idArticle);
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", auctionStartDate=");
		builder.append(auctionStartDate);
		builder.append(", auctionEndDate=");
		builder.append(auctionEndDate);
		builder.append(", startingPrice=");
		builder.append(startingPrice);
		builder.append(", salePrice=");
		builder.append(salePrice);
		builder.append(", idVendor=");
		builder.append(idVendor);
		builder.append(", idCategory=");
		builder.append(idCategory);
		builder.append(", idRemovalPoint=");
		builder.append(idRemovalPoint);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(idArticle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		return idArticle == other.idArticle;
	}
	
	
	
}
