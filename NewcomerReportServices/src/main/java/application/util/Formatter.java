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
	 * 
	 * @param input a string input of date
	 * @return a valid and formatted date in YYY-MM-DD
	 * @throws InvalidValueException if the input value is not valid to be formatted
	 */
	public static String formatDate (String input) throws InvalidValueException {
		input = input.replace("\\", "-").replace("/", "-").replace(" ", "-");
		
		// invalid / unsupported string input
		if(input.length() != 10) {
			throw new InvalidValueException();
		}
		
		int first;
		int second;
		
		
		// if YYYY-MM-DD, check which is mm and which is dd
		if(input.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
			return input;
		} 
		// YYYY-DD-MM
		else if(input.matches("([12]\\d{3}-(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2]))")) {
			return input.substring(0, 4) + "-" + input.substring(8, 10) + "-" + input.substring(5, 7);
		}
		// if MM-DD-YYYY, check which is mm and which is dd
		else if(input.matches("((0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])-[12]\\d{3})")) {
			// otherwise it's MM-DD-YYYY, fix
			return input.substring(6 ,10) + "-" + input.substring(0, 2) + "-" + input.substring(3, 5);
		}
		// if DD-MM-YYYY
		else if(input.matches("((0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-[12]\\d{3})")) {
			return input.substring(6, 10) + "-" + input.substring(3, 5) + "-" + input.substring(0, 2);
		}
		// if DD-YYYY-MM
		else if(input.matches("((0[1-9]|[12]\\d|3[01])-[12]\\d{3}-(0[1-9]|1[0-2]))")) {
			return input.substring(3, 7) + "-" + input.substring(8, 10) + "-" + input.substring(0, 2);
		}
		// if MM-YYYY-DD
		else if(input.matches("((0[1-9]|1[0-2])-[12]\\d{3}-(0[1-9]|[12]\\d|3[01]))")) {
			return input.substring(3, 7) + "-" + input.substring(0, 2) + "-" + input.substring(8, 10);
		}
		else {
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
	
	/**
	 * Checks if a given email is of valid formatting
	 * @param email the email to check
	 * @return true iff email is correctly formatted
	 */
	public static boolean checkValidEmail(String email) {
		return email.matches("[A-Za-z0-9]+@([A-Za-z]+\\.)+[A-Za-z]+");
	}

}
