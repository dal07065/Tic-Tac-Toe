package sample;

import javafx.embed.swing.JFXPanel;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class MainTest extends TestCase{

    private Board board;
    private JFXPanel panel = new JFXPanel();

    @Before
    public void setUp() throws Exception
    {
        board = new Board(3,3);
    }

    @Test
    public void testGetBoardSize() throws Exception
    {
        assertEquals(board.getBoardSize(), 2);
    }


}