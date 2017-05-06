package demo.williamssonoma.codechallenge;

import demo.williamssonoma.codechallenge.model.ZipRange;
import demo.williamssonoma.codechallenge.service.ArguementValidator;
import demo.williamssonoma.codechallenge.service.ZipRangeConsolidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;
import java.util.stream.Collectors;

import static java.lang.System.exit;

@SpringBootApplication
@Slf4j
public class SomonaZipConsolidationApplication implements CommandLineRunner {

    @Autowired
    private ArguementValidator arguementValidator;

    @Autowired
    private ZipRangeConsolidator zipRangeConsolidator;

    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(SomonaZipConsolidationApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        Collection<ZipRange> zipRanges1 = zipRangeConsolidator.consolidateZipRanges(arguementValidator.parseAndValidateArguement(args));

        log.info("Consolidated zip ranges: {}",zipRanges1.stream().map(x -> x.toString()).collect(Collectors.joining(", ")));

        exit(0);
    }
}