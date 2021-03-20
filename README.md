# SolarMaxPVOutput
A java (Spring CLI) based tool to monitor a single SolarMax inverter and a single SMA inverter and upload the statistics to PVOutput

**How to build**

Min requirements: Java 11 and Maven

Build using maven: `mvn clean package`

**How to run**

Copy the jar to any folder you like. In the same folder add an `application.yml` with the 2 values from the PVOutput api:
```yaml
pvoutput:
  apiKey: <your apikey>
```

Go to the designated folder and run (on a machine running java11 or later:
`java -jar SolarMaxPVOutput.jar`

Default the client wil look for the SolarMax inverter on `192.168.1.123:12345` you can override these settings by adding to the `application.yml`
```yaml
solarmax:
  clientIp: <inverter ip>
  clientPort: <inverter port>
  pvOutputSystemId: <your system id>


  Default the client wil look for the SMA inverter on `http://192.168.1.17` you can override these settings by adding to the `application.yml`
  ```yaml
sma:
  host: <inverter ip>
  password: <inverter user password>
  pvOutputSystemId: <your system id>
```

Other properties you can override:
```yaml
solarmax:
  request: # The request list to send to the inverter
   - PAC
   - TKK
   - KDY
   - UL1
  cron: '0 0/5 6-23 * * *' # the cron for requesting information from the inverter (now every 5 minutes between 6-23)

sma:
  right: (usr/istl)
  cron: '0 0/5 6-23 * * *' # the cron for requesting information from the inverter (now every 5 minutes between 6-23)


pvoutput:
  host: https://pvoutput.org
  cron: '5 0/1 6-23 * * *' # the cron for sendin information to pvoutput (now every 2 minutes between 6-23)

logging: # logging config
  path: logs # the directory relative to the 'root' of the application 
  file: ${logging.path}/solarmax.log # logfile name (+directory)
  pattern.file: '%d{dd-MM-yyyy HH:mm:ss.SSS} [%thread] %-5level %logger{36}.%M - %msg%n' # logfile pattern

```
