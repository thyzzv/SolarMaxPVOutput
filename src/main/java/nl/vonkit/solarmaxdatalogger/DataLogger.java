package nl.vonkit.solarmaxdatalogger;

import lombok.extern.slf4j.Slf4j;
import nl.vonkit.solarmaxdatalogger.pvoutput.PVOutputClient;
import nl.vonkit.solarmaxdatalogger.pvoutput.PVOutputRequest;
import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxClient;
import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxConfig;
import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxRequest;
import nl.vonkit.solarmaxdatalogger.solarmax.SolarMaxResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DataLogger {

    private final SolarMaxClient solarMaxClient;
    private final PVOutputClient pvOutputClient;
    private final List<String> requestFields;

    private final ConcurrentLinkedQueue<SolarMaxResponse> solarMaxResponses = new ConcurrentLinkedQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    DataLogger(SolarMaxClient solarMaxClient, PVOutputClient pvOutputClient, SolarMaxConfig config) {
        this.solarMaxClient = solarMaxClient;
        this.pvOutputClient = pvOutputClient;
        this.requestFields = config.getRequest();
    }

    @Scheduled(cron = "${solarmax.cron}")
    void requestData() {
        log.info("Running for req {}", requestFields);

        CompletableFuture.supplyAsync(() -> solarMaxClient.request(new SolarMaxRequest(requestFields)), executor)
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete((response, error) -> {
                    if (error == null) {
                        log.info("Got response: {}", response);
                        response.ifPresent(solarMaxResponses::add);
                    } else {
                        log.error("Something when wrong while executing solarmax-request", error);
                    }
                });

        log.info("finished for req {}", requestFields);

    }

    @Scheduled(cron = "${pvoutput.cron}")
    void postData() {
        SolarMaxResponse response = solarMaxResponses.peek();
        log.info("Running for response {}", response);
        if (response != null) {
            try {
                postToPVOutPut(response);
                solarMaxResponses.remove(response);
            } catch (Exception e) {
                log.error("Error sending request to PVOutput", e);
            }
        }
        log.info("finished for response {}", response);
    }

    private void postToPVOutPut(final SolarMaxResponse res) {
        PVOutputRequest request = new PVOutputRequest(res.getResponseValues(), res.getDateTime());
        log.info("PVOutput status request: {}", request);
        pvOutputClient.postStatus(request);
    }
}