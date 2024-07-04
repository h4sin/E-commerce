package Ecommerce.Exeptions;

	public class ProductNotExistsException extends IllegalArgumentException {
	    public ProductNotExistsException(String message) {
	        super(message);
	    }
	}

