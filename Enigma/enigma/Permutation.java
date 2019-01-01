package enigma;


/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Cassidy
 */


class Permutation {

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;
        _lettercount = lettercounter(cycles.toCharArray());
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {
        _permutations.toString().concat(cycle);
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        return _alphabet.toInt(permute(_alphabet.toChar(wrap(p))));
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to  C modulo the alphabet size.
     */
    int invert(int c) {
        return _alphabet.toInt(invert(_alphabet.toChar(wrap(c))));
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        int x = _alphabet.toInt(p);
        if (_cycles.length() == 0 || !letterinperm(p)) {
            return _alphabet.toChar(x);
        }
        if (_alphabet.contains(_cycles.toCharArray()[_cycles.indexOf(p) + 1])) {
            x = _alphabet.toInt(_cycles.toCharArray()[_cycles.indexOf(p) + 1]);
        } else {
            for (int i = _cycles.indexOf(p); i >= 0; i--) {
                if (_cycles.toCharArray()[i] == '(') {
                    x = _alphabet.toInt(_cycles.toCharArray()[i + 1]);
                    break;
                }
            }
        }
        return _alphabet.toChar(x);
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        int x = _alphabet.toInt(c);
        int index = _cycles.indexOf(c);
        if (_cycles.length() == 0 || !letterinperm(c)) {
            return _alphabet.toChar(x);
        }
        if (_alphabet.contains(_cycles.toCharArray()[index - 1])) {
            x = _alphabet.toInt(_cycles.toCharArray()[index - 1]);
        } else {
            for (int i = _cycles.indexOf(c); i != _cycles.length(); i++) {
                if (_cycles.toCharArray()[i] == ')') {
                    x = _alphabet.toInt(_cycles.toCharArray()[i - 1]);
                    break;
                }
            }
        }
        return _alphabet.toChar(x);
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        for (int i = 0; i < _cycles.length() - 2; i++) {
            if (_cycles.toCharArray()[i] == '('
                    && _cycles.toCharArray()[i + 2] == ')') {
                return false;
            }
            if (_lettercount == _alphabet.size()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;
    /**
     * String of this permutation.
     */
    private char[][] _permutations;

    /**
     * the cycles as a string.
     */
    private String _cycles;
    /**
     * the number of letters in cycles.
     */
    private int _lettercount;
    /**
     * Return the number of letter in all cycles.
     * @param cycles is a char array of cycles.
     */
    int lettercounter(char[] cycles) {
        int num = 0;
        for (int i = 0; i < cycles.length; i++) {
            if (_alphabet.contains(cycles[i])) {
                num += 1;
            }
        }
        return num;
    }
    /**
     * Return the true if the letter is contained within the permutation.
     * @param p ia a char.
     */
    boolean letterinperm(char p) {
        for (int i = 0; i < _cycles.length(); i++) {
            if (_cycles.charAt(i) == p) {
                return true;
            }
        }
        return false;
    }
}
