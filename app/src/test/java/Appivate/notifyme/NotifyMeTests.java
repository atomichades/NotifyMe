package Appivate.notifyme;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotifyMeTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void isTextValidationCorrect() {
         MainActivity mainActivity = new MainActivity();
        mainActivity.verifyTextFields("","");
        // does it check for null value
        assertFalse(mainActivity.verifyTextFields("",""));
        //does it check for max characters
        assertFalse(mainActivity.verifyTextFields("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a",""));
        // does it check for correct value
        assertTrue(mainActivity.verifyTextFields("Op Roundup","This is a test"));
    }


}