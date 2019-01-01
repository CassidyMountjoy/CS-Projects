package enigma;


/** Superclass that represents a rotor in the enigma machine.
 *  @author Cassidy
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _myposn = 0;
        _rightposn = 0;
        _slot = 0;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return _permutation.size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return _setting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        _setting = posn;
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        set(_permutation.alphabet().toInt(cposn));
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        int intocontact = (p + _setting) % size();
        int throughperm = _permutation.alphabet().toInt(_permutation.permute(
                _permutation.alphabet().toChar(intocontact)));
        int outcontact = (throughperm - _setting) % size();
        int correctindex = (outcontact + size()) % size();
        return correctindex;
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int intocontact = (e + _setting) % size();
        int throughperm = _permutation.alphabet().toInt(_permutation.invert(
                _permutation.alphabet().toChar(intocontact)));
        int outcontact = (throughperm - _setting) % size();
        int correctindex = (outcontact + size()) % size();
        return correctindex;
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
        if (rotates()) {
            _setting += 1;
            _setting = _setting % size();
        }
    }

    /** Sets the setting.
     * @param x set to an int.
     *  to advance. */
    void setsetting(int x) {
        _setting = x;
    }


    @Override
    public String toString() {
        return "Rotor " + _name;
    }


    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;
    /** This permutation at its position. */
    private int _myposn;
    /** The permutations notch. */
    private char[] _mynotches;
    /** The permutations notch. */
    private char[] _rightnotches;
    /** The right permutation at its position. */
    private int _rightposn;
    /** The setting of the rotor. */
    private int _setting;
    /** The permutations notch. */
    private int _slot;

}
