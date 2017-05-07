package demo.williamssonoma.codechallenge;

import demo.williamssonoma.codechallenge.exception.ConsolidationException;
import demo.williamssonoma.codechallenge.exception.InvalidInputException;
import demo.williamssonoma.codechallenge.exception.InvalidZipRangeException;
import demo.williamssonoma.codechallenge.model.ZipRange;
import demo.williamssonoma.codechallenge.service.ArguementValidator;
import demo.williamssonoma.codechallenge.service.ZipRangeConsolidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.System.exit;

/**
 * SpringBoot main application executed from console
 *
 * @author Michael Sun
 */
@SpringBootApplication
@Slf4j
public class SonomaZipConsolidationApplication implements CommandLineRunner {

    @Autowired
    private ArguementValidator arguementValidator;

    @Autowired
    private ZipRangeConsolidator zipRangeConsolidator;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SonomaZipConsolidationApplication.class);
        /*disable SpringBoot banner*/
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Collection<ZipRange> consolidatedZipRanges = zipRangeConsolidator.consolidateZipRanges(arguementValidator.parseAndValidateArguement(args));
            log.info("Consolidated zip ranges: {}", consolidatedZipRanges.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));
            exit(0);
        } catch (InvalidZipRangeException|InvalidInputException|ConsolidationException e) {
            log.error("Error to consolidate inputs: {}", Arrays.toString(args), e);
        }
    }
}