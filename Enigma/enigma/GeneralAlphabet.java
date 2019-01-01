package enigma;

import static enigma.EnigmaException.error;

/** A General Alphabet.
 *  @author Cassidy
 */
class GeneralAlphabet extends Alphabet {
    /**
     * A char[] for an alphabet.
     * @param letters an input string.
     */
    void characterarray(String letters) {
        newalphabet = new char[letters.length()];
        for (int i = 0; i < letters.length(); i++) {
            newalphabet[i] = letters.charAt(i);
        }
    }

    /**
     * Returns the size of the alphabet.
     */
    @Override
    int size() {
        return newalphabet.length;
    }

    /**
     * Returns true if preprocess(CH) is in this alphabet.
     */
    @Override
    boolean contains(char ch) {
        for (int i = 0; i < newalphabet.length; i++) {
            if (ch == newalphabet[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns character number INDEX in the alphabet, where
     * 0 <= INDEX < size().
     */

    @Override
    char toChar(int index) {
        if (index < 0 || index >= newalphabet.length) {
            throw error("character index out of range");
        }
        return newalphabet[index];
    }

    /**
     * Returns the index of character preprocess(CH), which must be in
     * the alphabet. This is the inverse of toChar().
     */

    @Override
    int toInt(char ch) {
        if (!contains(ch)) {
            throw error("character out of range");
        }
        int index = -1;
        for (int i = 0; i < newalphabet.length; i++) {
            index += 1;
            if (ch == newalphabet[i]) {
                break;
            }
        }
        return index;
    }

    /**
     * A char[] for an alphabet.
     */
    private char[] newalphabet;
}
