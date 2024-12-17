package fr.eni.projetEnchere.controllers.converters;

import java.beans.PropertyEditorSupport;
import java.util.List;

import fr.eni.projetEnchere.bo.Category;

public class CustomCategoryEditor extends PropertyEditorSupport{
	
	private final List<Category> allCategories;

    public CustomCategoryEditor(List<Category> allCategories) {
        this.allCategories = allCategories;
    }
	
	@Override
    public String getAsText() {
        Category cat = (Category) getValue();
        return cat == null ? "" : cat.getName();
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	System.out.println("category editor set as text called, with " + text);
    	if (!text.isBlank() && text != null) {
	    	int id = Integer.valueOf(text);
	    	Category cat = allCategories.stream().filter(elem -> elem.getIdCategory() == id)
	                .findFirst().orElse(null);
	        setValue(cat);
    	}
    }
    
    
    
    
    
    
}
