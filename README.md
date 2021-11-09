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

- To follow the MVC design pattern, we kept the GameModel class from the first milestone. This class deals with the majority of the logic and providese the backbone of the project. Every other class is connected to the GameModel class. 
- The GameFrame class is the view aspect of the MVC design. It creates and displays the gameboard in a JFrame as well as the player pieces, the player stats, and the buttons that the user controls the game with. The GameFrame gets updated frequently depending on the input that it gets from other classes, primarily GameModel.
- There are multiple controller classes for different aspects of the game. The WelcomeController class deals with the initial pop-up window that appears before the game board does and that allows the user to select the number of players. The CardController class deals with any type of control relating to the gameboard tiles. This class handles the pop-ups for when a player is buying a property, paying rent, or passing their turn. The GameController class handles the buttons that are directly in the game board such as roll. It also handles the display when there are changes in game or player state such as when a player goes bankrupt or when there is a winner. 
- The Player and Card classes simply hold attributes regarding the player and the board tiles since those are the mian aspects of the game.
- The GameEvent class simply helps to keep things encapsulated and makes it easier to access certain attributes.

## Known Issues

Occasionally, the icons will disappear for certain, but not all, people running the program. This may be due to screen size but we were unable to confirm if this was the actual cause. Additionally, if a user lands on a property and must pay rent but does not have sufficient funds, they simply go into debt. This does not really affect the gameplay as they will still go bankrupt. Also, their piece will not disappear from the board even though the player is no longer in the game. Finally, icons are currently implemented as class attributes however this is likely to change in future milestones as this is probably not the best design.

## Roadmap Ahead

In Milestone 3, additional Monopoly features will be implemented. These include jail, utility tiles, railroad tiles, property add-ons such as houses and hotels, and in game programmed players. Eventually mutiple versions of Monopoly will be created and the player will have the option to save their games and come back to them later. As well, as the project continues, the code and design will continue to be refined and improved.






