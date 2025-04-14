package org.wordz;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private final String correctWord;
    private final List<Letter> results = new ArrayList<>();

    public Score(String correctWord) {
        this.correctWord = correctWord;
    }

    public Letter letter(int position) {
        if (position < 0 || position >= results.size()) {
            // Handle invalid position, maybe throw an exception or return a default
            return Letter.INCORRECT; // Or throw IllegalArgumentException
        }
        return results.get(position);
    }

    public void add(Letter result) {
        results.add(result);
    }

    public int getSize() {
        return results.size();
    }

    // Override equals and hashCode for testing purposes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return java.util.Objects.equals(correctWord, score.correctWord) &&
               java.util.Objects.equals(results, score.results);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(correctWord, results);
    }

    // Optional: Override toString for better debugging/logging
    @Override
    public String toString() {
        return "Score{" +
               "correctWord='" + correctWord + '\'' +
               ", results=" + results +
               '}';
    }
} 