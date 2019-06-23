# SolarMaxPVOutput
A java (Spring CLI) based tool to monitor a single SolarMax inverter and upload the statistics to PVOutput

**How to build**

Min requirements: Java 11 and Maven

Build using maven: `mvn clean package`

**How to run**

Copy the jar to any folder you like. In the same folder add an `application.yml` with the 2 values from the PVOutput api:
```yaml
pvoutput:
  apiKey: <your apikey>
  systemId: <your system id>
```

Go to the designated folder and run (on a machine running java11 or later:
`java -jar SolarMaxPVOutput.jar`

Default the client wil look for the SolarMax inverter on `192.168.1.123:12345` you can override these settings by adding the the `application.yml`
```yaml
solrmax:
  clientIp: <inverter ip>
  port: <inverter port>
```