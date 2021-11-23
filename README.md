# Monopoly

by Andre Hazim, Hussein Elmokdad, Jack Mackinnon, and Cassidy Pacada

## General Information

As of Milestone 2, this program simulates a modified version of Monopoly complete with a GUI. Not all game features have been implemented yet but the user is able to choose between 2 to 4 players and each player is able to roll their dice, buy or pass on properties, and pay rent to other players should they land on an owned property. Whenever one of these events occurs in the game, the event announcement is made through a pop-up window. The players stats are shown in the side bar and the board and the player pieces are shown in the central part of the GUI frame. The game ends when all but one player is bankrupt.

## User Manual

To run the program, the player must have Java JDK 11 installed. Once the executable file is run, a window will pop up with a menu asking the user to select the number of players. Once the desired number of players has been selected, the game board will appear with Player 1's stats in the side bar.

The player has two initial buttons. One button to roll and one button to move on to the next player's turn. At the start of the turn, the only button enabled will be the roll button. The user can presst the button to move their piece. Once they have finished their roll and landed on the tile, they will be promopted with a window on whether to buy the property or to pass if the property is not already owned. They may make their selection using the given buttons. If the property is already owned, they will be notified that they must pay rent to the owner and the rent will automatically be deducted from their balance. Afterwards they may either select the pass button to move on to the next player's turn or, if they hvae rolled doubles, they may roll the dice again.

## Deliverables

##### Monopoly Source Files and Jar File
 - This includes the executable file to actually run the game as well as the code that was written for this milestone.
##### UML Class Diagrams
 - These diagrams depict how each of the classes interact with each other as well as their attributes.
##### Sequence Diagrams
 - These diagrams depict the logic behind certain events that occur during the program. The sequence diagrams in this program demonstrate what happens when a player rolls the dice, buys a property, pays rent, wins the game, or goes bankrupt.

## Design Decisions

#### Class Design Decisions

- We have chosen to make an enum for our cards to determine what type of property they are which helps us reduce the number of classes we need.
- To follow the MVC design pattern, we kept the GameModel class from the first milestone. This class deals with the majority of the logic and providese the backbone of the project. Every other class is connected to the GameModel class. 
- The GameFrame class is the view aspect of the MVC design. It creates and displays the gameboard in a JFrame as well as the player pieces, the player stats, and the buttons that the user controls the game with. The GameFrame gets updated frequently depending on the input that it gets from other classes, primarily GameModel.
- There are multiple controller classes for different aspects of the game. The WelcomeController class deals with the initial pop-up window that appears before the game board does and that allows the user to select the number of players. The CardController class deals with any type of control relating to the gameboard tiles. This class handles the pop-ups for when a player is buying a property, paying rent, or passing their turn. The GameController class handles the buttons that are directly in the game board such as roll. It also handles the display when there are changes in game or player state such as when a player goes bankrupt or when there is a winner. 
- The Player and Card classes simply hold attributes regarding the player and the board tiles since those are the mian aspects of the game.
- The GameEvent class simply helps to keep things encapsulated and makes it easier to access certain attributes.

## Known Issues

There is an issue with the ai that they can roll as many doubles as they want to we are trying to get that fixed in the nexted iterations. After the player gets out of jail their turn will automatically go to the next player after they get prompted to buy the property. The update player icon method can be fixed to not have stings but enums to determine what player they are.

## Roadmap Ahead

In Milestone 4 we will have to implement a save and load feature to the monopoly game. As well as add custom street names and currencies to our game.





