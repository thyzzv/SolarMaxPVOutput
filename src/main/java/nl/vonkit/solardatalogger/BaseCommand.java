package nl.vonkit.solardatalogger;

import java.util.function.Function;

public interface BaseCommand {

    String getName();

    String getPvOutputParam();

    Function<Float, String> getToPvOutputConverter();

    Function<Float, Float> getToHumanConverter();
}
