package fr.eni.projetEnchere.bo;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.boot.jdbc.HikariCheckpointRestoreLifecycle;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Article {
	
	// not null, pk
	private int idArticle;
	
	@NotNull(message = "Le nom de l'article est obligatoire.")
	@NotBlank(message = "Le nom doit contenir autre chose que des espaces.")
	@Size(min = 3, max = 30, message = "Le nom doit faire entre 3 et 30 caractères.")
	private String name;

	@NotNull(message = "La description de l'article est obligatoire.")
	@NotBlank(message = "La description doit contenir autre chose que des espaces.")
	@Size(min = 3, max = 300, message = "La description doit faire entre 3 et 300 caractères.")
	private String description;

	@NotNull(message = "La date de mise en vente est obligatoire.")
	// took out the FutureOrPresent because it didn't work for current minute? + people might cross over the minute
	private LocalDateTime auctionStartDate;

	@NotNull(message = "La date de fin de vente est obligatoire.")
	@Future
	// validating that it's after the start date looks horrible to do
	private LocalDateTime auctionEndDate;
	
	@Min(value = 0, message = "Le prix de départ doit être positif.")
	private int startingPrice;
	
	// .
	private int salePrice;
	
	// not null, fk members
	//@NotNull(message = "L'article doit avoir un vendeur.") // CANT, see ArticleController, we assign afterwards
	private Member vendor;
	
	// null, fk members
	// can be null at creation, so same in sql
	private Member buyer;
	
	@NotNull(message = "L'article doit avoir une catégorie.")
	private Category category;

	@NotNull(message = "L'article doit avoir un point de retrait.")
	private RemovalPoint removalPoint;
	
	// not null
	private ArticleStatus status;

	public Article() {
		super();
	}
	// only not nulls
	public Article(int idArticle, String name, String description, 
			LocalDateTime auctionStartDate, LocalDateTime auctionEndDate, ArticleStatus status, 
			Member vendor, Category category, RemovalPoint removalPoint) {
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
			LocalDateTime auctionEndDate, int startingPrice, int salePrice, Member vendor, Category category,
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
	public Member getVendor() {
		return vendor;
	}
	public void setVendor(Member vendor) {
		this.vendor = vendor;
	}
	public Member getBuyer() {
		return buyer;
	}
	public void setBuyer(Member buyer) {
		this.buyer = buyer;
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
		builder.append(", \n  >  >  vendor=");
		builder.append(vendor);
		builder.append(", \n  >  >  buyer=");
		builder.append(buyer);
		builder.append(", \n  >  >  category=");
		builder.append(category);
		builder.append(", \n  >  >  removalPoint=");
		builder.append(removalPoint);
		builder.append(", \n  >  >  status=");
		builder.append(status);
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
