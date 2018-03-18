import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;

import org.junit.Test;

public class PalindromesTest {
	final Palindromes instance = new Palindromes();
	
	@Test
	public void testIsPalindromeSimple() {
		assertTrue(instance.isPalindrome("racecar"));
		assertTrue(instance.isPalindrome("noon"));
		assertTrue(instance.isPalindrome("radar"));
		assertTrue(instance.isPalindrome("level"));
		assertTrue(instance.isPalindrome("kayak"));
		assertFalse(instance.isPalindrome("tweet"));
	}
	
	@Test
	public void testIsPalindromeMixedCase() {
		assertTrue(instance.isPalindrome("Racecar"));
		assertTrue(instance.isPalindrome("Noon"));
		assertTrue(instance.isPalindrome("Radar"));
		assertTrue(instance.isPalindrome("Level"));
		assertTrue(instance.isPalindrome("Kayak"));
		assertFalse(instance.isPalindrome("Tweet"));
	}
	
	@Test
	public void testIsPalindromeMultipleWords() {
		assertTrue(instance.isPalindrome("evil olive"));
	}
	
	@Test
	public void testIsPalindromeSentence1() {
		assertTrue(instance.isPalindrome("A Man, A Plan, A Canal, Panama!"));
	}
	
	@Test
	public void testIsPalindromeSentence2() {
		assertFalse(instance.isPalindrome("semordnilaps or palindromes"));
	}
	
	@Test
	public void testIsPalindromeFromDictionaryWords() throws Exception {
		// Set up
		final MessageDigest md5 = MessageDigest.getInstance("md5");
		final URL url = new URL(
			"https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt"
		);
		final BufferedReader reader = new BufferedReader(
			new InputStreamReader(url.openStream())
		);
		
		// Test
		reader.lines().
		filter(instance::isPalindrome).
		forEach(line -> md5.update(line.getBytes()));
		final byte[] actualMd5 = md5.digest();
		
		// Verify
		final byte[] expectedMd5 = new byte[] {
			74, -42, 100, -109, -98, -49, -41, 17, 90, 114, -66, 51, 36, -54, 34, -80
		};
		assertTrue(Arrays.equals(expectedMd5, actualMd5));
	}
}
