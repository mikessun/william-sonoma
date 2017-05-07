package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.ConsolidationException;
import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * This class implements {@link ZipRangeConsolidator}
 *
 *  @author Michael Sun
 */

@Service
@Slf4j
public class ZipRangeConsolidatorImpl implements ZipRangeConsolidator {
    /**
     * {@inheritDoc}
     * @see {@link ZipRangeConsolidator#consolidateZipRanges(Collection)}
     *
     * @param zipRanges
     * @return
     */
    @Override
    public Collection<ZipRange> consolidateZipRanges(Collection<ZipRange> zipRanges) {
        if (zipRanges == null || zipRanges.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        SortedSet<ZipRange> sortedSet = null;
        boolean makingModifiableCollection = true;
        if (zipRanges.getClass().isAssignableFrom(SortedSet.class)) {
            try {
                zipRanges.addAll(Collections.EMPTY_SET);
                sortedSet = (SortedSet<ZipRange>) zipRanges;
                makingModifiableCollection = false;
            } catch (UnsupportedOperationException e) {
                log.info("Input collection is unmodifiable");
            }
        }
        if (makingModifiableCollection) {
            sortedSet = new TreeSet(zipRanges);
        }

        ZipRange previousZipRange = null;
        Iterator<ZipRange> iterator = sortedSet.iterator();

        while (iterator.hasNext()) {
            ZipRange zipRange = iterator.next();
            validateZipRange(zipRange);
            if (previousZipRange == null) {
                previousZipRange = zipRange;
            } else if (isExcluded(previousZipRange, zipRange)) {
                previousZipRange = zipRange;
            } else if (isIncludedInSmallerKeyZipRange(previousZipRange, zipRange)) {
                iterator.remove();
            } else if (isIncludedInLargerKeyZipRange(previousZipRange, zipRange) || isOverlapped(previousZipRange, zipRange)) {
                previousZipRange.setUpperBound(zipRange.getUpperBound());
                iterator.remove();
            } else {
                throw new ConsolidationException(String.format("Invalid range: current temp range %s, current range %s",
                        previousZipRange, zipRange));
            }
        }

        return sortedSet;
    }

    private boolean isIncludedInSmallerKeyZipRange(ZipRange smallerKeyZipRange, ZipRange largerKeyZipRange) {
        return smallerKeyZipRange.getUpperBound() >= largerKeyZipRange.getUpperBound();
    }

    private boolean isIncludedInLargerKeyZipRange(ZipRange smallerKeyZipRange, ZipRange largerKeyZipRange) {
        return smallerKeyZipRange.getUpperBound() <= largerKeyZipRange.getUpperBound();
    }

    private boolean isExcluded(ZipRange smallerKeyZipRange, ZipRange largerKeyZipRange) {
        return smallerKeyZipRange.getUpperBound() < largerKeyZipRange.getLowBound();
    }

    private boolean isOverlapped(ZipRange smallerKeyZipRange, ZipRange largerKeyZipRange) {
        return smallerKeyZipRange.getUpperBound() >= largerKeyZipRange.getLowBound() && smallerKeyZipRange.getUpperBound()
                <= largerKeyZipRange.getUpperBound();
    }
}
