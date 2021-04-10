public class RabinKarpSearchAlgorithm {
    // d is the number of characters in the input alphabet
    private final int d = 256;

    /* pattern -> pattern
            text -> text
            prime -> A prime number
        */
    public boolean search(String pattern, String text, int prime) {
        int patternLength = pattern.length();
        int textLength = text.length();
        int i, j;
        int patternHash = 0; // hash value for pattern
        int textHash = 0; // hash value for text
        int h = 1;
        int found = 0;

        // The value of h would be "pow(d, patternLength-1)%prime"
        for (i = 0; i < patternLength - 1; i++) {
            h = (h * d) % prime;
        }

        // Calculate the hash value of pattern and first
        // window of text
        for (i = 0; i < patternLength; i++) {
            patternHash = (d * patternHash + pattern.charAt(i)) % prime;
            textHash = (d * textHash + text.charAt(i)) % prime;
        }

        // Slide the pattern over text one by one
        for (i = 0; i <= textLength - patternLength; i++) {

            // Check the hash values of current window of text
            // and pattern. If the hash values match then only
            // check for characters on by one
            if (patternHash == textHash) {
                /* Check for characters one by one */
                for (j = 0; j < patternLength; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }

                // if patternHash == textHash and pattern[0...patternLength-1] = text[i, i+1, ...i+patternLength-1]
                if (j == patternLength) {
                    System.out.println("Pattern found at index " + i);
                    found++;
                }
            }

            // Calculate hash value for next window of text: Remove
            // leading digit, add trailing digit
            if (i < textLength - patternLength) {
                textHash = (d * (textHash - text.charAt(i) * h) + text.charAt(i + patternLength)) % prime;

                // We might get negative value of textHash, converting it
                // to positive
                if (textHash < 0) {
                    textHash = (textHash + prime);
                }
            }
        }
        return found > 0;
    }
}
