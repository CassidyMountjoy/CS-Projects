package enigma;

import java.util.ArrayList;
import java.util.Collection;
import static enigma.EnigmaException.*;
import java.util.Set;
import java.util.HashSet;
/** Class that represents a complete enigma machine.
 *  @author Cassidy
 */
class Machine {

    /** A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     *  and 0 <= PAWLS < NUMROTORS pawls.  ALLROTORS contains all the
     *  available rotors.*/
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {
        _alphabet = alpha;
        _numRotors = numRotors;
        _pawls = pawls;
        _fixedrotors = _numRotors - pawls;
        _allRotors = allRotors;
        _myorderedrotors = new ArrayList<>();
    }

    /** Return the number of rotor slots I have. */
    int numRotors() {
        return _numRotors;
    }

    /** Return the number pawls (and thus rotating rotors) I have. */
    int numPawls() {
        return _pawls;
    }

    /** Set my rotor slots to the rotors named ROTORS from my set of
     *  available rotors (ROTORS[0] names the reflector).
     *  Initially, all rotors are set at their 0 setting. */
    void insertRotors(String[] rotors) {
        try {
            for (int i = 0; i < rotors.length; i++) {
                rotors[i] = rotors[i].toUpperCase();
            }
        } catch (NullPointerException excp) {
            throw error("Wrong number of rotors");
        }
        for (String i: rotors) {
            for (Rotor elem : _allRotors) {
                if (elem.name().toUpperCase().equals(i)) {
                    _myorderedrotors.add(elem);
                }
            }
        }
        Set<Rotor> set = new HashSet<>(_myorderedrotors);
        if (set.size() != _myorderedrotors.size()) {
            throw error("Repeated rotor");
        }
        if (!_myorderedrotors.get(0).reflecting()) {
            throw error("First rotor isn't a reflector");
        }
        if (numRotors() != _myorderedrotors.size()) {
            throw error("Wrong number of rotors");
        }
    }

    /** Set my rotors according to SETTING, which must be a string of
     *  numRotors()-1 upper-case letters. The first letter refers to the
     *  leftmost rotor setting (not counting the reflector).  */
    void setRotors(String setting) {
        char[] settingchar = setting.toCharArray();
        for (int i = 1; i < _myorderedrotors.size(); i++) {
            _myorderedrotors.get(i).setsetting(
                    _alphabet.toInt(settingchar[i - 1]));
        }
    }

    /** Set the plugboard to PLUGBOARD. */
    void setPlugboard(Permutation plugboard) {
        _plugboard = plugboard;
    }

    /** Returns the result of converting the input character C (as an
     *  index in the range 0..alphabet size - 1), after first advancing

     *  the machine. */
    int convert(int c) {
        for (int i = 0; i < _myorderedrotors.size(); i++) {
            if (canmove(i) && _myorderedrotors.get(i).rotates()) {
                _myorderedrotors.get(i).advance();
                if (triggered && i < _myorderedrotors.size() - 2) {
                    _myorderedrotors.get(i + 1).advance();
                    triggered = false;
                }
            }
        }
        c = _plugboard.permute(c);
        for (int i = _myorderedrotors.size() - 1; i >= 0; i--) {
            c = _myorderedrotors.get(i).convertForward(c);
        }
        for (int i = 1; i < _myorderedrotors.size(); i++) {
            c = _myorderedrotors.get(i).convertBackward(c);
        }
        c = _plugboard.permute(c);
        return c;
    }

    /** Returns the encoding/decoding of MSG, updating the state of
     *  the rotors accordingly. */
    String convert(String msg) {
        char[] message = new char[msg.length()];
        for (int i = 0; i < msg.length(); i++) {
            message[i] = _alphabet.toChar(
                    convert(_alphabet.toInt(msg.charAt(i))));
        }
        return message.toString();
    }

    /** Returns my ordered
     *  the rotors accordingly. */
    ArrayList<Rotor> getmyorderedrotors() {
        return _myorderedrotors;
    }

    /** Clears the rotors. */
    void clearrotors() {
        ArrayList<Rotor> newlist = new ArrayList<>();
        _myorderedrotors = newlist;
    }

    /** Common alphabet of my rotors. */
    private final Alphabet _alphabet;
    /** Number of rotors. */
    private int _numRotors;
    /** Number of pawls / moving rotors. */
    private int _pawls;
    /** The number of fixed rotors. */
    private int _fixedrotors;
    /** The number of fixed rotors. */
    private Permutation _plugboard;
    /** A collection of all the possible rotors. */
    private Collection<Rotor> _allRotors;
    /** The number of fixed rotors. */
    private ArrayList<Rotor> _myorderedrotors;
    /** If its been triggered or not.
     * @return false
     * */
    private boolean triggered = false;

    /** True if the rotor to the right is at a notch
     * and therefore can move.
     * @param index is the location of rotor
     * @return boolean indicating if it can move
     * */
    Boolean canmove(int index) {
        if (index == _myorderedrotors.size() - 1) {
            return true;
        }
        if (_myorderedrotors.get(index + 1).atNotch()) {
            triggered = true;
            return true;
        }
        return false;
    }
}
