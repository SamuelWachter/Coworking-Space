# Coworking Space Management System
Eine Applikation zur Verwaltung von Coworking Spaces.
## Aufsetzten - Docker
### Docker
Um die Applikation zu starten, muss Docker installiert sein.
### Docker-Compose
Um die Applikation zu starten, muss Docker-Compose installiert sein.
Anschließend muss die Datei `docker-compose.yml` ausgeführt werden.
Dies kann mit dem Befehl `docker-compose up` gemacht werden.
## Starten und Stoppen - Applikation
### Starten
Die Datenbank kann mit dem Befehl `docker-compose up` gestartet werden.
Daraufhin kann die Applikation mit dem Befehl `mvn spring-boot:run` gestartet werden.
### Stoppen
Die Datenbank kann mit dem Befehl `docker-compose down` gestoppt werden.
Daraufhin kann die Applikation mit `Strg + C` gestoppt werden.
## Testdaten
Testdaten sind in der Datenbank gespeichert. Wenn man das Docker-Compose ausführt, wird die Datenbank mit den Testdaten gefüllt.