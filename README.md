# Monopoly

by Andre Hazim, Hussein Elmokdad, Jack Mackinnon, and Cassidy Pacada

## General Information

As of Milestone 4, this program simulates the completed version of Monopoly including a GUI, utilties, jail, automated players, a save and load feature, and the ability to play a custom Spongebob version of the game. When the player runs the program and a previous save file exists, the program will send out a prompt asking if the user would like to load that previous save file. If they choose to do so, the program loads the most recently saved game. If there is no previous save file or if the user decides not to load a previous game, the program will send a prompt asking if the user would like the play the Original or the Spongebob version of the game as well as another prompt asking the player how many real players and bot players they would like in the game. It will then load an new game with the desired property names and colours. 

## User Manual

To run the program, the player must have Java JDK 11 installed. Once the executable file is run, a window will pop up with a menu asking the user to select the number of players. Once the desired number of players has been selected, the game board will appear with Player 1's stats in the side bar.

Initially, the player has two buttons enabled. One button to roll and one button to save the game. To start their turn, the player will need to press the roll button and their piece will automatically move to the correct spot. Once they have rolled, the buy button and the next turn button is enabled. The next turn button simply changes turns to the next player. The buy button sends a prompt asking the user to confirm that they would like to buy the property they landed on. If a player lands on a property that is already owned, they will be notified that they must pay rent to the owner and the rent will automatically be deducted from their balance. If the player has rolled doubles, they may roll again, otherwise they must select Next Turn. If a player lands on the "Go to Jail" tile, they will automatically be transported to the Jail square and they must either roll doubles or pay a fine to get out. The automated bot players will complete their turns on their own. 

## Deliverables

##### Monopoly Source Files and Jar File
- This includes the executable file to actually run the game as well as the code that was written for this milestone.
##### UML Class Diagrams
- These diagrams depict how each of the classes interact with each other as well as their attributes.
##### Sequence Diagrams
- These diagrams depict the logic behind certain events that occur during the program. The sequence diagrams in this program demonstrate what happens when a player rolls the dice, buys a property, pays rent, wins the game, or goes bankrupt.

## Design Decisions

Many design changes were made in Milestone 4. 

- The general MVC format was kept with GameModel acting as the model and GameFrame acting as the View. CardController was renamed because it no longer really acts as  a controller but more as class that deals with announcements and popups for the player. 
- GameController and WelcomeController are the Controller portion of the MVC design as they take user input and call the appropriate GameModel method based on what a user selects.
- The play() function was refactored into multiple smaller functions because it was difficult to understand and to keep track of how each aspect of the game worked. 
- In order to save the game, an XML file is created using XMLEncoder for each active player containing all of their information. These files are saved in a directory. In order to load a save file the program goes through the directory and deserializes each player xml file to a player object and places them back onto the board.
- The way in which the board is loaded (both for the custom version and for the original version) was refactored to be similar to how the save files are loaded. Each tile is saved into an xml file which in turn is saved into a directory (one directory for the original version and one directory for the custom version). Depending on which version is chosen, the respective directory is looped through and each tile is deserialized into a card object and placed on the board. 
- Certain classes have empty default constructors which was important to keep because XMLEncoder needs them. Even though they seem to have no purpose, the encoder will break if they're not there.

## Known Issues

If the user has a screen size that is too small, some of the board tiles may appear squished or the player icons may not appear as clearly as they would on a larger screen. 

## Roadmap Ahead

No Roadmap. We're done. :)





