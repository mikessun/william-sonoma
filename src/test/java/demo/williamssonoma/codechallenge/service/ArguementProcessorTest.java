package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidInputException;
import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

/**
 * Tests for {@link ArguementProcessor}
 *
 * @author Michael Sun
 */
public class ArguementProcessorTest {

    @Rule
    public ExpectedException expectedException=ExpectedException.none();

    private ArguementProcessor arguementProcessor;

    @Before
    public void before() {
        arguementProcessor = new ArguementProcessorImpl();
    }

    @Test
    public void testParseAndValidateArguementWithNoArguement() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("zip range input can not be empty!"));
        arguementProcessor.parseAndValidateArguement(null);
    }

    @Test
    public void testParseAndValidateArguementWithEmptyArguement() {
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("zip range input can not be empty!"));
        arguementProcessor.parseAndValidateArguement(new String[]{});
    }

    @Test
    public void testParseAndValidateArguementWithValidRange() {
        ZipRange zipRange1 = new ZipRange(12345, 12348);
        ZipRange zipRange2 = new ZipRange(22345, 22345);
        String[] args = new String[]{"[ " + zipRange1.getLowBound() + " , " + zipRange1.getUpperBound() +
                " ]", "[" + zipRange2.getLowBound() + "," + zipRange2.getUpperBound() + "]"};
        Collection<ZipRange> collection = arguementProcessor.parseAndValidateArguement(args);

        assertThat(collection, hasItem(zipRange1));
        assertThat(collection, hasItem(zipRange2));
    }

    @Test
    public void testParseAndValidateArguementWith6DigitsRange() {
        ZipRange zipRange1 = new ZipRange(111111, 111112);
        ZipRange zipRange2 = new ZipRange(11114, 11115);
        String[] args = new String[]{"[ " + zipRange1.getLowBound() + " , " + zipRange1.getUpperBound() +
                " ]", "[" + zipRange2.getLowBound() + "," + zipRange2.getUpperBound() + "]"};

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("Invalid input element: [ 111111 , 111112 ]"));
        arguementProcessor.parseAndValidateArguement(args);
    }

    @Test
    public void testParseAndValidateArguementWithMinusDigitsRange() {
        ZipRange zipRange1 = new ZipRange(-12341,  12342);
        ZipRange zipRange2 = new ZipRange(11114, 11115);
        String[] args = new String[]{"[ " + zipRange1.getLowBound() + " , " + zipRange1.getUpperBound() +
                " ]", "[" + zipRange2.getLowBound() + "," + zipRange2.getUpperBound() + "]"};

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("Invalid input element: [ -12341 , 12342 ]"));
        arguementProcessor.parseAndValidateArguement(args);
    }

    @Test
    public void testParseAndValidateArguementWithInvalidRange() {
        String[] args = new String[]{"[11112]", "[11114,11115]"};

        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("Invalid input element: [11112]"));
        arguementProcessor.parseAndValidateArguement(args);
    }

    @Test
    public void testParseAndValidateArguementWithLowOverUpperRange() {
        String[] args = new String[]{"[11112,11110]", "[11114,11115]"};
        expectedException.expect(InvalidZipRangeException.class);
        expectedException.expectMessage(containsString("Lower bound must not be greater than upper bound - [11112,11110]"));
        arguementProcessor.parseAndValidateArguement(args);
    }

    @Test
    public void testParseAndValidateArguementWithNonnumbericRange() {
        String[] args = new String[]{"[low, 11112]", "[11114, 11115]"};
        expectedException.expect(InvalidInputException.class);
        expectedException.expectMessage(containsString("Invalid input element: [low, 11112]"));
        arguementProcessor.parseAndValidateArguement(args);
    }
}