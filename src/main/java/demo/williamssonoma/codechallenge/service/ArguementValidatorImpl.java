package demo.williamssonoma.codechallenge.service;

import demo.williamssonoma.codechallenge.exception.InvalidInputException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * This is the implementation of arguements validation
 */
@Service
public class ArguementValidatorImpl implements ArguementValidator {
    public static final Pattern ZIP_RANGE_PATTERN = Pattern.compile("\\[(\\d{5}),(\\d{5})\\]");

    @Override
    public Collection<ZipRange> parseAndValidateArguement(String[] args) {
        if (args == null || args.length == 0) {
            throw new InvalidInputException("zip range input can not be empty!");
        }
        Collection<ZipRange> zipRanges = new TreeSet<>();
        IntStream.range(0, args.length)
                .forEach(i -> {
                    Matcher matcher = ZIP_RANGE_PATTERN.matcher(args[i].replaceAll(" ", ""));

                    if (matcher.find()) {
                        final int lowBound = Integer.parseInt(matcher.group(1));
                        final int upperBound = Integer.parseInt(matcher.group(2));
                        if (lowBound > upperBound) {
                            throw new InvalidInputException(String.format("low bound can not be greater than upper bound: %s",
                                    args[i]));
                        }
                        zipRanges.add(new ZipRange(lowBound, upperBound));
                    } else {
                        throw new InvalidInputException(String.format("Invalid input element: %s", args[i]));
                    }

                });

        return zipRanges;
    }
}
