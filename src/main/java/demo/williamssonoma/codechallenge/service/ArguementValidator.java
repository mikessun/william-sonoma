package demo.williamssonoma.codechallenge.service;

import java.util.Collection;

/**
 * This is the service to parse and validate input arguements
 */

public interface ArguementValidator<ZipRange> {

    public Collection<ZipRange> parseAndValidateArguement(String[] args);

}
