package enigma;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import static enigma.TestUtils.*;
public class DoubleStepTest {

    @Test
    public void testDoubleStep() {
        Alphabet ac = new CharacterRange('A', 'D');
        Rotor one = new Reflector("R1", new Permutation("(AC) (BD)", ac));
        Rotor two = new MovingRotor("R2", new Permutation("(ABCD)", ac), "C");
        Rotor three = new MovingRotor("R3", new Permutation("(ABCD)", ac), "C");
        Rotor four = new MovingRotor("R4", new Permutation("(ABCD)", ac), "C");
        String setting = "AAA";
        Rotor[] machineRotors = {one, two, three, four};
        String[] rotors = {"R1", "R2", "R3", "R4"};
        Machine mach = new Machine(ac, 4, 3,
                new ArrayList<>(Arrays.asList(machineRotors)));
        mach.insertRotors(rotors);
        mach.setRotors(setting);
        Permutation plugs = new Permutation(
                "(A)(B)(C)(D)(E)(F)(G)(H)(I)(J)(K)(L)"
                + "(M)(N)(O)(P)(Q)(R)(S)(T)(U)(V)(W)(X)(Y)(Z)", ac);
        mach.setPlugboard(plugs);

        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(3).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(2).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(1).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(0).setting()));
        mach.convert(0);
        assertEquals('B', UPPER.toChar(
                mach.getmyorderedrotors().get(3).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(2).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(1).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(0).setting()));
        mach.convert(0);
        mach.convert(0);
        assertEquals('D', UPPER.toChar(
                mach.getmyorderedrotors().get(3).setting()));
        assertEquals('B', UPPER.toChar(
                mach.getmyorderedrotors().get(2).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(1).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(0).setting()));
        mach.convert(0);
        mach.convert(0);
        assertEquals('B', UPPER.toChar(
                mach.getmyorderedrotors().get(3).setting()));
        assertEquals('B', UPPER.toChar(
                mach.getmyorderedrotors().get(2).setting()));
        assertEquals('A', UPPER.toChar(
                mach.getmyorderedrotors().get(1).setting()));
    }
}
