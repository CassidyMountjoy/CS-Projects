package amazons;


import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import static amazons.Piece.EMPTY;
import static amazons.Piece.BLACK;
import static amazons.Piece.WHITE;
import static amazons.Piece.SPEAR;
import static amazons.Move.mv;


/** The state of an Amazons Game.
 *  @author Cassidy
 */
class Board {

    /** The number of squares on a side of the board. */
    static final int SIZE = 10;

    /** Initializes a game board with SIZE squares on a side in the
     *  initial position. */
    Board() {
        init();
    }

    /** Initializes a copy of MODEL. */
    Board(Board model) {
        copy(model);
    }

    /** Copies MODEL into me. */
    void copy(Board model) {
        Piece[][] interboard = new
                Piece[model._board.length][model._board.length];
        for (int i = 0; i < model._board.length; i++) {
            for (int j = 0; j < model._board.length; j++) {
                interboard[i][j] = model._board[i][j];
            }
        }
        this._board = interboard;
    }

    /** Clears the board to the initial position. */
    void init() {
        _board = new Piece[10][10];
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                _board[i][j] = Piece.EMPTY;
            }
        }
        _board[0][0] = Piece.EMPTY;
        _board[0][1] = Piece.EMPTY;
        _board[0][2] = Piece.EMPTY;
        _board[0][4] = Piece.EMPTY;
        _board[0][5] = Piece.EMPTY;
        _board[0][7] = Piece.EMPTY;
        _board[0][8] = Piece.EMPTY;
        _board[0][9] = Piece.EMPTY;
        _board[9][0] = Piece.EMPTY;
        _board[9][1] = Piece.EMPTY;
        _board[9][2] = Piece.EMPTY;
        _board[9][4] = Piece.EMPTY;
        _board[9][5] = Piece.EMPTY;
        _board[9][7] = Piece.EMPTY;
        _board[9][8] = Piece.EMPTY;
        _board[9][9] = Piece.EMPTY;
        _board[1][0] = Piece.EMPTY;
        _board[2][0] = Piece.EMPTY;
        _board[4][0] = Piece.EMPTY;
        _board[5][0] = Piece.EMPTY;
        _board[7][0] = Piece.EMPTY;
        _board[8][0] = Piece.EMPTY;
        _board[1][9] = Piece.EMPTY;
        _board[2][9] = Piece.EMPTY;
        _board[4][9] = Piece.EMPTY;
        _board[5][9] = Piece.EMPTY;
        _board[7][9] = Piece.EMPTY;
        _board[8][9] = Piece.EMPTY;
        _board[9][3] = BLACK;
        _board[9][6] = BLACK;
        _board[6][0] = BLACK;
        _board[6][9] = BLACK;
        _board[0][3] = WHITE;
        _board[0][6] = WHITE;
        _board[3][0] = WHITE;
        _board[3][9] = WHITE;
        _turn = WHITE;
        _winner = null;
    }

    /** Return the Piece whose move it is (WHITE or BLACK). */
    Piece turn() {
        return _turn;
    }

    /** Return the number of moves (that have not been undone) for this
     *  board. */
    int numMoves() {
        return _nummoves;
    }

    /** Return the winner in the current position, or null if the game is
     *  not yet finished. */
    Piece winner() {
        if (_winner != null) {
            return _winner;
        } else {
            return null;
        }
    }

    /** Return the contents the square at S. */
    final Piece get(Square s) {
        return _board[s.row()][s.col()];
    }

    /** Return the contents of the square at (COL, ROW), where
     *  0 <= COL, ROW <= 9. */
    final Piece get(int col, int row) {
        return _board[row][col];
    }

    /** Return the contents of the square at COL ROW. */
    final Piece get(char col, char row) {
        return get(col - 'a', row - '1');
    }

    /** Set square S to P. */
    final void put(Piece p, Square s) {
        _board[s.row()][s.col()] = p;
    }

    /** Set square (COL, ROW) to P. */
    final void put(Piece p, int col, int row) {
        _board[row + 1][col + 1] = p;
    }

    /** Set square COL ROW to P. */
    final void put(Piece p, char col, char row) {
        put(p, col - 'a', row - '1');
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedMove(Square from, Square to, Square asEmpty) {
        _itsnull = false;
        if (asEmpty == null) {
            _itsnull = true;
        }
        if (to == null || from == null) {
            return false;
        }
        if (!isinbounds(from) || !isinbounds(to)) {
            return false;
        }
        if (!from.isQueenMove(to)) {
            return false;
        }
        if (from.col() > 9 || from.col() < 0
                || to.col() > 9 || to.col() < 0
                || from.row() > 9 || from.row() < 0
                || to.row() > 9 || to.row() < 0) {
            return false;
        }
        if (from.row() == to.row()) {
            if (from.col() < to.col()) {
                for (int col = from.col() + 1; col <= to.col(); col++) {
                    if (to.row() > 9 || to.row() < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    if (_board[to.row()][col] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, to.row())) {
                            return false;
                        }
                    }
                    if (_board[to.row()][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
            if (from.col() > to.col()) {
                for (int col = from.col() - 1; col >= to.col(); col--) {
                    if (to.row() > 9 || to.row() < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    if (_board[to.row()][col] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, to.row())) {
                            return false;
                        }
                    }
                    if (_board[to.row()][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
        }
        return isUnblockedcolumn(from, to, asEmpty);
    }
    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockedcolumn(Square from, Square to, Square asEmpty) {
        if (from.col() == to.col()) {
            if (from.row() < to.row()) {
                for (int row = from.row() + 1; row <= to.row(); row++) {
                    if (row > 9 || row < 0 || to.col() > 9 || to.col() < 0) {
                        return false;
                    }
                    if (_board[row][to.col()] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(from.col(), row)) {
                            return false;
                        }
                    }
                    Piece s = _board[row][to.col()];
                    if (_board[row][to.col()] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
            if (from.row() > to.row()) {
                for (int row = from.row() - 1; row >= to.row(); row--) {
                    if (row > 9 || row < 0 || to.col() > 9 || to.col() < 0) {
                        return false;
                    }
                    if (_board[row][to.col()] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(from.col(), row)) {
                            return false;
                        }
                    }
                    if (_board[row][to.col()] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }

        }
        return isUnblockeddiag(from, to, asEmpty);
    }
    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean isUnblockeddiag(Square from, Square to, Square asEmpty) {
        if (isdiagonalmxb(from, to)) {
            if (to.col() > from.col()) {
                int col = from.col();
                for (int row = from.row() + 1; row <= to.row(); row++) {
                    col += 1;
                    if (row > 9 || row < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    Square diag = Square.sq(col, row);
                    if (_board[row][col] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, row)) {
                            return false;
                        }
                    }
                    if (_board[row][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
            if (to.col() < from.col()) {
                int col = from.col();
                for (int row = from.row() - 1; row >= to.row(); row--) {
                    col -= 1;
                    if (row > 9 || row < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    if (get(col, row) != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, row)) {
                            return false;
                        }
                    }
                    if (_board[row][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }

        }
        return ishelper(from, to, asEmpty);
    }

    /** Return true iff FROM - TO is an unblocked queen move on the current
     *  board, ignoring the contents of ASEMPTY, if it is encountered.
     *  For this to be true, FROM-TO must be a queen move and the
     *  squares along it, other than FROM and ASEMPTY, must be
     *  empty. ASEMPTY may be null, in which case it has no effect. */
    boolean ishelper(Square from, Square to, Square asEmpty) {
        if (isdiagonalnxb(from, to)) {
            if (from.col() > to.col()) {
                int row = from.row();
                for (int col = from.col() - 1; col >= to.col(); col--) {
                    row += 1;
                    if (row > 9 || row < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    if (_board[row][col] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, row)) {
                            return false;
                        }
                    }
                    if (_board[row][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
            if (from.col() < to.col()) {
                int row = from.row();
                for (int col = from.col() + 1; col <= to.col(); col++) {
                    row -= 1;
                    if (row > 9 || row < 0 || col > 9 || col < 0) {
                        return false;
                    }
                    if (_board[row][col] != EMPTY && !_itsnull) {
                        if (asEmpty != Square.sq(col, row)) {
                            return false;
                        }
                    }
                    if (_board[row][col] != EMPTY && _itsnull) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
    /** Return true iff FROM is a valid starting square for a move.
     * @param to is a square to.
     * */
    boolean isinbounds(Square to) {
        if (to.col() > 9 || to.row() < 0) {
            return false;
        }
        return true;
    }

    /** Return true iff FROM is a valid starting square for a move. */
    boolean isLegal(Square from) {
        if (_board[from.row()][from.col()] == turn()) {
            return true;
        }
        return false;
    }

    /** Return true iff FROM-TO is a valid first part of move, ignoring
     *  spear throwing. */
    boolean isLegal(Square from, Square to) {
        if (isUnblockedMove(from, to, null)) {
            return true;
        }
        return false;
    }

    /** Return true iff FROM-TO(SPEAR) is a legal move in the current
     *  position. */
    boolean isLegal(Square from, Square to, Square spear) {
        return isLegal(from) && isLegal(from, to)
                && isUnblockedMove(to, spear, from);
    }

    /** Return true iff MOVE is a legal move in the current
     *  position. */
    boolean isLegal(Move move) {
        return isLegal(move.from(), move.to(), move.spear());
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move. */
    void makeMove(Square from, Square to, Square spear) {
        put(turn(), to);
        put(EMPTY, from);
        put(SPEAR, spear);
        _nummoves += 1;
        if (_turn == WHITE) {
            _turn = BLACK;
            return;
        }

        if (_turn == BLACK) {
            _turn = WHITE;
        }
    }

    /** Move according to MOVE, assuming it is a legal move. */
    void makeMove(Move move) {
        movesdone.add(move);
        makeMove(move.from(), move.to(), move.spear());
    }

    /** Undo one move.  Has no effect on the initial board. */
    void undo() {
        Move mv = movesdone.get(_nummoves - 1);
        put(EMPTY, mv.spear());
        put(turn(), mv.from());
        put(EMPTY, mv.to());
        if (_turn == WHITE) {
            _turn = BLACK;
            return;
        }
        if (_turn == BLACK) {
            _turn = WHITE;
        }
    }

    /** Return an Iterator over the Squares that are reachable by an
     *  unblocked queen move from FROM. Does not pay attention to what
     *  piece (if any) is on FROM, nor to whether the game is finished.
     *  Treats square ASEMPTY (if non-null) as if it were EMPTY.  (This
     *  feature is useful when looking for Moves, because after moving a
     *  piece, one wants to treat the Square it came from as empty for
     *  purposes of spear throwing.) */
    Iterator<Square> reachableFrom(Square from, Square asEmpty) {
        return new ReachableFromIterator(from, asEmpty);
    }

    /** Return an Iterator over all legal moves on the current board. */
    Iterator<Move> legalMoves() {
        return new LegalMoveIterator(_turn);
    }


    /** Return an Iterator over all legal moves on the current board for
     *  SIDE (regardless of whose turn it is). */
    Iterator<Move> legalMoves(Piece side) {
        return new LegalMoveIterator(side);
    }

    /** An iterator used by reachableFrom. */
    private class ReachableFromIterator implements Iterator<Square> {

        /** Iterator of all squares reachable by queen move from FROM,
         *  treating ASEMPTY as empty. */
        ReachableFromIterator(Square from, Square asEmpty) {
            _from = from;
            _dir = 0;
            _steps = 0;
            _asEmpty = asEmpty;
            toNext();
        }

        @Override
        public boolean hasNext() {
            return _dir < 8;
        }

        @Override
        public Square next() {
            toNext();
            return _prev;
        }

        /** Advance _dir and _steps, so that the next valid Square is
         *  _steps steps in direction _dir from _from. */
        private void toNext() {
            if (_dir > 7) {
                return;
            }
            _prev = _from.queenMove(_dir, _steps);
            if (_from.col() + (DIR[_dir][0] * (_steps + 1)) <= 10
                    && _from.row() + (DIR[_dir][1]
                    * (_steps + 1)) <= 10
                    && _from.col() + (DIR[_dir][0]
                    * (_steps + 1)) >= 0
                    && _from.row() + (DIR[_dir][1]
                    * (_steps + 1)) >= 0) {
                if (isUnblockedMove(_from, _from.queenMove(_dir,
                        _steps + 1), _asEmpty)) {
                    _steps += 1;
                    return;
                }
            }
            if (_dir < 7) {
                while (hasNext()
                        && !isUnblockedMove(_from,
                        _from.queenMove(_dir + 1, 1), _asEmpty)) {
                    _dir += 1;
                    _steps = 1;
                    if (_dir == 7) {
                        break;
                    }
                }
            }
            _dir += 1;
            _steps = 1;
        }

        /** Starting square. */
        private Square _from;
        /** Previous square. */
        private Square _prev;
        /** Current direction. */
        private int _dir;
        /** Current distance. */
        private int _steps;
        /** Square treated as empty. */
        private Square _asEmpty;

    }

    /** An iterator used by legalMoves. */
    private class LegalMoveIterator implements Iterator<Move> {

        /** All legal moves for SIDE (WHITE or BLACK). */
        LegalMoveIterator(Piece side) {
            _startingSquares = Square.iterator();
            _spearThrows = NO_SQUARES;
            _pieceMoves = NO_SQUARES;
            _fromPiece = side;
            _laststartiterator = Square.iterator();
            getthenextstart();
            getthefinal();
            toNext();
            _first = false;
        }

        @Override
        public boolean hasNext() {
            if (_lastpiece == _start && (!_pieceMoves.hasNext()
                    && !_spearThrows.hasNext())) {
                return false;
            }
            if (_spearThrows.hasNext() || _startingSquares.hasNext()
                    || _pieceMoves.hasNext()) {
                return true;
            }
            return false;
        }
        @Override
        public Move next() {
            toNext();
            return _move;
        }

        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void toNext() {
            if (hasNext()) {
                if (!_first) {
                    if (_spearThrows.hasNext()) {
                        Square spear = _spearThrows.next();
                        if (spear != null) {
                            _move = mv(_start, _nextSquare, spear);
                            return;
                        }
                    }
                    if (_pieceMoves.hasNext()) {
                        _nextSquare = _pieceMoves.next();
                        _spearThrows = reachableFrom(_nextSquare, _start);
                        Square spear = _spearThrows.next();
                        if (_start != null && _nextSquare != null
                                && spear != null) {
                            _move = mv(_start, _nextSquare, spear);
                        }
                        return;
                    }
                }
                if (_startingSquares.hasNext() && !_pieceMoves.hasNext()) {
                    getthenextstart();
                    toNext();
                }
            }
        }
        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void getthenextstart() {
            Square potstart;
            while (_startingSquares.hasNext()) {
                potstart = _startingSquares.next();
                if (get(potstart) == _fromPiece) {
                    _start = potstart;
                    _pieceMoves = reachableFrom(_start, null);
                    _nextSquare = _pieceMoves.next();
                    _spearThrows = reachableFrom(_nextSquare, _start);
                    break;
                }
            }
        }
        /** Advance so that the next valid Move is
         *  _start-_nextSquare(sp), where sp is the next value of
         *  _spearThrows. */
        private void getthefinal() {
            Square potstart;
            while (_laststartiterator.hasNext()) {
                potstart = _laststartiterator.next();
                if (get(potstart) == _fromPiece) {
                    _lastpiece = potstart;
                }
            }
        }

        /** Color of side whose moves we are iterating. */
        private Piece _fromPiece;
        /** Current starting square. */
        private Square _start;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _startingSquares;
        /** Current piece's new position. */
        private Square _nextSquare;
        /** Remaining moves from _start to consider. */
        private Iterator<Square> _pieceMoves;
        /** Remaining spear throws from _piece to consider. */
        private Iterator<Square> _spearThrows;
        /** Remaining starting squares to consider. */
        private Iterator<Square> _laststartiterator;
        /** Remaining starting squares to consider. */
        private HashSet<Square> _movesfrompiece;
        /** Remaining starting squares to consider. */
        private Move _move;
        /** Remaining starting squares to consider. */
        private boolean _first = true;
        /** Remaining starting squares to consider. */
        private Square _lastpiece;
    }

    @Override
    public String toString() {
        String repr = "  ";
        int j = 0;
        for (int i = 9; i >= 0; i--) {
            while (j != 10) {
                if (_board[i][j] == null) {
                    repr += " -";
                } else {
                    repr += " " + _board[i][j].toString();
                }
                j += 1;
            }
            if (i == 0) {
                repr += "\n";
            } else {
                repr += "\n  ";
            }
            j = 0;
        }
        return repr;
    }

    /** An empty iterator for initialization. */
    private static final Iterator<Square> NO_SQUARES =
        Collections.emptyIterator();


    /** Move FROM-TO(SPEAR), assuming this is a legal move.
     * @return returns if it is diagonal.
     * */
    boolean isdiagonalmxb(Square from, Square to) {
        if (to.col() > 9 || to.col() < 0
                || to.row() > 9 || to.row() < 0) {
            return false;
        }
        int bincr = from.row() - from.col();
        if ((to.row() == to.col() + bincr)) {
            return true;
        }
        return false;
    }

    /** Move FROM-TO(SPEAR), assuming this is a legal move.
     * @return returns if it is diagonal.
     * */
    boolean isdiagonalnxb(Square from, Square to) {
        if (to.col() > 9 || to.col() < 0
                || to.row() > 9 || to.row() < 0) {
            return false;
        }
        int bdecr = from.col() + from.row();
        if ((to.row() == bdecr - to.col())) {
            return true;
        }
        return false;
    }
    /** Piece whose turn it is (BLACK or WHITE).
     * @param winner takes in winner and sets it.
     * */
    void setwinner(Piece winner) {
        _winner = winner;
    }

    /** Piece whose turn it is (BLACK or WHITE).
     * @param turn whose turn you are trying to set.
     * */
    void setturn(Piece turn) {
        _turn = turn;
    }

    /** Piece whose turn it is (BLACK or WHITE). */
    private Piece _turn;
    /** Cached value of winner on this board, or EMPTY if it has not been
     *  computed. */
    private Piece _winner;
    /** The moves that have been done. */
    private ArrayList<Move> movesdone = new ArrayList<>();
    /** The number of moves. */
    private int _nummoves;
    /** representation of the board. */
    private Piece[][] _board;
    /** My directions. */
    private static final int[][] DIR = {
            { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 },
            { 0, -1 }, { -1, -1 }, { -1, 0 }, { -1, 1 }
    };
    /** If its null. */
    private boolean _itsnull = false;

}
