package application.util;

public class Formatter {
	
	/**
	 * 
	 * @param input a string input of postal code
	 * @return a valid and formatted Canadian postal code
	 * @throws InvalidValueException if the input value is not valid to be formatted
	 */
	public static String formatPostalCode (String input) throws InvalidValueException {
		input = input.replaceAll("\\s", "");
		if(input.matches("^([A-Za-z]\\d[A-Za-z][-]?\\d[A-Za-z]\\d)")) {
			input = input.toUpperCase();
			return input.substring(0, 3) + " " + input.substring(3, input.length());
		} else {
			throw new InvalidValueException();
		}
	}


}
