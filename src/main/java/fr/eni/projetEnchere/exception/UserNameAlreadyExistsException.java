package fr.eni.projetEnchere.exception;

public class UserNameAlreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7763696105716039650L;
	
	private String message;
	
	public UserNameAlreadyExistsException(String message) {
		this.message = message;
	}

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

	
}
