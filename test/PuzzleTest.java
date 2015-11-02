import static org.junit.Assert.*;
import org.junit.Test;

/** Testklass.
 * @author jaanus
 */
public class PuzzleTest {

   @Test (timeout=20000)
   public void test1() { 
      Puzzle.main (new String[]{"YKS", "KAKS", "KOLM"});
      Puzzle.main (new String[]{"SEND", "MORE", "MONEY"});
      // Puzzle.main (new String[]{"ABCDEFGHIJAB", "ABCDEFGHIJA", "ACEHJBDFGIAC"});
      assertTrue ("There are no tests", true);
   }

}

