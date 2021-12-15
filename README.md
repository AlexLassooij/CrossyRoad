# My Personal Project

## Project Description

### What will the application do?
The application will let a user enter a virtual Arcade. Initially they will be able to create or choose an existing
profile and enter a game "signed in" under that profile. Once the user enters the game, they will be able to control
their player's position in order to complete the game. Upon completion, they may proceed to the next level or quit 
the game. Later on, (not yet implemented) players will be able to gain points through completing the game. 

### Who will use it?
Anyone who is looking to have a bit of fun or relax by playing a simple yet engaging game. It is easy to use and 
the user interface is intuitive, so people of all ages and backgrounds will be able to use it.

### Why is this project of interest to you?
Personally, I find that life nowadays gets extremely stressful and people often forget to take some time out of 
their days to have some fun. Therefore, it would be nice to build an application that gives people quick access to a 
variety of games (the long term idea is to build multiple mini-games for this arcade) at their fingertips. 
Additionally, I  enjoyed playing the real Crossy Road game when I was younger, so it is engaging experience to
create a similar game. I believe building such a game is a bit more complex than is required, so it is also a good 
experience for me to get more familiar with Object-Oriented Programming. 

### User Stories:
- As a user, I want to be able to create and add a new profile to the list of players in the Arcade
- As a user, I want to be to choose what player profile I want to play a game with
- As a user, I want to be able to navigate around the game board within the crossy road game
- As a user, I want to be able to choose between proceeding to the next level or quitting the game

### Phase 4: Task 2 
Player moved to X: 600 Y: 600
Thu Nov 25 15:07:21 PST 2021
Car 1 moved to X: 1582 Y: 400
Thu Nov 25 15:07:21 PST 2021
Car 2 moved to X: -182 Y: 0
Thu Nov 25 15:07:21 PST 2021
Car 3 moved to X: 1482 Y: 200
Thu Nov 25 15:07:21 PST 2021
Car 4 moved to X: 1482 Y: 100
Thu Nov 25 15:07:21 PST 2021
Car 5 moved to X: -282 Y: 600
Thu Nov 25 15:07:22 PST 2021
Player moved to X: 600 Y: 700

### Phase 4 : Task 3 
If I had more time, I would definitely try to make my code more coherent. I think that many of my classes are
quite large and could be split into smaller ones that have one specific responsibility. For example, CrossyRoadGame
has many method for generating and replacing cars, which could be moved to a new CarFactory class. One more thing I 
would like to improve is the way that the crossy road game returns to the main arcade screen. Right now, it is done 
by throwing an exception, but I think that there is a better way to achieve this. 