package amazons;



import java.util.Iterator;



import static amazons.Piece.*;


/** A Player that automatically generates moves.
 *  @author Cassidy
 */
class AI extends Player {

    /**
     * A position magnitude indicating a win (for white if positive, black
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;

    /**
     * A new AI with no piece or controller (intended to produce
     * a template).
     */
    AI() {
        this(null, null);
    }

    /**
     * A new AI playing PIECE under control of CONTROLLER.
     */
    AI(Piece piece, Controller controller) {
        super(piece, controller);
    }

    @Override
    Player create(Piece piece, Controller controller) {
        _controller = controller;
        return new amazons.AI(piece, controller);
    }

    @Override
    String myMove() {
        _nummoves += 1;
        Move move = findMove();
        _controller.reportMove(move);
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = new Board(board());
        if (_myPiece == WHITE) {
            findMove(b, maxDepth(b), true, 1, -INFTY, INFTY);
        } else {
            findMove(b, maxDepth(b), true, -1, -INFTY, INFTY);
        }
        return _lastFoundMove;
    }

    /**
     * The move found by the last call to one of the ...FindMove methods
     * below.
     */
    private Move _lastFoundMove;

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _lastFoundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _lastMoveFound.
     */
    private int findMove(Board board, int depth,
                         boolean saveMove, int sense,
                         int alpha, int beta) {
        if (depth == 0 || board.winner() != null) {
            return staticScore(board);
        }
        if (sense == 1) {
            Iterator<Move> mymoves = board.legalMoves(WHITE);
            int bestval = -INFTY;
            int numsearches = 0;
            while (mymoves.hasNext() & numsearches < 100) {
                numsearches += 1;
                Board currentboard = board();
                Move currentmove = mymoves.next();
                currentboard.copy(board);
                currentboard.makeMove(currentmove);
                int potent = findMove(currentboard,
                        depth - 1, false, -1, alpha, beta);
                currentboard.undo();
                if (potent >= bestval) {
                    if (saveMove) {
                        _lastFoundMove = currentmove;
                    }
                    bestval = potent;
                    alpha = Math.max(potent, alpha);
                    if (bestval > beta) {
                        break;
                    }
                }
            }
            return bestval;
        } else {
            findMoveother(board, depth, saveMove, sense, alpha, beta);
        }
        return 2;
    }

    /**
     * Return a heuristically determined maximum search depth
     * based on characteristics of BOARD.
     * @param depth deep.
     * @param board the board.
     * @param saveMove if move should be saved.
     * @param sense max or not.
     * @param alpha min.
     * @param beta max.
     */
    private int findMoveother(Board board, int depth,
                              boolean saveMove, int sense,
                              int alpha, int beta) {
        int bestval = INFTY;
        int numsearches = 0;
        Iterator<Move> opponentmoves = board.legalMoves(BLACK);
        while (opponentmoves.hasNext() & numsearches < 100) {
            numsearches += 1;
            Board currentboard = board();
            Move currentmove = opponentmoves.next();
            currentboard.copy(board);
            currentboard.makeMove(currentmove);
            int potent = findMove(currentboard,
                    depth - 1, false, 1, alpha, beta);
            currentboard.undo();
            if (potent <= bestval) {
                if (saveMove) {
                    _lastFoundMove = currentmove;
                }
                bestval = potent;
                beta = Math.min(potent, beta);
                if (beta <= alpha) {
                    break;
                }
            }
        }
        return bestval;
    }

    /**
     * Return a heuristically determined maximum search depth
     * based on characteristics of BOARD.
     */
    private int maxDepth(Board board) {
        int N =  _nummoves;
        if (N < 2 * 10) {
            return 5;
        }
        return 2 * 5;
    }


    /**
     * Return a heuristic value for BOARD.
     */
    private int staticScore(Board board) {
        Iterator<Move> whitemoves;
        whitemoves = board.legalMoves(WHITE);
        Iterator<Move> blackmoves;
        blackmoves = board.legalMoves(BLACK);
        int scorewhite = 0;
        int scoreblack = 0;
        if (board.winner() == BLACK) {
            return -WINNING_VALUE;
        } else if (board.winner() == WHITE) {
            return WINNING_VALUE;
        }
        while (whitemoves.hasNext()) {
            Move m = whitemoves.next();
            scorewhite += 1;
        }
        while (blackmoves.hasNext()) {
            Move p = blackmoves.next();
            scoreblack += 1;
        }
        if (scorewhite == 0 && _controller.board().turn() == WHITE) {
            _controller.board().setwinner(BLACK);
        }
        if (scoreblack == 0 && _controller.board().turn() == BLACK) {
            _controller.board().setwinner(WHITE);
        }
        return scorewhite - scoreblack;
    }
    /** The move iterator. */
    private Iterator<Move> _mymoves;
    /** The opponents iterator. */
    private Iterator<Move> _opponentmoves;
    /** A current board to be updated. */
    private Move bestmove;
    /** A current board to be updated. */
    private int _nummoves = 0;
}
