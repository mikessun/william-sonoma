package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidInputException;
import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

/**
 * Tests for {@link ZipRangeConsolidator}
 *
 * @author Michael Sun
 */
public class ZipRangeConsolidatorTest {
    @Rule
    public ExpectedException expectedException=ExpectedException.none();

    private ZipRangeConsolidator zipRangeConsolidator;

    @Before
    public void before() {
        zipRangeConsolidator=new ZipRangeConsolidatorImpl();
    }

    @Test
    public void testConsolidateZipRangesWithExcludedZipRanges() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94133);
        ZipRange zipRange1 = new ZipRange(94200, 94299);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange2, zipRange1, zipRange});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(3));
        assertThat(zipRanges, contains(zipRange,zipRange1,zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWith3DigitsZipRanges() throws Exception {
        ZipRange zipRange = new ZipRange(133, 136);
        ZipRange zipRange1 = new ZipRange(200, 299);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange2, zipRange1, zipRange});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(3));
        assertThat(zipRanges, contains(zipRange,zipRange1,zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWithIncludedElementsInSmallerKeyElement() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94193);
        ZipRange zipRange1 = new ZipRange(94170, 94172);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange2, zipRange, zipRange1});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(2));
        assertThat(zipRanges, contains(zipRange,zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWithIncludedElementsInLargerKeyElement() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94193);
        ZipRange zipRange1 = new ZipRange(94133, 94200);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange, zipRange1, zipRange2});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(2));
        assertThat(zipRanges, contains(zipRange1,zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWithEqualElements() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94193);
        ZipRange zipRange1 = new ZipRange(94133, 94193);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange2, zipRange1, zipRange});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(2));
        assertThat(zipRanges, contains(zipRange1,zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWithOverlappedElements() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94193);
        ZipRange zipRange1 = new ZipRange(94163, 94200);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange2, zipRange1, zipRange});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(2));
        assertThat(zipRanges, contains(new ZipRange(zipRange.getLowBound(),zipRange1.getUpperBound()),zipRange2));
    }

    @Test
    public void testConsolidateZipRangesWithEqualUpperBoundAndLowerBound() throws Exception {
        ZipRange zipRange = new ZipRange(94133, 94193);
        ZipRange zipRange1 = new ZipRange(94193, 94200);
        ZipRange zipRange2 = new ZipRange(94600, 94699);
        List<ZipRange> zipRangesOrig = Arrays.asList(new ZipRange[]{zipRange1, zipRange, zipRange2});
        Collection<ZipRange> zipRanges = zipRangeConsolidator.consolidateZipRanges(zipRangesOrig);

        assertThat(zipRanges.size(), is(2));
        assertThat(zipRanges, contains(new ZipRange(zipRange.getLowBound(),zipRange1.getUpperBound()),zipRange2));
    }

    @Test
    public void testConsolidateMinusZipRanges() throws Exception {
        expectedException.expect(InvalidZipRangeException.class);
        expectedException.expectMessage(containsString("Invalid zip range - [-0012,-0001]"));
        zipRangeConsolidator.consolidateZipRanges(Arrays.asList(new ZipRange[]{new ZipRange(-12, -1)}));
    }
    @Test
    public void testConsolidateInvalidZipRanges() throws Exception {
        expectedException.expect(InvalidZipRangeException.class);
        expectedException.expectMessage(containsString("Lower bound must not be greater than upper bound - [2,1]"));
        zipRangeConsolidator.consolidateZipRanges(Arrays.asList(new ZipRange[]{new ZipRange(2, 1)}));
    }
}