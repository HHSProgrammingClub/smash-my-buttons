# This imports some other functions that I can use.
import random
import math
AIName = "JackTheRipper"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Jack"

# A variable for storing attack mode.
attackMode = "attack"

# A function for returning distance.
def distance(x1, y1, x2, y2):
    return math.sqrt(math.pow(x1 - x2, 2) + math.pow(y1 - y2, 2))

def loop(player, enemy):
    # This is how you access variables outside a function.
    global attackMode
    if attackMode == "attack":
        # Follow them, within a certain range.
        if abs(player.getX() - enemy.getX()) < 5:
            if player.getX() > enemy.getX():
                player.moveLeft()
            else:
                player.moveRight()
        # Jump if they're up above.
        if player.getY() > enemy.getY() + 1.4:
            player.jump()
        # Jab if they're real close.
        if abs(player.getX() - enemy.getX()) < 0.2 and \
           abs(player.getY() - enemy.getY()) < 0.75:
            player.jab()
        # Tilt if they're a bit farther away and <80%.
        elif abs(player.getX() - enemy.getX()) < 0.4 and \
           abs(player.getY() - enemy.getY()) < 0.75 and \
           enemy.getDamage() < 80:
            player.tilt()
        # Smash if they're on the far side and damage 
        elif abs(player.getX() - enemy.getX()) < 0.7 and \
           abs(player.getY() - enemy.getY()) < 0.75 and \
           enemy.getDamage() > 40:
            player.smash()
        # If they're far enough away, throw some cups.
        elif random.random() < 0.04 and abs(player.getX() - enemy.getX()) < 5:
            player.proj()
        # If they're REALLY far away, call em up.
        elif distance(player.getX(), player.getY(),
                      enemy.getX(), enemy.getY()) > 5:
            player.signature()
        # If I'm below the stage, switch to recovery mode.
        if player.getY() > 6:
            attackMode = "recovery"
    elif attackMode == "recovery":
        # Try to go to the center of the stage.
        if player.getX() > 7:
            player.moveLeft()
        else:
            player.moveRight()
        # Jump and/or recover
        if not player.jumped():
            player.jump()
        elif not player.recovered():
            player.recover()
        # If we're back on stage, go into attack mode!
        if abs(7 - player.getX()) < 4 and player.getY() <= 6:
            attackMode = "attack"
