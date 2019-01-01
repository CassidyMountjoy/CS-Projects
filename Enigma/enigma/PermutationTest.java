package enigma;

import org.junit.Test;
import static org.junit.Assert.*;
import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @Cassidy
 */
public class PermutationTest {

    /** Testing time limit. */

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */
    @Test
    public void permute() {
        perm = new Permutation("(AELTPHQXRU) (CMOY)"
                + " (DFG) (IV) (JZ) (S)", UPPER);
        assertEquals(perm.permute('U'), 'A');
        assertEquals(perm.permute('Z'), 'J');
        assertEquals(perm.permute('T'), 'P');
    }
    @Test
    public void invert() {
        char p = 'B';
        perm = new Permutation("(ABADEF) (ZA)", UPPER);
        assertEquals(perm.invert(p), 'A');
        assertEquals(perm.invert('P'), 'P');
        assertEquals(perm.invert('Z'), 'A');
        assertEquals(perm.invert('P'), 'P');
    }
    @Test
    public void lettercounter() {
        String s = "(ABFED) (QUZX)";
        char[] p = s.toCharArray();
        perm = new Permutation("(ABFED) (QUZX)", UPPER);
        assertEquals(perm.lettercounter(p), 9);
    }
    @Test
    public void derangement() {
        String s = "(ABCDEFGHIJKLMNOPQRSTUVWXY) (z)";
        perm = new Permutation(s, UPPER);
        assertEquals(perm.derangement(), false);
        String q = "(ABCDEFGHIJKLMNOPQRSTUVWXYZ)";
        Permutation perm1 = new Permutation(q, UPPER);
        assertEquals(perm1.derangement(), true);
    }
    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

}
