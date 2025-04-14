package org.wordz;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.assertj.core.api.Assertions.assertThat;

public class WordzTest {

    // Scénario 2: Lettre correcte, position correcte
    @Test
    public void oneCorrectLetter() {
        // Arrange
        var wordz = new Wordz("A"); // Target word
        
        // Act
        Score score = wordz.scoreGuess("A"); // Guess
        
        // Assert
        // Check the score for the first letter (index 0)
        assertThat(score.letter(0)).isEqualTo(Letter.CORRECT);
    }

    // Scénario 1: Lettre incorrecte
    @Test
    public void oneIncorrectLetter() {
        // Arrange
        var wordz = new Wordz("A"); // Target word

        // Act
        Score score = wordz.scoreGuess("Z"); // Guess

        // Assert
        assertThat(score.letter(0)).isEqualTo(Letter.INCORRECT);
    }

    // Scénario 3: Lettre correcte, mauvaise position
    @Test
    public void onePartCorrectLetter() {
        // Arrange
        var wordz = new Wordz("AR"); // Target word: AR

        // Act
        Score score = wordz.scoreGuess("ZA"); // Guess: ZA

        // Assert
        // 'Z' is incorrect
        assertThat(score.letter(0)).isEqualTo(Letter.INCORRECT);
        // 'A' is in the word but wrong position
        assertThat(score.letter(1)).isEqualTo(Letter.PART_CORRECT);
    }

    // Scénario 4: Test paramétré depuis CSV
    @ParameterizedTest
    @CsvFileSource(resources = "/test-data.csv", numLinesToSkip = 1) // Skip header line
    void scoreParameterized(String targetWord, String guess, String expectedScoreString) {
        // Arrange
        var wordz = new Wordz(targetWord);

        // Act
        Score score = wordz.scoreGuess(guess);

        // Assert
        assertThat(score.getSize()).isEqualTo(expectedScoreString.length());
        for (int i = 0; i < expectedScoreString.length(); i++) {
            Letter expectedLetter;
            switch (expectedScoreString.charAt(i)) {
                case 'C':
                    expectedLetter = Letter.CORRECT;
                    break;
                case 'P':
                    expectedLetter = Letter.PART_CORRECT;
                    break;
                case 'I':
                    expectedLetter = Letter.INCORRECT;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid expected score character: " + expectedScoreString.charAt(i));
            }
            assertThat(score.letter(i))
                    .as("Comparing letter at index %d for target '%s', guess '%s'", i, targetWord, guess)
                    .isEqualTo(expectedLetter);
        }
    }
} 