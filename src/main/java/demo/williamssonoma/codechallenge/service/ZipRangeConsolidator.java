package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Collection;
import java.util.Set;

/**
 * implementing zip range consolidation
 */
public interface ZipRangeConsolidator {
    public static final Validator CONATRAIN_VALIDAOR = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * <p>Given a list of {@link ZipRange}s, this method consolidates the zip ranges and output
     * the minmum number of ranges required to represent the same ranges as the input.
     * Ranges may be provided in any arbitrary order and may or may not overlap.
     *
     * @param ranges the zip code ranges to be merged
     * @return the consolidated list of zip code ranges, sorted in ascending order
     */
    public Collection<ZipRange> consolidateZipRanges(Collection<ZipRange> zipRanges);

    default void validateZipRange(ZipRange zipRange) {
        Set<ConstraintViolation<ZipRange>> violations = CONATRAIN_VALIDAOR.validate(zipRange);
        if (violations != null && violations.size() > 0) {
            throw new InvalidZipRangeException(String.format("Invalid zip range - %s", zipRange));
        }
    }
}
