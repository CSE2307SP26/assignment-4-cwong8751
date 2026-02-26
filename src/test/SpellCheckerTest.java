package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class SpellCheckerTest {

	SpellChecker sc;

	@Before
	void setup() {
		sc = new SpellChecker();
	}

	// A spellchecker should be able to tell us how many words it currently knows
	// about
	@Test
	void testGetNumberOfWords() {
		int words = sc.getNumberOfWords();
		assertEquals(0, words);
	}

	@Test
	void testNewWordAdded() {
		// The number of words a spellchecker knows about should go up by one whenever a
		// new word is added
		int words1 = sc.getNumberOfWords("a fox jumped over a dog");
		int words2 = sc.getNumberOfWords("a fox jumped over a dog red");
		int diff = words2 - words1;
		assertEquals(1, diff);
	}

	@Test
	void testSameWordAdded() {
		// If a word is added that is already contained in the spellchecker, then the
		// number of words contained in the spellchecker should not change
		int words1 = sc.getNumberOfWords("a fox jumped over a dog");
		int words2 = sc.getNumberOfWords("a fox jumped over a dog");
		assertEquals(words1, words2);
	}

	@Test
	void checkProperlySpelledWord() {
		String[] dict = { "apple", "banana", "orange" };
		sc.setDict(dict);

		boolean result1 = sc.check("apple");
		assertTrue(result1);

		sc.clearDict();
	}

	@Test
	void checkImproperlySpelledWord() {
		String[] dict = { "apple", "banana", "orange" };
		sc.setDict(dict);

		boolean result1 = sc.check("grape");
		assertFalse(result1);
		sc.clearDict();
	}

	@Test
	void checkCaseInsensitive() {
		String[] dict = { "apple", "banana", "orange" };
		sc.setDict(dict);

		boolean result1 = sc.check("APPLE");
		assertTrue(result1);

		boolean result2 = sc.check("bAnAnA");
		assertTrue(result2);

		sc.clearDict();
	}

	@Test
	void checkRecommendation() {
		String[] nDict = { "a", "fox", "jumped", "over", "dog", "in", "bank" };
		sc.setDict(nDict);

		String results5 = sc.getRecommendation("bamk");

		String expected = "bank";
		assertEquals(expected, results5);

		sc.clearDict();
	}

	@Test
	void checkRecommendationNone() {
		String[] nDict = { "a", "fox", "jumped", "over", "dog", "in", "bank" };
		sc.setDict(nDict);

		String results5 = sc.getRecommendation("bank");

		String expected = "bank";
		assertEquals(expected, results5);

		sc.clearDict();
	}

	// this feature allows the spellchecker to replace certain words with the same words in another language
	@Test
	void checkTranslation() {
		String [] ptDict = {"bank", "banco"};
		sc.setTranslationDict(ptDict);

		String result = sc.getTranslation("bank");
		String expected = "banco";
		assertEquals(expected, result);

		sc.clearTranslationDict();
	}
	
	// this feature allows the user to replace a certain word with another word, convenient when they want to not use a certain word in their writing 
	@Test
	void checkReplacement() {
		sc.addReplacement("apple", "opple");

		String result = sc.getReplacement("there are 3 green apple on the table");
		String expected = "there are 3 green opple on the table";
		assertEquals(expected, result);

		sc.clearReplacements();
	}
}
