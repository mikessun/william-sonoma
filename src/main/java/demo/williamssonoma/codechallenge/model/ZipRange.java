package demo.williamssonoma.codechallenge.model;

import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Comparator;

/**
 * this class represents an excluded zip range
 */
@EqualsAndHashCode
public class ZipRange implements Comparable<ZipRange> {
    private static final Comparator<ZipRange> ZIP_RANGE_COMPARATOR =
            Comparator.comparingInt(ZipRange::getLowBound).thenComparingInt(ZipRange::getUpperBound);

    @Getter
    @Min(1)
    @Max(99999)
    private int lowBound;
    @Getter
    @Setter
    @Min(1)
    @Max(99999)
    private int upperBound;

    public ZipRange(int lowBound, int upperBound) {
        if (lowBound > upperBound) {
            throw new InvalidZipRangeException(String.format("Lower bound must not be greater than upper bound - [%s,%s]",
                    lowBound, upperBound));
        }
        this.lowBound = lowBound;
        this.upperBound = upperBound;
    }

    /**
     * Compares this zip code range with the specified range for order. Ordering
     * is determined first by the lower bound, then the upper bound.
     *
     * @param another the range to be compared with
     * @return a negative integer, zero, or a positive integer when this range
     * is less-than, equal-to, or greater-than (respectively) the specified range
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(ZipRange another) {
        return ZIP_RANGE_COMPARATOR.compare(this, another);
    }

    /**
     * Returns a string representation of this zip code range.
     *
     * @return a string representation of the range in the form {@code [lower,upper]}
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.format("[%1$05d,%2$05d]", this.getLowBound(), this.getUpperBound());
    }
}
