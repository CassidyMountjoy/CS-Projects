package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/** Enigma simulator.
 *  @author Cassidy
 */
public final class Main {

    /** Process a sequence of encryptions and decryptions, as
     *  specified by ARGS, where 1 <= ARGS.length <= 3.
     *  ARGS[0] is the name of a configuration file.
     *  ARGS[1] is optional; when present, it names an input file
     *  containing messages.  Otherwise, input comes from the standard
     *  input.  ARGS[2] is optional; when present, it names an output
     *  file for processed messages.  Otherwise, output goes to the
     *  standard output. Exits normally if there are no errors in the input;
     *  otherwise with code 1. */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /** Check ARGS and open the necessary files (see comment on main). */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }
        _config = getInput(args[0]);
        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /** Return a Scanner reading from the file named NAME. */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Return a PrintStream writing to the file named NAME. */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /** Configure an Enigma _machine from the contents of configuration
     *  file _config and apply it to the messages in _input, sending the
     *  results to _output. */
    private void process() {
        String message;
        firstline = _input.nextLine();
        String line = firstline;
        if (line.charAt(0) == '*') {
            setUp(readConfig(), line);
        }
        if (line.charAt(0) != '*') {
            throw error("Message without configuration");
        }
        while (_input.hasNextLine()) {
            message = "";
            line = _input.nextLine();
            if (!line.contains("*")) {
                for (int j = 0; j < line.length(); j++) {
                    line = line.toUpperCase();
                    if (line.charAt(j) != ' ') {
                        message += _alph.toChar(
                                _machine.convert(
                                        _alph.toInt(line.charAt(j))));
                    }
                }
                _output.println(messageline(message));
            } else {
                setUp(_machine, line);
            }
        }

    }

    /** Return an Enigma _machine configured from the contents of configuration
     *  file _config. */
    private Machine readConfig() {
        try {
            String line = _config.nextLine();
            int numrotors = _config.nextInt();
            int movingrotors = _config.nextInt();
            if (line.length() == 3) {
                _alph = new CharacterRange(line.charAt(0), line.charAt(2));
            } else {
                _alph = new GeneralAlphabet();
                ((GeneralAlphabet) _alph).characterarray(line);
            }
            while (_config.hasNext()) {
                allrotors.add(readRotor());
            }
            _machine = new Machine(
                    _alph, numrotors, movingrotors, allrotors);
            return _machine;
        } catch (NoSuchElementException excp) {
            throw error("configuration file truncated");
        }
    }

    /** Return a rotor, reading its description from _config. */
    private Rotor readRotor() {
        try {
            boolean moving = false;
            boolean fixed = false;
            boolean reflect = false;
            String name = _config.next();
            String perm = "";
            String rotor = _config.next();
            String notches = "";
            Rotor p;
            if (rotor.charAt(0) == 'M') {
                moving = true;
            }
            if (rotor.charAt(0) == 'N') {
                fixed = true;
            }
            if (rotor.charAt(0) == 'R') {
                reflect = true;
            }
            for (int i = 1; i < rotor.length(); i++) {
                notches += rotor.charAt(i);
            }
            while (_config.hasNext()) {
                perm += _config.next() + "";
                if (!_config.hasNext("[(][^()-]*[)]")) {
                    break;
                }
            }
            Permutation permute = new Permutation(perm, _alph);
            if (moving) {
                return new MovingRotor(name, permute, notches);
            }
            if (fixed) {
                return new FixedRotor(name, permute);
            } else {
                return new Reflector(name, permute);
            }
        } catch (NoSuchElementException excp) {
            throw error("bad rotor description");
        }
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void setUp(Machine M, String settings) {
        Scanner s = new Scanner(settings);
        String set = "";
        String[] rotors = new String[M.numRotors()];
        String plugos = "";
        int index = 0;
        String p;
        boolean isname;
        try {
            while (s.hasNext()) {
                isname = false;
                p = s.next();
                for (Rotor i : allrotors) {
                    if (p.equals(i.name().toUpperCase())) {
                        rotors[index] = i.name();
                        index += 1;
                        isname = true;
                        break;
                    }
                }
                if (p.charAt(0) == '(') {
                    plugos += p;
                }
                if (!isname && !p.equals("*")) {
                    set += p;
                }
            }
        } catch (ArrayIndexOutOfBoundsException excp) {
            throw error("Wrong number of rotors");
        }
        if (M.getmyorderedrotors().size() != 0) {
            M.clearrotors();
        }
        M.insertRotors(rotors);
        int i = 0;
        String reduced = "";
        while (set.charAt(i) != '(') {
            reduced += set.charAt(i);
            if (i == set.length() - 1) {
                break;
            }
            i += 1;
        }
        this.setting = reduced;
        if (reduced.length() != _machine.numRotors() - 1) {
            throw error("Settings wrong length");
        }
        M.setRotors(reduced);
        Permutation plugs = new Permutation(
                "(A)(B)(C)(D)(E)(F)(G)(H)(I)(J)(K)(L)"
                        + "(M)(N)(O)(P)(Q)(R)(S)(T)(U)(V)(W)(X)(Y)(Z)", _alph);
        if (plugos != null) {
            plugs = new Permutation(plugos, _alph);
        }
        M.setPlugboard(plugs);
        test(M);
    }

    /** Set M according to the specification given on SETTINGS,
     *  which must have the format specified in the assignment. */
    private void test(Machine M) {
        int reflect = 0;
        int moving = 0;
        int fixed = 0;
        for (Rotor elem: M.getmyorderedrotors()) {
            if (elem.reflecting()) {
                reflect += 1;
            }
            if (elem.rotates()) {
                moving += 1;
            } else {
                fixed += 1;
            }
        }
        if (reflect != 1 || moving != M.numPawls()
                || fixed != (M.numRotors() - M.numPawls())) {
            throw error("Wrong Args");
        }
    }

    /** Return string MSG in groups of five (except that the last group may
     *  have fewer letters). */
    private String messageline(String msg) {
        int printed = 0;
        String output = "";
        for (printed = 0; printed < msg.length(); printed += 1) {
            if (printed == 0) {
                output += msg.charAt(printed);
            }
            if (printed % 5 != 0) {
                output += msg.charAt(printed);
            }
            if (printed != 0 && printed % 5 == 0) {
                output += " ";
                output += msg.charAt(printed);
            }
        }
        return output;
    }

    /** Alphabet used in this _machine. */
    private Alphabet _alph;

    /** Source of input messages. */
    private Scanner _input;

    /** Source of _machine configuration. */
    private Scanner _config;

    /** File for encoded/decoded messages. */
    private PrintStream _output;

    /** My _machine. */
    private Machine _machine;

    /** Config file. */
    private String setting;

    /** All my rotors. */
    private ArrayList<Rotor> allrotors = new ArrayList<>();

    /** First line of input. */
    private String firstline;

}
