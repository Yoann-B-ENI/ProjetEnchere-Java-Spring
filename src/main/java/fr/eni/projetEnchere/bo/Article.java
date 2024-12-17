package fr.eni.projetEnchere.bo;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.boot.jdbc.HikariCheckpointRestoreLifecycle;

public class Article {
	
	// not null, pk
	private int idArticle;
	
	// not null
	private String name;
	
	// not null
	private String description;
	
	// not null
	private LocalDateTime auctionStartDate;
	
	// not null
	private LocalDateTime auctionEndDate;
	
	// .
	private int startingPrice;
	
	// .
	private int salePrice;
	
	// not null, fk members
	private int vendor;
	
	// not null, fk categories
	private Category category;
	
	// not null, fk removalpoints
	private RemovalPoint removalPoint;
	
	// not null
	private ArticleStatus status;

	public Article() {
		super();
	}
	// only not nulls
	public Article(int idArticle, String name, String description, 
			LocalDateTime auctionStartDate, LocalDateTime auctionEndDate, ArticleStatus status, 
			int vendor, Category category, RemovalPoint removalPoint) {
		super();
		this.idArticle = idArticle;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.status = status;
		this.vendor = vendor;
		this.category = category;
		this.removalPoint = removalPoint;
	}
	// all attributes
	public Article(int idArticle, String name, String description, LocalDateTime auctionStartDate,
			LocalDateTime auctionEndDate, int startingPrice, int salePrice, int vendor, Category category,
			RemovalPoint removalPoint, ArticleStatus status) {
		super();
		this.idArticle = idArticle;
		this.name = name;
		this.description = description;
		this.auctionStartDate = auctionStartDate;
		this.auctionEndDate = auctionEndDate;
		this.startingPrice = startingPrice;
		this.salePrice = salePrice;
		this.vendor = vendor;
		this.category = category;
		this.removalPoint = removalPoint;
		this.status = status;
	}
	
	
	

	public int getIdArticle() {
		return idArticle;
	}
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getAuctionStartDate() {
		return auctionStartDate;
	}
	public void setAuctionStartDate(LocalDateTime auctionStartDate) {
		this.auctionStartDate = auctionStartDate;
	}
	public LocalDateTime getAuctionEndDate() {
		return auctionEndDate;
	}
	public void setAuctionEndDate(LocalDateTime auctionEndDate) {
		this.auctionEndDate = auctionEndDate;
	}
	public int getStartingPrice() {
		return startingPrice;
	}
	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}
	public int getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}
	public int getVendor() {
		return vendor;
	}
	public void setVendor(int vendor) {
		this.vendor = vendor;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public RemovalPoint getRemovalPoint() {
		return removalPoint;
	}
	public void setRemovalPoint(RemovalPoint removalPoint) {
		this.removalPoint = removalPoint;
	}
	public ArticleStatus getStatus() {
		return status;
	}
	public void setStatus(ArticleStatus status) {
		this.status = status;
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
		//builder.append(", Vendor=");
		//builder.append(vendor);
		builder.append(", Category=");
		builder.append(category);
		builder.append(", idRemovalPoint=");
		builder.append(removalPoint);
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
