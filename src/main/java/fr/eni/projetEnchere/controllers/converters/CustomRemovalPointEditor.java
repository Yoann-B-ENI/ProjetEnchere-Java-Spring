package fr.eni.projetEnchere.controllers.converters;

import java.beans.PropertyEditorSupport;
import java.util.List;

import fr.eni.projetEnchere.bo.Category;
import fr.eni.projetEnchere.bo.RemovalPoint;

public class CustomRemovalPointEditor extends PropertyEditorSupport{
	
	private final List<RemovalPoint> allRemovalPoints;

    public CustomRemovalPointEditor(List<RemovalPoint> allRemovalPoints) {
        this.allRemovalPoints = allRemovalPoints;
    }
	
	@Override
    public String getAsText() {
		System.out.println("calling the get as text of custom editor (wrong value?)");
		RemovalPoint rp = (RemovalPoint) getValue();
        return rp == null ? "" : rp.toString();
    }
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
    	System.out.println("removal point editor set as text called, with " + text);
    	if (!text.isBlank() && text != null) {
	    	int id = Integer.valueOf(text);
	    	RemovalPoint rp = allRemovalPoints.stream().filter(elem -> elem.getIdRemovalPoint() == id)
	                .findFirst().orElse(null);
	        setValue(rp);
    	}
    }
	
	
	
	
}
