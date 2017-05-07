package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.model.ZipRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                /*test if the collection is modifiable*/
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
            } else if (isIncludedInSmallerZipRange(previousZipRange, zipRange)) {
                iterator.remove();
            }else {
                previousZipRange.setUpperBound(zipRange.getUpperBound());
                iterator.remove();
            }
        }

        return sortedSet;
    }

    private boolean isIncludedInSmallerZipRange(ZipRange smallerZipRange, ZipRange largerZipRange) {
        return smallerZipRange.getUpperBound() >= largerZipRange.getUpperBound();
    }

    private boolean isExcluded(ZipRange smallerZipRange, ZipRange largerZipRange) {
        return smallerZipRange.getUpperBound() < largerZipRange.getLowBound();
    }
}
