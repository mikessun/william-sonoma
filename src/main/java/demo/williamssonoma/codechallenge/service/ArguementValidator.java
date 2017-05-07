package demo.williamssonoma.codechallenge.service;

import java.util.Collection;

/**
 * This is the service to parse and validate input arguements
 *
 *  @author Michael Sun
 */

public interface ArguementValidator<ZipRange> {

    /**
     * <p>
     *     This method will parse the input array of string zip ranges and validate
     *     the each zip range element. Finally return a ascending-order set of zip ranges
     * </p>
     *
     * @param array of string zip ranges
     * @return the consolidated zip ranges
     */
    public Collection<ZipRange> parseAndValidateArguement(String[] args);

}
