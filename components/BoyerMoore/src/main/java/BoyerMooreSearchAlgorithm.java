public class BoyerMooreSearchAlgorithm {
    private final int NO_OF_CHARS = 256;

    //The preprocessing function for Boyer Moore's
    //bad character heuristic
    public void badCharHeuristic(char[] str, int size, int[] badChar) {
        // Initialize all occurrences as -1
        for (int i = 0; i < NO_OF_CHARS; i++) {
            badChar[i] = -1;
        }

        // Fill the actual value of last occurrence
        // of a character
        for (int i = 0; i < size; i++) {
            badChar[str[i]] = i;
        }
    }

    /* A pattern searching function that uses Bad
        Character Heuristic of Boyer Moore Algorithm */
    public boolean search(char[] pattern, char[] text) {
        int patternLength = pattern.length;
        int textLength = text.length;
        boolean found = false;

        int[] badChar = new int[NO_OF_CHARS];

      /* Fill the bad character array by calling
         the preprocessing function badCharHeuristic()
         for given pattern */
        badCharHeuristic(pattern, patternLength, badChar);

        int s = 0;  // s is shift of the pattern with
        // respect to text
        while (s <= (textLength - patternLength)) {
            int j = patternLength - 1;

          /* Keep reducing index j of pattern while
             characters of pattern and text are
             matching at this shift s */
            while (j >= 0 && pattern[j] == text[s + j]) {
                j--;
            }

          /* If the pattern is present at current
             shift, then index j will become -1 after
             the above loop */
            if (j < 0) {
                found = true;
              /* Shift the pattern so that the next
                 character in text aligns with the last
                 occurrence of it in pattern.
                 The condition s+patternLength < textLength is necessary for
                 the case when pattern occurs at the end
                 of text */
                s += (s + patternLength < textLength) ? patternLength - badChar[text[s + patternLength]] : 1;

            } else {
              /* Shift the pattern so that the bad character
                 in text aligns with the last occurrence of
                 it in pattern. The max function is used to
                 make sure that we get a positive shift.
                 We may get a negative shift if the last
                 occurrence  of bad character in pattern
                 is on the right side of the current
                 character. */
                s += Math.max(1, j - badChar[text[s + j]]);
            }
        }
        return found;
    }
}
