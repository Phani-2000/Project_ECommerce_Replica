package com.casestudy.shoppingcart.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -634544925268378841L;

	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
