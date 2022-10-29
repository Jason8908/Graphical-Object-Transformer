package assignment;

class WrongFileFormatException extends Exception {
	public String message;
	public WrongFileFormatException(String message) {
		this.message = message;
	}
}
