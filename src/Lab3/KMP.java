package Lab3;

public class KMP {

    public static boolean KnuthMorrisPratt(String text, String pattern) {
        int textIterator = 0, patternIterator = 0;

        int[] longestPrefixSuffix = prefixFunction(pattern);

        while (textIterator < text.length()) {
            if (text.charAt(textIterator) != pattern.charAt(patternIterator)) {//we have to handle two cases when there is a mismatch
                if (patternIterator == 0) //start again from the next character in the text as we cant match the first character in the pattern
                    textIterator++;
                else //longestPrefixSuffix[i-1] will tell from where to compare next and no need to match for pattern[1..aux[i-1]], they will match anyway, that’s what kmp is about.
                    patternIterator = longestPrefixSuffix[patternIterator - 1];
            } else {
                textIterator++;
                patternIterator++;
                if (patternIterator == pattern.length()) { // we found the pattern in the text
                    return true;
                    //if we want to find more occurrences of the pattern, we can continue as if no match was found and update the patternIterator using the longestPrefixSuffix array.
                }
            }
        }
        return false;
    }


    public static int[] prefixFunction(String input) {
        int n = input.length();

        int[] longestPrefixSuffix = new int[n]; // longestPrefixSuffix[i] = length of the longest proper prefix that is also a suffix of the substring input[0,i]
        longestPrefixSuffix[0] = 0; //base case

        int i = 1; //iterates through the input
        int len = 0; //length of the longest proper prefix that is a suffix so far
        while (i < n) {
            if (input.charAt(i) == input.charAt(len)) { //if they match assign longestPrefixSuffix[i] = len +1 and increment len
                len++;
                longestPrefixSuffix[i] = len;
                i++;
            } else { //if they don't match try to match with the previous possible prefix
                if (len != 0)
                    len = longestPrefixSuffix[len - 1]; //Note that we dont increment i here
                else { //there was no any proper prefix found which is equal to the suffix for index i
                    longestPrefixSuffix[i] = 0;
                    i++;
                }
            }
        }
        return longestPrefixSuffix;
    }
}
