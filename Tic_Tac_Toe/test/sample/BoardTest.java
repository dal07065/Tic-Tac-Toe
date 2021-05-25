package sample;

import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private JFXPanel panel = new JFXPanel();

    @Before
    public void setUp() throws Exception {
        board = new Board(3,3);
    }

    @Test
    public void getBoardSize() {
        assertEquals(3, board.getBoardSize());

    }
}