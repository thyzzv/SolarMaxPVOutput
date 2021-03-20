package nl.vonkit.solardatalogger.sma;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties("sma")
@Validated
public class SmaConfig {
    @NonNull
    private String host;
    @NonNull
    private String right;
    @NonNull
    private String password;
    @NonNull
    private String pvOutputSystemId;
}
