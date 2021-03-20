package nl.vonkit.solardatalogger.sma;

import nl.vonkit.solardatalogger.BaseCommand;

import java.util.function.Function;

public enum SmaCommand implements BaseCommand {
    ENERGY_DAY("6400_00262200", "v1", v -> v / 1000f, v -> String.valueOf(v)),
    P_AC("6100_40263F00", "v2", v -> v , v -> String.valueOf(v)),
    U_AC_L1("6100_00464800", "v6", v -> v / 100f, v -> String.valueOf(v / 100f));

    private final String responseText;
    private final String pvOutputParam;
    private final Function<Float, Float> toHumanConverter;
    private final Function<Float, String> toPVOutputConverter;

    private SmaCommand(String reponseText) {
        this(reponseText, null, null, null);
    }

    private SmaCommand(String responseText, String pvOutputParam, Function<Float, Float> toHumanConverter, Function<Float, String> toPVOutputConverter) {
        this.responseText = responseText;
        this.pvOutputParam = pvOutputParam;
        this.toHumanConverter = toHumanConverter;
        this.toPVOutputConverter = toPVOutputConverter;
    }

    public String getName() {
        return this.name();
    }

    public String getResponeKey(){
        return responseText;
    }

    public static SmaCommand getEnumByString(String code) {
        for (SmaCommand e : SmaCommand.values()) {
            if (code.equals(e.responseText)) return e;
        }
        return null;
    }

    public String getPvOutputParam() {
        return pvOutputParam;
    }

    public Function<Float, String> getToPvOutputConverter() {
        return toPVOutputConverter;
    }

    public Function<Float, Float> getToHumanConverter() {
        return toHumanConverter;
    }
}
