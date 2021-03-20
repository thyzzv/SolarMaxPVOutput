package nl.vonkit.solardatalogger;

import lombok.extern.slf4j.Slf4j;
import nl.vonkit.solardatalogger.pvoutput.PvOutputClient;
import nl.vonkit.solardatalogger.pvoutput.PvOutputRequest;
import nl.vonkit.solardatalogger.sma.SmaClient;
import nl.vonkit.solardatalogger.solarmax.SolarMaxClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DataLogger {

    private final SolarMaxClient solarMaxClient;
    private final SmaClient smaClient;
    private final PvOutputClient pvOutputClient;

    private final ConcurrentLinkedQueue<BaseResponse> responses = new ConcurrentLinkedQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    DataLogger(SolarMaxClient solarMaxClient,
               SmaClient smaClient,
               PvOutputClient pvOutputClient) {
        this.solarMaxClient = solarMaxClient;
        this.smaClient = smaClient;
        this.pvOutputClient = pvOutputClient;
    }

    @Scheduled(cron = "${solarmax.cron}")
    void requestSolarMaxData() {
        log.info("Starting request Solarmax");

        CompletableFuture.supplyAsync(solarMaxClient::request, executor)
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete((response, error) -> {
                    if (error == null) {
                        log.info("Got response: {}", response);
                        response.ifPresent(responses::add);
                    } else {
                        log.error("Something when wrong while executing solarmax-request", error);
                    }
                });

        log.info("Finished request Solarmax");

    }
    @Scheduled(cron = "${sma.cron}")
    void requestSmaData() {
        log.info("Starting request SMA");

        CompletableFuture.supplyAsync(smaClient::request, executor)
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete((response, error) -> {
                    if (error == null) {
                        log.info("Got response: {}", response);
                        response.ifPresent(responses::add);
                    } else {
                        log.error("Something when wrong while executing sma-request", error);
                    }
                });

        log.info("Finished request SMA");

    }

    @Scheduled(cron = "${pvoutput.cron}")
    void postData() {
        BaseResponse response = responses.peek();
        log.info("Running for response {}", response);
        if (response != null) {
            try {
                postToPVOutPut(response);
                responses.remove(response);
            } catch (Exception e) {
                log.error("Error sending request to PVOutput", e);
            }
        }
        log.info("finished for response {}", response);
    }

    private void postToPVOutPut(final BaseResponse res) {
        PvOutputRequest request = new PvOutputRequest(res.getResponseValues(), res.getDateTime(), res.getSystemId());
        log.info("PVOutput status request: {}", request);
        pvOutputClient.postStatus(request);
    }
}
