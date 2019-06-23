package nl.vonkit.solarmaxdatalogger.solarmax;

import java.util.function.Function;

public enum SolarMaxCommand {
    BUILD_NUMBER("BDN"),
    COS_PHI_ABSOLUTE("CPA"),
    COUNTRY_CODE("CYC"),
    DATE_TIME("SDAT"),
    DATE("DATE"),
    DAY("DDY"),
    DEBUG_HOST_IP("DRIP"),
    DEVICE_ADDRESS("ADR"),
    DEVICE_STATUS("SYS"),
    DEVICE_TYPE("TYP"),
    ENABLE_DIAGNOSIS("DIAG"),
    ENABLE_ETHERNET("ETH"),
    ENERGY_DAY("KDY", "v1", v -> v / 10f, v -> String.valueOf(Math.round(v * 100))),
    ENERGY_LAST_DAY("KLD"),
    ENERGY_LAST_MONTH("KLM"),
    ENERGY_LAST_YEAR("KLY"),
    ENERGY_MONTH("KMT"),
    ENERGY_TOTAL("KT0"),
    ENERGY_YEAR("KYR"),
    ERROR_1_DAY("E1D"),
    ERROR_1_EC_1("E11"),
    ERROR_1_EC_2("E12"),
    ERROR_1_HOUR("E1h"),
    ERROR_1_MINUTE("E1m"),
    ERROR_1_MONTH("E1M"),
    ERROR_LOG_ENTRY_0("EL00"),
    ERROR_LOG_ENTRY_1("EL01"),
    ERROR_LOG_ENTRY_2("EL02"),
    ERROR_LOG_ENTRY_N("EL%02d"),
    ERROR_N_DAY("E%dD"),
    ERROR_N_EC_1("E%d1"),
    ERROR_N_EC_2("E%d2"),
    ERROR_N_HOUR("E%dh"),
    ERROR_N_MINUTE("E%dm"),
    ERROR_N_MONTH("E%dM"),
    FAN_STATE_REGISTER("FSR"),
    FAN("FAN"),
    FREQUENCY_POWER_LIMITATION("PLF"),
    FREQUENCY("TNF"),
    GREAT_BRITAIN_STANDARD("GBS"),
    GRID_CONNECTION_POINT("GCP"),
    GRID_PERIOD("TNP"),
    HOUR("THR"),
    I_AC_L1("IL1", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_AC_L2("IL2", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_AC_L3("IL3", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_AC("IAC", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_DC_L1("ID01", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_DC_L2("ID02", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    I_DC_L3("ID03", null, v -> v / 100f, v -> String.valueOf(Math.round(v / 100f))),
    INSTALLED_POWER("PIN"),
    IP_ADDRESS("IP4"),
    IRRADIATION("RAD"),
    ITALY_STANDARD("ITN"),
    MAC_ADDRESS("MAC"),
    MAXIMUM_POWER("PAM"),
    MINUTE("TMI"),
    MONTH("DMT"),
    NOMINAL_VOLTAGE("RAV"),
    NUMBER_OF_MODULE_STRINGS("MOD"),
    P_AC_EFF("PAE", null, v -> v / 2f, v -> String.valueOf(Math.round(v / 2f))),
    P_AC("PAC", "v2", v -> v / 2f, v -> String.valueOf(Math.round(v / 2f))),
    PHASE_DIFFERENCE_ANGLE("PFA"),
    PLANT_POWER_RATING("PPO"),
    POWER_RESOLUTION("0.5D"),
    RATED_FREQUENCY("FMO"),
    REACTIVE_POWER_REALTIVE("RPR"),
    REBOOT("RST"),
    RESTORE_FACTORY_SETTINGS("CLX"),
    SERIAL_NUMBER("DIN"),
    SPAIN_STANDARD("ESS"),
    STANDARD_P_SERIES("STDP"),
    STATISTIC_DAY_ENTRY_0("DD00"),
    STATISTIC_DAY_NN("DD%02d"),
    STATISTIC_MONTH_ENTRY_0("DM00"),
    STATISTIC_MONTH_NN("DM%02d"),
    STATISTIC_YEAR_ENTRY_0("DY00"),
    STATISTIC_YEAR_NN("DY%02d"),
    STATUS_ALARM("SAL"),
    STATUS_COMMAND("SCD"),
    STATUS_DSP_TO_PIC("SDP"),
    STATUS_ERROR_1("SE1"),
    STATUS_ERROR_2("SE2"),
    STATUS_LOG_ENTRY_0("EC00"),
    STATUS_LOG_ENTRY_N("EC%02d"),
    STATUS_POWER("SPR"),
    STATUS_SYSTEM("SPC"),
    SUBNET_MASK("SNM"),
    TCP_PORT("TCP"),
    TEMPERATURE_MAXIMUM("TKK", "v5", v -> v, String::valueOf),
    TEMPERATURE_POWER_UNIT_1("TK1"),
    TIME("TIME"),
    U_AC_10_MIN_MEAN_L1("UM1", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC_10_MIN_MEAN_L2("UM2", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC_10_MIN_MEAN_L3("UM3", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC_10_MIN_MEAN("UAM", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC_L1("UL1", "v6", v -> v / 10f, v -> String.valueOf(v / 10f)),
    U_AC_L2("UL2", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC_L3("UL3", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_AC("UAC", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_DC_L1("UD01", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_DC_L2("UD02", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    U_DC_L3("UD03", null, v -> v / 10f, v -> String.valueOf(Math.round(v / 10f))),
    UPDATE_COMMAND("UPD"),
    WARNINGS("SAL"),
    YEAR("DYR"),
    DEVICE_MODE("DMO"),
    NA_SCHUTZ_TEST_MODE("NATM");

    private final String reqText;
    private final String pvOutputParam;
    private final Function<Float, Float> toHumanConverter;
    private final Function<Float, String> toPVOutputConverter;

    private SolarMaxCommand(String reqText) {
        this(reqText, null, null, null);
    }

    private SolarMaxCommand(String reqText, String pvOutputParam, Function<Float, Float> toHumanConverter, Function<Float, String> toPVOutputConverter) {
        this.reqText = reqText;
        this.pvOutputParam = pvOutputParam;
        this.toHumanConverter = toHumanConverter;
        this.toPVOutputConverter = toPVOutputConverter;
    }

    public String getName() {
        return this.name();
    }

    public static SolarMaxCommand getEnumByString(String code) {
        for (SolarMaxCommand e : SolarMaxCommand.values()) {
            if (code.equals(e.reqText)) return e;
        }
        return null;
    }

    public String getPVoutputParam() {
        return pvOutputParam;
    }

    public Function<Float, String> getToPVOutputConverter() {
        return toPVOutputConverter;
    }

    public Function<Float, Float> getToHumanConverter() {
        return toHumanConverter;
    }
}
