package demo.williamssonoma.codechallenge.service;

import java.util.Collection;

/**
 * This is the service to parse and validate input arguements
 *
 *  @author Michael Sun
 */

public interface ArguementProcessor<ZipRange> {

    /**
     * <p>
     *     This method will parse the input array of string zip ranges and validate
     *     each zip range element. It returns a set of valid {@link ZipRange}s
     * </p>
     *
     * @param array of string zip ranges
     * @return the consolidated zip ranges
     */
    public Collection<ZipRange> parseAndValidateArguement(String[] args);

}
