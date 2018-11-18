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
		input = input.replace("\\", "-").replace("/", "-").replaceAll(" ", "-");
		
		// invalid / unsupported string input
		if(input.length() != 10) {
			throw new InvalidValueException();
		}
		
		int first;
		int second;
		
		
		// if YYYY-XX-XX, check which is mm and which is dd
		if(input.matches("([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))")) {
			
			// check if first xx is dd ( > 12)
			first = Integer.parseInt(input.substring(5, 7));
			second = Integer.parseInt(input.substring(8, 10));
			
			//if both mm and dd above 12, throw error
			if(first > 12 && second > 12) {
				throw new InvalidValueException();
			}
			// if YYYY-DD-MM, fix
			else if(first > 12) {
				return input.substring(0, 4) + "-" + input.substring(8, 10) + "-" + input.substring(5, 7);
			}
			// otherwise it's formatted correct, so return it
			return input;
		} 
		// if XX-XX-YYYY, check which is mm and which is dd
		else if(input.matches("^((0|1)\\d{1})-((0|1|2)\\d{1})-((19|20)\\d{2})")) {
			
			// check if first xx is dd ( > 12)
			first = Integer.parseInt(input.substring(0, 2));
			second = Integer.parseInt(input.substring(3, 5));
			
			//if both mm and dd above 12, throw error
			if(first > 12 && second > 12) {
				throw new InvalidValueException();
			}
			// if DD-MM-YYYY, fix
			else if(first > 12) {
				return input.substring(6, 10) + "-" + input.substring(3, 5) + "-" + input.substring(0, 2);
			}
			// otherwise it's MM-DD-YYYY, fix
			return input.substring(6 ,10) + "-" + input.substring(0, 2) + "-" + input.substring(3, 5);
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
		if(email.matches("[A-Za-z0-9]+@([A-Za-z]+\\.)+[A-Za-z]+")) {
			return true;
		} else {
			return false;
		}
	}

}
