package nl.vonkit.solarmaxdatalogger.solarmax;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SolarMaxClient {
    private final SolarMaxConfig config;

    public SolarMaxClient(SolarMaxConfig config) {
        this.config = config;
        log.info("Starting with:  {} ", config);

    }

    public Optional<SolarMaxResponse> request(SolarMaxRequest request) {
        Optional<SolarMaxResponse> response;
        try (Socket socket = createSocket()) {
            log.info("Executing request {}", request);
            socket.connect(new InetSocketAddress(config.getClientIp(), config.getClientPort()), 1000);
            socket.setSoTimeout(5000);
            log.info("Connected");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.write(request.toString());
            out.flush();
            log.info("request written to output");
            char[] responseBuffer = new char[1024];
            int read = in.read(responseBuffer);
            log.info("response read {} characters", read);
            response = Optional.of(SolarMaxResponse.from(Arrays.copyOfRange(responseBuffer, 0, read)));
        } catch (SocketTimeoutException e) {
            //timeout is ok (solar max is not longer available)
            response = Optional.empty();
        } catch (Exception e) {
            log.error("Could not request data from solarmax", e);
            response = Optional.empty();
        }
        log.info("Returning: {}", response);
        return response;
    }

    protected Socket createSocket() {
        return new Socket();
    }
}
