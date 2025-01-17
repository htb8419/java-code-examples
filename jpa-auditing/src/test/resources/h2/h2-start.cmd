@rem jdbc:h2:tcp://localhost:9092/~/test
@rem java -cp .\h2-2.3.232.jar org.h2.tools.Server -?

java -cp .\h2-2.3.232.jar org.h2.tools.Server -webPort 8082 -tcpPort 9092 -tcpPassword sa -ifNotExists