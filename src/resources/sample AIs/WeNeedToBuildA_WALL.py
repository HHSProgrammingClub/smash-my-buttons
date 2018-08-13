AIName = "> Brawl Ice Climbers"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "W'all"

import random
attackMode = "approach"
counter = 0
def switchMode(player, enemy):
    global attackMode
    if abs(player.getX() - enemy.getX()) < 1.25:
        attackMode = "combo"
        if enemy.getDamage() > 70:
            attackMode = "lethal"
    if abs(player.getX() - enemy.getX()) > 1.25 or \
       abs(player.getY() - enemy.getY()) > 1.75:
        attackMode = "approach"
    if player.getY() > 5 or player.getX() > 12 or player.getX() < 0.5:
        attackMode = "recovery"
    if attackMode != "recovery" and enemy.getY() > 6\
       or enemy.getX() > 14 or enemy.getX() < 0:
        attackMode = "edgeguard"
def moveTowards(player, enemy):
    global counter
    if player.getX() > enemy.getX():
        player.moveLeft()
    else:
        player.moveRight()
    if counter % 20 == 0 or player.getY() > enemy.getY() + 1:
        player.jump()

def moveAway(player, enemy):
    global counter
    if player.getX() > enemy.getX():
        player.moveRight()
    else:
        player.moveLeft()
    if counter % 20 == 0 or player.getY() > enemy.getY() + 1:
        player.jump()

def loop(player, enemy):
    global counter
    global attackMode
    counter += 1
    hd = abs(player.getX() - enemy.getX())
    vd = abs(player.getY() - enemy.getY())
    switchMode(player, enemy)
    if attackMode == "approach":
        moveTowards(player, enemy)
        if hd > 2.5 and hd < 4 and vd < 2 and abs(player.getX() - 6.25) < 2:
            player.signature()
        elif hd < 2.5:
            rand = random.random()
            if rand < 0.4:
                player.proj()
            elif rand < 0.75:
                player.signature()
            elif abs(6.25 - player.getX()) < 3:
                player.recover()
    elif attackMode == "lethal":
        moveTowards(player, enemy)
        if hd < 1.1:
            moveAway(player, enemy)
            player.smash()
    elif attackMode == "combo":
        moveTowards(player, enemy)
        if hd < 1.1 and enemy.getY() > player.getY():
            player.tilt()
        elif hd > 1.1 and enemy.getY() > player.getY():
            player.jab()
        elif enemy.getY() < player.getY():
            rand = random.random()
            player.jump()
            if player.getYVel() < -1:
                if rand < 0.4:
                    moveAway(player, enemy)
                    player.smash()
                elif rand < 1:
                    player.jab()
    elif attackMode == "edgeguard":
        if player.getX() < 11 and player.getX() > 1.5:
            moveTowards(player, enemy)
        if player.getX() > 10 or player.getX() < 2.5:
            player.proj()
