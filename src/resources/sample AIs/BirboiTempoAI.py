import math
AIName = "DestroyerOfWorlds"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Birboi"

import random
counter = 0
attackMode = "combo"
jabCounter = 0
def distance(x1, y1, x2, y2):
    return math.sqrt(math.pow(x1 - x2, 2) + math.pow(y1 - y2, 2))
def switchMode(player, enemy):
    global attackMode
    if abs(player.getX() - enemy.getX()) < 1.5:
        if attackMode == "recovery":
            print("Heya")
        attackMode = "combo"
    if abs(player.getX() - enemy.getX()) > 1.5:
        if attackMode == "recovery":
            print("Heya")
        #print("Hola")
        if enemy.getDamage() < 100:
            attackMode = "space"
        else:
            attackMode = "kill"
    else:
        pass
    if player.getY() > 6 or player.getX() > 11 or player.getX() < 1:
        attackMode = "recovery"
def moveTowards(player, enemy):
    global counter
    if player.getX() > enemy.getX():
        player.moveLeft()
    else:
        player.moveRight()
    if counter % 20 == 0 or player.getY() > enemy.getY() + 1:
        player.jump()
def loop(player, enemy):
    global counter
    global attackMode
    global jabCounter
    print(attackMode)
    if attackMode == "combo":
        moveTowards(player, enemy)
        if enemy.getY() > player.getY() + 1:
            player.signature()
        else:
            if abs(player.getX() - enemy.getX()) < 1 and jabCounter < 7:
                player.jab()
                jabCounter += 1
            else:
                player.jump()
                if player.getYVel() < -1:
                    player.tilt()
                jabCounter = 0
    elif attackMode == "space" or attackMode == "kill":
        moveTowards(player, enemy)
        if abs(player.getX() - enemy.getX()) > 4.5 and random.random() < 0.2 \
           and abs(player.getX() - 6.25) < 3.5:
            player.proj()
        elif abs(player.getX() - enemy.getX()) < 3 and \
             abs(player.getY() - enemy.getY()) < 1:
            player.smash()
    elif attackMode == "recovery":
        if player.getX() > 6.25:
            player.moveLeft()
        else:
            player.moveRight()
        if not player.jumped():
            player.jump()
        elif not player.recovered():
            player.recover()
        else:
            player.smash()
        
    counter += 1
    switchMode(player, enemy)
    #print("THE POLICE")
