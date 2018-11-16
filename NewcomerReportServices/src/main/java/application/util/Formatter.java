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

	/**
	 * Formats a given phone number into Canadian domestic style
	 * @param input a string input of phone number
	 * @return a valid and formatted Canadian phone number
	 * @throws InvalidValueException if the input value is not valid to be formatted
	 */
	public static String formatPhoneNumber (String input) throws InvalidValueException {
		// replace spaces by empty string
		input = input.replaceAll("\\s",  "");
		// replace all dashes with empty space
		input = input.replaceAll("-", "");
		input = input.replaceAll("\\(", "");
		input = input.replaceAll("\\)", "");
		// check if input is 9 numbers
		if(input.matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d")) {
			return "(" + input.substring(0, 3) + ") " + input.substring(3, 6) + "-" + input.substring(6, input.length());
		} else {
			throw new InvalidValueException();
		}
	}

}
