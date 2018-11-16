package application.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	@DisplayName("format regular canadian phone number")
	void testRegCanPhoneNum() {
		String regCanPhoneNum = "416-123-4567";
		try {
			String formatted = Formatter.formatPhoneNumber(regCanPhoneNum);
			assertEquals("(416) 123-4567", formatted);
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("format odd phone number")
	void testOddPhoneNum() {
		String oddPhoneNum = "(905)        -234-(56)-78";
		try {
			String formatted = Formatter.formatPhoneNumber(oddPhoneNum);
			assertEquals("(905) 234-5678", formatted);
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@DisplayName("format invalid phone number with < 10 digits")
	void testLessDigPhoneNum() {
		String invalidPhoneNum = "416123456";
		assertThrows(InvalidValueException.class, ()->Formatter.formatPostalCode(invalidPhoneNum));
	}
	
	@Test
	@DisplayName("format invalid phone number with > 10 digits")
	void testMoreDigPhoneNum() {
		String invalidPhoneNum = "41612345678";
		assertThrows(InvalidValueException.class, ()->Formatter.formatPostalCode(invalidPhoneNum));
	}
	
	@Test
	@DisplayName("format invalid phone number with alpha char")
	void testAlphaPhoneNum() {
		String invalidPhoneNum = "abc-123-4567";
		assertThrows(InvalidValueException.class, ()->Formatter.formatPostalCode(invalidPhoneNum));
	}
	
	@Test
	@DisplayName("format invalid phone number with non-alphanumeric char")
	void testNonNumericPhoneNum() {
		String invalidPhoneNum = "!@#-$%^-&*12";
		assertThrows(InvalidValueException.class, ()->Formatter.formatPostalCode(invalidPhoneNum));
	}
	
	@Test
	@DisplayName("test valid email")
	void testValidEmail() {
		assertTrue(Formatter.checkValidEmail("test@gmail.com"));
	}
	
	@Test
	@DisplayName("test valid email with numerics and multiple periods")
	void testValidEmailWithNumerics() {
		assertTrue(Formatter.checkValidEmail("test123@mail.utoronto.ca"));
	}
	
	@Test
	@DisplayName("test invalid email starting with special char")
	void testInvalidEmail() {
		assertFalse(Formatter.checkValidEmail("@asdf@bcd.com"));
	}
	
	@Test
	@DisplayName("test invalid email with no email domain")
	void testInvalidEmailNoDomain() {
		assertFalse(Formatter.checkValidEmail("test123.com"));
	}
	
	@Test
	@DisplayName("test invalid email with no .com")
	void testInvalidEmailNoDotCom() {
		assertFalse(Formatter.checkValidEmail("test123@asdf"));
	}
	
	@Test
	@DisplayName("test invalid email with bad format")
	void testBadFormat() {
		assertFalse(Formatter.checkValidEmail("Team28@.com"));
	}
}
