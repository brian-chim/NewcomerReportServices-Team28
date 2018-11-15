package application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class FormatterTest {
	
	@Test
	@DisplayName("format valid postal code")
	void testValidPostalCode() {
		String validPostalCode = "k2    m           5 T3   ";
		String result = "";
		try {
			result = Formatter.formatPostalCode(validPostalCode);
			assertEquals(result, "K2M 5T3");
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("throw exception for invalid postal code")
	void testInvalidalidPostalCode(){
		String invalidPostalCode = "224r 5tt5 ";
		assertThrows(InvalidValueException.class, ()->Formatter.formatPostalCode(invalidPostalCode));
	}


}
