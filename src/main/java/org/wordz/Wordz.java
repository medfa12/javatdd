package org.wordz;

import java.util.ArrayList;
import java.util.List;

public class Wordz {
    private final String word;

    public Wordz(String word) {
        this.word = word;
    }

    public Score scoreGuess(String guess) {
        Score score = new Score(word);
        List<Letter> results = new ArrayList<>();
        boolean[] wordLetterUsed = new boolean[word.length()]; // Track used letters in the target word
        boolean[] guessLetterScored = new boolean[guess.length()]; // Track scored letters in the guess

        // 1. First pass: Check for CORRECT letters (right letter, right position)
        for (int i = 0; i < guess.length(); i++) {
            results.add(Letter.INCORRECT); // Initialize with INCORRECT
            if (i < word.length() && guess.charAt(i) == word.charAt(i)) {
                results.set(i, Letter.CORRECT);
                wordLetterUsed[i] = true;
                guessLetterScored[i] = true;
            }
        }

        // 2. Second pass: Check for PART_CORRECT letters (right letter, wrong position)
        for (int i = 0; i < guess.length(); i++) {
            if (!guessLetterScored[i]) { // Only check letters not already marked CORRECT
                for (int j = 0; j < word.length(); j++) {
                    if (!wordLetterUsed[j] && i < word.length() && guess.charAt(i) == word.charAt(j)) {
                        results.set(i, Letter.PART_CORRECT);
                        wordLetterUsed[j] = true; // Mark this target letter as used
                        guessLetterScored[i] = true;
                        break; // Move to the next guess letter
                    }
                }
            }
        }

        // Add results to the Score object
        for (Letter result : results) {
            score.add(result);
        }

        return score;
    }
} 