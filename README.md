# looser-taxi
A really simple text quest where it's not so easy to order a taxi and pretty hard to get to your destination if you manage to get the car

To run the game in the console mode please use next command: 
mvn spring-boot:run -Dspring-boot.run.profiles=console

The profile setting was used to leave place for extending the project with other types of user communication - 
for this you'll need to implement UserInputScanner (for all input types - currently there are only inputs for integer and string) and MessageOutputService the way you need
