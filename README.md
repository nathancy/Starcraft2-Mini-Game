# Starcraft2-Mini-Game

The objective is to destroy all of the units and avoid the buildings. There are 20 units and 20 buildings. Destroying units increase points while hitting buildings decrease points. The player loses the game if the points drop to zero. With each additional kill, the aura of the mothership becomes larger.


[![SC2 Video](doc/SC2_youtube.PNG)](https://www.youtube.com/watch?v=--b-9HrKK6w "SC2 Mini game - Click to Watch!")

# Features
#### Player
  * Move two dimensionally around map (`w, s, a, d`)
  * Three player skins (battlecruiser, leviathan, and mothership)
  * Increasing aura with each kill
  * Unit hit detection
    * Ability to shoot projectile to kill units (`k`)
    * Ability to kill units by contact
  
#### Units
  * Rotating aura
  * Randomly moves across map
  * Unique sound for each unit 
  * Unique death animation
  * +1 point for each kill
  
#### Buildings
  * Rotating aura
  * Teleports player to random location on contact
  * Unique contact sound
  * -1 point for each contact

#### Sound
Two music tracks (`0` and `9` to switch music)
  
#### Skins
There are three skins available to the user. The user can switch between the skins with `1`, `2`, or `3`.

# Development 
The sound and image capability is powered by the [EZ Graphics Library](http://www2.hawaii.edu/~dylank/ics111/). The game is built on two classes. The Buildings class controls all functions and features relating to buildings while the Units class controls all features involving the units.