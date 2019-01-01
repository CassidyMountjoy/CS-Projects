package amazons;
import org.junit.Test;
import static org.junit.Assert.*;
import ucb.junit.textui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** Junit tests for our Board iterators.
 *  @author Cassidy
 */
public class IteratorTests {

    /** Run the JUnit tests in this package. */
    public static void main(String[] ignored) {
        textui.runClasses(IteratorTests.class);
    }

    /** Tests reachableFromIterator to make sure it returns all reachable
     *  Squares. This method may need to be changed based on
     *   your implementation. */
    @Test
    public void testReachableFrom() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numSquares = 0;
        Set<Square> squares = new HashSet<>();
        Iterator<Square> reachableFrom = b.reachableFrom(Square.sq(5, 4), null);
        while (reachableFrom.hasNext()) {
            Square s = reachableFrom.next();
            assertTrue(REACHABLEFROMTESTSQUARES.contains(s));
            numSquares += 1;
            squares.add(s);
        }
        assertEquals(REACHABLEFROMTESTSQUARES.size(), numSquares);
        assertEquals(REACHABLEFROMTESTSQUARES.size(), squares.size());
    }

    /** Tests legalMovesIterator to make sure it returns all legal Moves.
     *  This method needs to be finished and may need to be changed
     *  based on your implementation. */
    @Test
    public void testLegalMoves() {
        Board b = new Board();
        buildBoard(b, REACHABLEFROMTESTBOARD);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(_moves.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        assertEquals(52, numMoves);
        assertEquals(52, moves.size());
    }
    @Test
    public void testSIMPLELEGALlMoves() {
        Board b = new Board();
        buildBoard(b, SIMPLELEGAL);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            assertTrue(_simplemoves.contains(m));
            numMoves += 1;
            moves.add(m);
        }
        int x = moves.size();
        Move it;
        for (Move i: _simplemoves) {
            if (!moves.contains(i)) {
                it = i;
            }
        }
        assertEquals(20, numMoves);
        assertEquals(20, moves.size());
    }

    @Test
    public void teststartboard() {
        Board b = new Board();
        buildBoard(b, INITIALBOARD);
        int numMoves = 0;
        Set<Move> moves = new HashSet<>();
        Iterator<Move> legalMoves = b.legalMoves(Piece.WHITE);
        while (legalMoves.hasNext()) {
            Move m = legalMoves.next();
            moves.add(m);
        }
        int x = moves.size();
        assertEquals(2176, moves.size());
    }

    @Test
    public void testNone() {
        Board b = new Board();
        buildBoard(b, INITIALBOARDER);
        Iterator<Move> iter = b.legalMoves(B);
        assertFalse(iter.hasNext());
    }

    @Test
    public void testdone() {
        Board b = new Board();
        buildBoard(b, NOMOVES);
        Iterator<Move> iter = b.legalMoves(W);
        assertFalse(iter.hasNext());
    }

    @Test
    public void testStatic() {
        Board b = new Board();
        buildBoard(b, STATICBOARD);
        int numMoves = 0;
        Iterator<Move> iter = b.legalMoves(W);
        while (iter.hasNext()) {
            Move mv = iter.next();
            numMoves += 1;
        }
    }
    @Test
    public void teststart2() {
        Board b = new Board();
        buildBoard(b, TESTBOARDCAP);
        int numMoves = 0;
        Iterator<Move> iter = b.legalMoves(B);
        while (iter.hasNext()) {
            Move mv = iter.next();
            String p = mv.toString();
            numMoves += 1;
        }
    }



    private void buildBoard(Board b, Piece[][] target) {
        for (int col = 0; col < Board.SIZE; col++) {
            for (int row = Board.SIZE - 1; row >= 0; row--) {
                Piece piece = target[Board.SIZE - row - 1][col];
                b.put(piece, Square.sq(col, row));
            }
        }
    }

    Square from = Square.sq(5, 4);
    private Set<Move> _moves = new HashSet<>(Arrays.asList(
            Move.mv(from, Square.sq(5, 5), from),
            Move.mv(from, Square.sq(5, 5), Square.sq(4, 5)),
            Move.mv(from, Square.sq(5, 5), Square.sq(4, 4)),
            Move.mv(from, Square.sq(5, 5), Square.sq(4, 4)),
            Move.mv(from, Square.sq(5, 5), Square.sq(6, 5)),
            Move.mv(from, Square.sq(5, 5), Square.sq(6, 4)),
            Move.mv(from, Square.sq(5, 5), Square.sq(7, 5)),

            Move.mv(from, Square.sq(6, 5), Square.sq(5, 4)),
            Move.mv(from, Square.sq(6, 5), Square.sq(5, 5)),
            Move.mv(from, Square.sq(6, 5), Square.sq(4, 5)),
            Move.mv(from, Square.sq(6, 5), Square.sq(6, 4)),
            Move.mv(from, Square.sq(6, 5), Square.sq(7, 4)),
            Move.mv(from, Square.sq(6, 5), Square.sq(7, 5)),
            Move.mv(from, Square.sq(6, 5), Square.sq(7, 6)),
            Move.mv(from, Square.sq(6, 5), Square.sq(8, 7)),

            Move.mv(from, Square.sq(7, 6), Square.sq(6, 5)),
            Move.mv(from, Square.sq(7, 6), Square.sq(5, 4)),
            Move.mv(from, Square.sq(7, 6), Square.sq(8, 7)),
            Move.mv(from, Square.sq(7, 6), Square.sq(8, 6)),
            Move.mv(from, Square.sq(7, 6), Square.sq(7, 5)),
            Move.mv(from, Square.sq(7, 6), Square.sq(7, 4)),
            Move.mv(from, Square.sq(7, 6), Square.sq(6, 7)),
            Move.mv(from, Square.sq(7, 6), Square.sq(5, 8)),
            Move.mv(from, Square.sq(7, 6), Square.sq(4, 9)),

            Move.mv(from, Square.sq(8, 7), Square.sq(8, 6)),
            Move.mv(from, Square.sq(8, 7), Square.sq(7, 6)),
            Move.mv(from, Square.sq(8, 7), Square.sq(6, 5)),
            Move.mv(from, Square.sq(8, 7), Square.sq(5, 4)),
            Move.mv(from, Square.sq(8, 7), Square.sq(7, 8)),
            Move.mv(from, Square.sq(8, 7), Square.sq(6, 9)),

            Move.mv(from, Square.sq(6, 4), Square.sq(7, 4)),
            Move.mv(from, Square.sq(6, 4), Square.sq(7, 5)),
            Move.mv(from, Square.sq(6, 4), Square.sq(6, 4)),
            Move.mv(from, Square.sq(6, 4), Square.sq(5, 5)),
            Move.mv(from, Square.sq(6, 4), Square.sq(5, 4)),
            Move.mv(from, Square.sq(6, 4), Square.sq(4, 4)),
            Move.mv(from, Square.sq(6, 4), Square.sq(6, 5)),
            Move.mv(from, Square.sq(6, 4), Square.sq(6, 5)),
            Move.mv(from, Square.sq(6, 4), Square.sq(7, 5)),
            Move.mv(from, Square.sq(6, 4), Square.sq(8, 6)),

            Move.mv(from, Square.sq(7, 4), Square.sq(7, 5)),
            Move.mv(from, Square.sq(7, 4), Square.sq(7, 6)),
            Move.mv(from, Square.sq(7, 4), Square.sq(6, 5)),
            Move.mv(from, Square.sq(7, 4), Square.sq(6, 4)),
            Move.mv(from, Square.sq(7, 4), Square.sq(5, 4)),
            Move.mv(from, Square.sq(7, 4), Square.sq(4, 4)),

            Move.mv(from, Square.sq(4, 4), Square.sq(5, 4)),
            Move.mv(from, Square.sq(4, 4), Square.sq(6, 4)),
            Move.mv(from, Square.sq(4, 4), Square.sq(7, 4)),
            Move.mv(from, Square.sq(4, 4), Square.sq(4, 5)),
            Move.mv(from, Square.sq(4, 4), Square.sq(4, 5)),
            Move.mv(from, Square.sq(4, 4), Square.sq(5, 5)),

            Move.mv(from, Square.sq(4, 5), Square.sq(5, 5)),
            Move.mv(from, Square.sq(4, 5), Square.sq(6, 5)),
            Move.mv(from, Square.sq(4, 5), Square.sq(7, 5)),
            Move.mv(from, Square.sq(4, 5), Square.sq(4, 4)),
            Move.mv(from, Square.sq(4, 5), Square.sq(5, 4))));


    Square w1 = Square.sq(0, 3);
    Square w2 = Square.sq(2, 0);
    private Set<Move> _simplemoves = new HashSet<>(Arrays.asList(
            Move.mv(w1, Square.sq(1, 3), Square.sq(0, 3)),
            Move.mv(w1, Square.sq(1, 3), Square.sq(0, 2)),
            Move.mv(w1, Square.sq(0, 2), Square.sq(1, 3)),
            Move.mv(w1, Square.sq(0, 2), Square.sq(0, 3)),
            Move.mv(w1, Square.sq(0, 2), Square.sq(0, 1)),
            Move.mv(w1, Square.sq(0, 1), Square.sq(0, 2)),
            Move.mv(w1, Square.sq(0, 1), Square.sq(0, 3)),
            Move.mv(w1, Square.sq(0, 1), Square.sq(1, 0)),

            Move.mv(w2, Square.sq(3, 0), Square.sq(2, 0)),
            Move.mv(w2, Square.sq(3, 0), Square.sq(1, 0)),
            Move.mv(w2, Square.sq(3, 0), Square.sq(2, 1)),
            Move.mv(w2, Square.sq(1, 0), Square.sq(2, 0)),
            Move.mv(w2, Square.sq(1, 0), Square.sq(3, 0)),
            Move.mv(w2, Square.sq(1, 0), Square.sq(2, 1)),
            Move.mv(w2, Square.sq(1, 0), Square.sq(3, 2)),
            Move.mv(w2, Square.sq(1, 0), Square.sq(0, 1)),
            Move.mv(w2, Square.sq(2, 1), Square.sq(2, 0)),
            Move.mv(w2, Square.sq(2, 1), Square.sq(3, 0)),
            Move.mv(w2, Square.sq(2, 1), Square.sq(1, 0)),
            Move.mv(w2, Square.sq(2, 1), Square.sq(3, 2))));

    static final Piece E = Piece.EMPTY;

    static final Piece W = Piece.WHITE;

    static final Piece B = Piece.BLACK;

    static final Piece S = Piece.SPEAR;

    static final Piece[][] REACHABLEFROMTESTBOARD = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, S, S },
            { E, E, E, E, E, E, E, S, E, S },
            { E, E, E, S, S, S, S, E, E, S },
            { E, E, E, S, E, E, E, E, B, E },
            { E, E, E, S, E, W, E, E, B, E },
            { E, E, E, S, S, S, B, S, B, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
    };
    static final Piece[][] SIMPLELEGAL = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { S, S, S, S, S, E, E, E, E, E },
            { W, E, S, S, S, E, E, E, E, E },
            { E, S, S, E, S, E, E, E, E, E },
            { E, S, E, S, S, E, E, E, E, E },
            { S, E, W, E, S, E, E, E, E, E },
    };
    static final Piece[][] INITIALBOARD = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { W, E, E, E, E, E, E, E, E, W },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, W, E, E, W, E, E, E },
    };
    static final Piece[][] TESTBOARDCAP = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { W, E, E, E, E, E, E, E, E, W },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, W, E, S, E, E, E, E },
            { E, E, E, E, E, E, W, E, E, E },
    };

    static final Piece[][] STATICBOARD = {
            { E, E, E, B, E, E, B, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { B, E, E, E, E, E, E, E, E, B },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { W, E, E, E, E, E, E, E, E, W },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, W, E, E, E, E, E, E },
            { E, E, E, E, E, E, W, E, E, E },
    };

    static final Piece[][] INITIALBOARDER = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, W, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { S, S, E, E, E, E, E, E, E, E },
            { B, S, E, E, E, E, E, E, E, E },
    };
    static final Piece[][] NOMOVES = {
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, B, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { E, E, E, E, E, E, E, E, E, E },
            { S, S, E, E, E, E, E, E, E, E },
            { W, S, S, S, E, E, E, E, E, E },
            { W, W, W, S, E, E, E, E, E, E },
    };
    static final Set<Square> REACHABLEFROMTESTSQUARES =
            new HashSet<>(Arrays.asList(
                    Square.sq(5, 5),
                    Square.sq(4, 5),
                    Square.sq(4, 4),
                    Square.sq(6, 4),
                    Square.sq(7, 4),
                    Square.sq(6, 5),
                    Square.sq(7, 6),
                    Square.sq(8, 7)));

}
