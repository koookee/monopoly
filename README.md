# Monopoly

by Andre Hazim, Hussein Elmokdad, Jack Mackinnon, and Cassidy Pacada

## General Information

As of Milestone 1, this program simulates a text-based version of Monopoly for a default number of four players. Not all game features have been implemented yet but a player is able to roll their dice, buy or pass on properties, and pay rent to other players should they land on an owned property. The game ends when all but one player is bankrupt.

## User Manual

To run the program, the player must have Java JDK 11 installed. Once the executable file is run, the game starts in the console. All four players must be on the same console. Valid commands are prompted and the user can type those commands into the console to play the game.

To start the game the firsst user must type in start. They will be prompted to roll the dice but they can also quit the game or access the help menu whenever they want to by using the quit or help commands. If they choose to roll, they will be given property information. They may choose to check their player status and then buy or pass on the property. Should they land on  a property that is owned by another player the rent will automatically be deducted. The game ends when all players are bankrupt except for one.


## Deliverables

##### Monopoly Source Files and Jar File
 - This includes the executable file to actually run the game as well as the code that was written for this milestone.
##### UML Class Diagrams
 - These diagrams depict how each of the classes interact with each other as well as their attributes.
##### Sequence Diagrams
 - These diagrams depict the logic behind certain events that occur during the program. The sequence diagrams in this program demonstrate what happens when a player rolls the dice, buys a property, pays rent, wins the game, or goes bankrupt.

## Design Decisions

#### Class Design Decisions

- To follow the MVC design pattern, we created a GameModel class that handles the general logic behind the main aspects of the game such as dice rolling, updating player status, and creating the board. Most of the logic in dealing with the user inputted commands goes into the Game class which updates variables and displays output depending on which command words the user inputs. This is the view aspect of the MVC as it calls the GameModel class to know how to update the display. The parser class acts as the controller and receives user inputs that it turns into command words for the Game class to use.
- There is an Interface called GameView in order to properly turn Game into the view to fulfill the V portion of the MVC design pattern.

#### Other Design Decisions

- A hashmap was used to keep track of the card/property that a player is on as this was the easiest way to keep track of all the cards. The cards that affect a player are directly related to their position on the board so using their position as a key to which card to display made the most sense. 

## Known Issues

If a user has rolled and landed on a property that they choose to buy or pass, their turn ends immediately after the command has been inputted rather than giving the user a chance to use the "state" command.

## Roadmap Ahead

In Milestone 2 the game will transition from text-based to being playable within a GUI such that the user can control their piece's movements using their mouse and various buttons. As well, as each Milestone progresses, additional Monopoly features will be implemented. These include jail, utility tiles, railroad tiles, property add-ons such as houses and hotels, and in game programmed players. Eventually mutiple versions of Monopoly will be created and the player will have the option to save their games and come back to them later. As well, as the project continues, the code and design will continue to be refined and improved.






