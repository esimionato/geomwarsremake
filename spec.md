# Introduction #

This document explain how the game will be.

# Glossary #

**Game Begin** : The game begin when the player start the game.

**Game** : The game begin when the player start the game and end when he dies. All the actions take place during the game.

**Game End** : The game end when the player’s ship run out of life.

**Player** : The player is the person who play the game.

**Map Area** : The place where all the actions take place. The map area is a big rectangle a little bit bigger than the screen.

**Ship** : The player character in the game. It is represented by some sort of ‘C’. The ship can be controlled by the player. It can move and shot.

**Shot** : The shots are missile fired by the player. The player chose the direction of the missile. The missile start at the middle of the ship location and move away from it.

**Weapon** : The ship’s weapon is the thing that fire the shot. There is many different type of weapon. Different weapon vary by the number of shots they fire and the speed of the shot.

**Ennemy** : The ennemies are other ship that try to destroy the player’s ship. Ennemies are represented by diverse forms and each form has specific attributes.

**Ship’s Life** : The player’s ship has many life. The ship lose a life (and die) when he touch a ennemy. The ship can earn life as he increase his score. When the game begin, the player’s ship has 3 life.

**Ship’s Death** : The player’s ship die when he touch an ennemy. When he die, 1 life is substract from the ship’s life. If the ship life after a death is 0, the game end. When the ship die, all the ennemies on the map desappear. The ship is revive at the position he died.

**Score** : The score represent the number of points that the player earn. When the player reach certain score event are trigger : bonus life, bonus bomb, bigger weapon, acheivement.

**Ship’s Bomb** : The bombs are the biggest weapon of the ship, but they are limited. When a bomb is used, it kill all the ennemies on the map but doesn’t give any point. When the game begin the ship has 3 bomb.

**Acheivement** : The player unlock acheivement by getting big score, getting a certain number of life, not dying until a certain score, etc.

**Victory** : There is no complete victory in Geometry Wars. The player goal is simply to get the biggest score and/or the more acheivements.

# Player’s Controls #

The player use the keyboard to move the ship. There will be 4 possible directions (we could also make 8) and the player will use the 4 arrow key (or ‘wasd’ or something else) to decide in which direction the ship will move. If no key are pressed the ship doesn’t move.

The player use the mouse to fire the shots. When the mouse button is pressed, shot will be fired. Shots will start from the ship location and will move in direction of the mouse position at the moment the shot was fired.

# What happens in a game? #

A game start when the player click ‘start game’ from the menu. At this moment, his ship appear in the middle of the map area. At this moment there is no ennemy on the map, the ship has 3 life and 3 bombs.

As the time passed, ennemy begin to appear in a random pattern. The ennemy spawn rate increase as the time passed so the difficulty too. The goal of the player is to survive by avoiding all the ennemies and making the biggest score by killing all the ennemies he can. To kill the ennemies the player must shot them.

When the ship is in trouble, the player can use a bomb to clear the map area of all the ennemies. Unfortunately, the bombs are limited and don’t give any point so the player must use them only when it’s really needed.

If the ship is touch by an ennemies, the ship die. When the ship die, all the ennemies on the map died, the ship lose 1 life and is revive at the position he died.

If the ship run out of life, the game end and the player score is saved if it’s his highest score.