package fr.eni.projetEnchere.bo;

import java.util.Objects;

public class Category {
	
	// not null, pk
	private int idCategory;
	
	// not null
	private String name;
	
	public Category() {
		super();
	}
	public Category(int idCategory, String name) {
		super();
		this.idCategory = idCategory;
		this.name = name;
	}
	
	
	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Category [idCategory=");
		builder.append(idCategory);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idCategory, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return idCategory == other.idCategory && Objects.equals(name, other.name);
	}
	
	
	
	
	
}
