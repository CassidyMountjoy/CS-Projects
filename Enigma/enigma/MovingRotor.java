package enigma;
/** Class that represents a rotating rotor in the enigma machine.
 *  @author Cassidy
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _mynotches = notches.toCharArray();
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < _mynotches.length; i++) {
            if (permutation().alphabet().toInt(_mynotches[i]) == setting()) {
                return true;
            }
        }
        return false;
    }
    @Override
    boolean rotates() {
        return true;
    }
    @Override
    void advance() {
        if (setting() == alphabet().size() - 1) {
            set(0);
        } else {
            set(setting() + 1);
        }
        set(setting() % size());
    }
    /** The permutations notch. */
    private char[] _mynotches;
    /** The right permutation at its position. */
}
