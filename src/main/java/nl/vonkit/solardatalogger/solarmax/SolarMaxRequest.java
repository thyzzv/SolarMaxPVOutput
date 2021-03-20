package nl.vonkit.solardatalogger.solarmax;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class SolarMaxRequest {
    // {(1) + Src(3) + dest(3) + len(2) + |(1) + enconding(3) + body + |(1) + checksum(4) + }(1)


    private static final int PREFIX_LENGTH = 13;
    private static final int SUFFIX_LENGTH = 6;
    private static final String MESSAGE_DELIMITER = ";";
    private static final String MESSAGE_PART_DELIMITER = "|";
    private static final String SOURCE = "FB";
    private static final String DEFAULT_DESTINATION = "00";
    private static final String ENCODING = "64:";

    private final String request;

    public SolarMaxRequest(List<String> requestBody) {
        this(DEFAULT_DESTINATION, requestBody);
    }

    public SolarMaxRequest(String destination, List<String> requestBody) {
        var requestParts = String.join(MESSAGE_DELIMITER, requestBody);
        int length = PREFIX_LENGTH + requestParts.length() + SUFFIX_LENGTH;
        var message = new StringBuilder(length);
        message
                .append(SOURCE).append(MESSAGE_DELIMITER)
                .append(destination).append(MESSAGE_DELIMITER)
                .append(StringUtils.leftPad(Integer.toHexString(length), 2, '0'))
                .append(MESSAGE_PART_DELIMITER)
                .append(ENCODING).append(requestParts)
                .append(MESSAGE_PART_DELIMITER);
        var checkSum = calculateChecksum(message.toString().getBytes());

        this.request = "{" + message + checkSum + '}';
    }

    @Override
    public String toString() {
        return request;
    }

    private static String calculateChecksum(byte[] buf) {
        int crc = 0;
        for (byte b : buf) {
            crc += (int) b;
        }
        return StringUtils.leftPad(Long.toHexString(crc).toUpperCase(), 4, '0');
    }
}
