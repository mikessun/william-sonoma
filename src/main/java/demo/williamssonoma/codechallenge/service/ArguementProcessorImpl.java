package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidInputException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * This is the implementation of arguements validation interface {@link ArguementProcessor}
 *
 *  @author Michael Sun
 */
@Service
public class ArguementProcessorImpl implements ArguementProcessor {
    /*zip range pattern*/
    public static final Pattern ZIP_RANGE_PATTERN = Pattern.compile("\\[(\\d{5}),(\\d{5})\\]");

    /**
     * {@inheritDoc}
     * @see ArguementProcessor#parseAndValidateArguement(String[])
     *
     * @param args
     * @return
     */
    @Override
    public Collection<ZipRange> parseAndValidateArguement(String[] args) {
        if (args == null || args.length == 0) {
            throw new InvalidInputException("zip range input can not be empty!");
        }
        Collection<ZipRange> zipRanges = new HashSet<>();
        IntStream.range(0, args.length)
                .forEach(i -> {
                    Matcher matcher = ZIP_RANGE_PATTERN.matcher(args[i].replaceAll(" ", ""));

                    if (matcher.find()) {
                        final int lowBound = Integer.parseInt(matcher.group(1));
                        final int upperBound = Integer.parseInt(matcher.group(2));
                        zipRanges.add(new ZipRange(lowBound, upperBound));
                    } else {
                        throw new InvalidInputException(String.format("Invalid input element: %s", args[i]));
                    }

                });

        return zipRanges;
    }
}
