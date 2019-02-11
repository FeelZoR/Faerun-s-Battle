# Faerun's Battle
This game is the subject of a project we have to do while studying OOP in Java. I modified it a lot so it meets my need
and is a bit more balanced than the original game.

## How to play
The game is pretty simple. You have a board with 5, 10 or 15 cells. On the left, you have the **blue** castle, and on the
right, the **red** castle. Each of them start with 3 resources, earning one more at the beginning of each turn.

Blue plays first, then comes red turn. During a turn, you can do multiple actions :

- Train a new unit, and add it to the training queue. It will be trained when enough resources are available (and all
previous units in the queue have been trained).
- Use a skill, which can change how the game goes. They're very important in the balance of the game as they can
completely reverse the game.
- Get information about a warrior or a skill.

If red and blue troops are standing on the same cell, a fight will begin. Troops will fight until the enemy army had
completely died, and will then continue to move forward. Once they reach the cell next to the enemy's castle, the game
ends, and they win.

## Warriors
### Dwarf
***Cost:*** 2

***Description:*** They are regular warriors with twice as much defense.

### Dwarf Leader
***Cost:*** 3

***Description:*** They are dwarfs with twice as much defense.

### Elf
***Cost:*** 2

***Description:*** They are regular warriors with twice as much strength.

### Elf Leader
***Cost:*** 3

***Description:*** They are elves with twice as much strength.

### Healer
***Cost:*** 5

***Description:*** When they attack, they have 40% chance to heal the most injured ally by 20hp or all their allies by 10hp.

### Paladin
***Cost:*** 4

***Description:*** If their level of provocation is below their team's average, they provoke their enemies.
They also have 200hp and more defense.

### Recruiter
***Cost:*** 5

***Description:*** They have 15% chances to change their target's team color.
They only have 60hp.

## Skills
### Bargaining
***Cost:*** Variable, starting at 5 and increasing every time it's used.

***Description:*** Increase the number of resources gained each turn by one.

### Intensive Training
***Cost:*** 3 if not active, 2 otherwise

***Description:*** Increase the base strength of newly trained troops by 2, and an additional bonus of 1 every time this
skill is used again while active.

### Motivating Call
***Cost:*** 2

***Description:*** Motivates troops, giving them the strength to move twice during this turn.

### Negotiations
***Cost:*** 4

***Description:*** Negotiate training costs, reducing them by one during 3 turns, with a minimum of 1.