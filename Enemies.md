# Introduction #

This document describe all there is to know about the enemies in Gemometry Wars. The goal of the enemies are to destroy the player's ship. There is many differents type of enemies and each have special abilities.

  * Movement : Describe how the enemies is moving
  * Special : any special ability of the ship
  * Weight : The weight of the enemies. Determine how this enemies react to the attraction field of the attraction hole
  * Kill : Describe how this enemy is killed. If it's not specified it means that this enemy die from one shot touching them.

# Spinning one #

  * Movement : Random, slow, no acceleration.
  * Weight : medium

# Blue lozenge #

  * Movement : Follow the player's ship, constant speed, max speed increase slowly over time, initial max speed slow
  * Weight : medium

# Green square #

  * Movement : Follow the player's ship, very fast acceleration, max speed a bit slower than the player's ship
  * Special : Avoid the shots. They move away when a shot is coming toward them.
  * Weight : medium

# Pink square #

  * Movement : Follow the player's ship, medium acceleration, max speed as fast as the player ship (but their acceleration make it easy to avoid them)
  * Special : When they die they release 3 small pink square (pink square children).
  * Weight : medium

# Pink square children #

  * Movement : Follow the player's ship, no acceleration, max speed very slow
  * Special : Rotate around an imaginary point. It's this point who is moving toward the player's ship.
  * Weight : light

# Attraction hole #

  * Movement : Follow the player's ship, move really really slow.
  * Special : If hit by an shot they start attracting everything around them. The force of the attraction seems only dependant of the distance from the hole.
  * Kill : Unlike other enemies, these one require many shots to hit them before they die. Also, they gain additionnal life when they eat another enemies.
  * Weight : heavy

# Snake #

  * Movement : Follow the player's ship, move slowly, zigzag like a snake
  * Special : Have an invincible tail.
  * Kill : the only way to kill them is to shot their head.
  * Weight : Really heavy

# Attraction hole children #

  * Movement : Follow the player's ship, medium acceleration, max speed 2 times faster than player's ship.
  * Weight : Really light

# Pacman #

  * Movement : Follow the player's ship, fast acceleration, max speed faster than player's ship, only start moving when they are align with the player's ship, stop moving when they are no longer within a certain angle from the direction of the player's ship
  * Special : They have a shield poping up in front of them when a shot is coming toward them.
  * Kill : must shot them in the back or on the side (front won't work because of the shield)
  * Weight : heavy