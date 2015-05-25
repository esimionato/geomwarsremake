# Introduction #

This document describe all there is to know about the ship (the player’s character) in Geometry War.

# Control #

The player control the ship with the mouse and the keyboard. He use the keyboard to move the ship around and he use the mouse to aim and fire.

# Weapon #

The ship has 3 different weapons.

**Weapon 1** : Small weapon that the ship begin the game with. It fires 2 bullet very close to each other. In fact the 2 bullets look like they are only one. The bullet doesn’t get away from each other. This weapon is only availabe when the player’s score is between 0 and 10 000.
  * Frequency : normal
  * Number of shots : 2
  * Starting position of the shots : side to side (not on each other)
  * Shots moving away from each other : false
  * Score required : between 0 and 10000

**Weapon 2** : Fire very frequently and the shots move really fast. The shots are forming a small cone. This is only available once the player’s score reach 10 000.
  * Frequency : fast
  * Number of shots : 3
  * Starting position of the shots : one above each other
  * Shots moving away from each other : true with small angle
  * Score required : at least 10000.
  * Special : The shot in the middle move a little bit faster than the 2 shots on his side.

**Weapon 3** : Big weapon that fire at the same frequence than the first one but fire 6 shot instead of 2. The shots are forming a medium cone. This weapon is only available once the player’s score reach 10 000.
  * Frequency : normal
  * Number of shots : 5
  * Starting position of the shots : one above each other
  * Shots moving away form each other : true with medium/small angle
  * Socre required : at least 10000.

Once the player’s score is 10 000. The weapon 2 and 3 are choose randomly by the program. The player doesn’t have any control on which weapon is use at a moment. Still weapon 2 is as powerfull as weapon 3 so it’s not a big deal.

The speed of the shots depend on the speed of the ship at the moment they were fired. The speed of the ship is add to the speed of the shots.
Before, I talk about an angle to describe the speed at which the shots are getting away from each other, but it’s not very true. It would be better describe as a perpendicular speed to the direction of the shot in the middle. A behavior that confirm this is that if you fire in the opposite direction of your movement the shots will move slower but they will still get away from each other as fast as if you fired them while not moving. So they seems to form a bigger angle.

# Movement #

The player control the ship deplacement with the keyboard. The ship always move at a constant speed. There is no acceleration or deceleration. Four (or eight) directions are possible.

# Life #

The ship start with 3 life and has a maximum of 9 life. The ship lose 1 life when he touches an ennemy. The ship earn 1 life for each 75000 points the player make.

# Bomb #

The ship start with 3 bombs and has a maximum of 9 bombs. The ship earn a bomb each time the player make 100000 points.

When a player use a bomb, all the ennemy on the map are cleared. The explosion look… like an explosion. It start at the ship position and quickly expands in circle and kills all the ennemies it encounter.

# Death #

The ship die when he touches an ennemy. At this moment, he lose 1 life, all the ennemies on the map are cleared and he is revive at the position he died (if he still has life left).

After he revive the ship is immune to everything and all ennemies hitting it die. This immunity is represented as a small white circle around the ship.

# Visual #

The ship look like a big white C. When the ships move there is a small trail of particule behind it.