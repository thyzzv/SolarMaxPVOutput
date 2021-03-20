package nl.vonkit.solardatalogger.solarmax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("solarmax")
@Validated
public class SolarMaxConfig {
    @NonNull
    private List<String> request;
    @NonNull
    private String clientIp;
    @NonNull
    private int clientPort;
    @NonNull
    private String pvOutputSystemId;
}
