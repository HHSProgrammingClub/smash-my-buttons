import random
import math
AIName = "JackTheRipper"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Jack"

attackMode = "attack"

def distance(x1, y1, x2, y2):
    return math.sqrt(math.pow(x1 - x2, 2) + math.pow(y1 - y2, 2))

def loop(player, enemy):
    global attackMode
    if attackMode == "attack":
        if abs(player.getX() - enemy.getX()) < 5:
            if player.getX() > enemy.getX():
                player.moveLeft()
            else:
                player.moveRight()
        if player.getY() > enemy.getY() + 1.4:
            player.jump()
            
        if abs(player.getX() - enemy.getX()) < 0.2 and \
           abs(player.getY() - enemy.getY()) < 0.75:
            player.jab()
        elif abs(player.getX() - enemy.getX()) < 0.4 and \
           abs(player.getY() - enemy.getY()) < 0.75 and \
           enemy.getDamage() < 80:
            player.tilt()
        elif abs(player.getX() - enemy.getX()) < 0.7 and \
           abs(player.getY() - enemy.getY()) < 0.75 and \
           enemy.getDamage() > 40:
            player.smash()
        elif random.random() < 0.04 and abs(player.getX() - enemy.getX()) < 5:
            player.proj()
        elif distance(player.getX(), player.getY(),
                      enemy.getX(), enemy.getY()) > 5:
            player.signature()

        if player.getY() > 6:
            attackMode = "recovery"
    elif attackMode == "recovery":
        if player.getX() > 7:
            player.moveLeft()
        else:
            player.moveRight()
        if not player.jumped():
            player.jump()
        elif not player.recovered():
            player.recover()

        if abs(7 - player.getX()) < 4 and player.getY() <= 6:
            attackMode = "attack"
