package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

/**
 * This interface defines the action to consolidate zip ranges
 *
 *  @author Michael Sun
 */
public interface ZipRangeConsolidator {
    public static final Validator CONATRAIN_VALIDAOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * <p>
     *     This method consolidates the input {@link ZipRange}s and output the minmum number of
     *     ranges with ascending order.
     *
     * @param zipRanges the zip code ranges to be consolidated
     * @return the consolidated list of zip ranges, sorted in ascending order
     */
    public Collection<ZipRange> consolidateZipRanges(Collection<ZipRange> zipRanges);

    /**
     * <p>
     *     This method validates the constraints defined in a {@link ZipRange} listed below:
     *     <li>only positive values</li>
     *     <li>must be not great than 99999</li>
     * </p>
     *
     * <p></p>
     * @param zipRange
     */
    default void validateZipRange(ZipRange zipRange) {
        Set<ConstraintViolation<ZipRange>> violations = CONATRAIN_VALIDAOR.validate(zipRange);
        if (violations != null && violations.size() > 0) {
            throw new InvalidZipRangeException(String.format("Invalid zip range - %s", zipRange));
        }
    }
}
