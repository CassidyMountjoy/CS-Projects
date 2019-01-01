package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import static enigma.TestUtils.*;

public class MachineTest {
    private ArrayList<Rotor> allrotors = new ArrayList<>();
    private MovingRotor rotorI = new MovingRotor("I",
            new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)",
                    UPPER), "Q");
    private MovingRotor rotorII = new MovingRotor("II",
            new Permutation("(FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)",
                    UPPER), "E");
    private MovingRotor rotorIII = new MovingRotor("III",
            new Permutation("(ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)",
                    UPPER), "V");
    private MovingRotor rotorIV = new MovingRotor("IV",
            new Permutation("(AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)",
                    UPPER), "J");
    private MovingRotor rotorV = new MovingRotor("V",
            new Permutation("(AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)",
                    UPPER), "Z");
    private MovingRotor rotorVI = new MovingRotor("VI",
            new Permutation("(AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)",
                    UPPER), "ZM");
    private MovingRotor rotorVII = new MovingRotor("VII",
            new Permutation("(ANOUPFRIMBZTLWKSVEGCJYDHXQ)",
                    UPPER), "ZM");
    private MovingRotor rotorVIII = new MovingRotor("VIII",
            new Permutation("(AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)",
                    UPPER), "ZM");
    private FixedRotor rotorBeta = new FixedRotor("Beta",
            new Permutation("(ALBEVFCYODJWUGNMQTZSKPR) (HIX)",
                    UPPER));
    private FixedRotor rotorGamma = new FixedRotor("Gamma",
            new Permutation("(AFNIRLBSQWVXGUZDKMTPCOYJHE)",
                    UPPER));
    private Reflector reflectorB = new Reflector("B",
            new Permutation("(AE) (BN) (CK) (DQ) (FU)"
                    + " (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)", UPPER));
    private Reflector reflectorC = new Reflector("C",
            new Permutation("(AR) (BD) (CO) (EJ) (FN)"
                    + " (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)", UPPER));
    private Permutation plugboard = new Permutation("(YF) (ZH)",
            UPPER);
    private String[] names = new String[5];


    @Test
    public void convert() {
        allrotors.add(rotorI);
        allrotors.add(rotorII);
        allrotors.add(rotorIII);
        allrotors.add(rotorIV);
        allrotors.add(rotorV);
        allrotors.add(rotorVI);
        allrotors.add(rotorVII);
        allrotors.add(rotorVIII);
        allrotors.add(rotorBeta);
        allrotors.add(rotorGamma);
        allrotors.add(reflectorB);
        allrotors.add(reflectorC);
        names[0] = "B";
        names[1] = "Beta";
        names[2] = "III";
        names[3] = "IV";
        names[4] = "I";
        Machine navalmachine = new Machine(UPPER, 5, 3, allrotors);
        navalmachine.setPlugboard(plugboard);
        navalmachine.insertRotors(names);
        String setting = "AXLE";
        navalmachine.setRotors(setting);
        assertEquals(25, navalmachine.convert(24));
        for (int i = 0; i < 11; i++) {
            navalmachine.convert(i);
            navalmachine.getmyorderedrotors().get(4).setting();
        }
        assertEquals('Q',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(4).setting()));
        navalmachine.convert(0);
        System.out.println(
                navalmachine.getmyorderedrotors().get(4).setting());
        assertEquals('R',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(4).setting()));
        assertEquals('M',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(3).setting()));
        assertEquals('X',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(2).setting()));
        assertEquals('A',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(1).setting()));
        for (int i = 0; i < 597; i++) {
            navalmachine.convert(0);
        }
        assertEquals('Q',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(4).setting()));
        assertEquals('I',
                UPPER.toChar(
                        navalmachine.getmyorderedrotors().get(3).setting()));

    }
}
