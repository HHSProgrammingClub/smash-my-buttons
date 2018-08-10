AIName = "Allahu Akbar!"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Cam"

import random
counter = 0
attackMode = "combo"
jabCounter = 0
def switchMode(player, enemy):
    global attackMode
    if abs(player.getX() - enemy.getX()) < 2.5:
        if attackMode == "recovery":
            print("Heya")
        attackMode = "combo"
    if abs(player.getX() - enemy.getX()) > 2.5:
        attackMode = "space"
    if player.boosted():
        attackMode = "setup"
    if enemy.currentStateName() == "hitstun" and \
       enemy.currentStateDurationLeft() > 1:
        attackMode = "kill"
    if player.getY() > 6 or player.getX() > 12 or player.getX() < 0.5:
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
            if abs(player.getX() - enemy.getX()) < 1.75 and jabCounter < 7:
                player.jab()
                jabCounter += 1
            elif abs(player.getX() - enemy.getX()) < 1.5:
                player.tilt()
                jabCounter = 0
            elif abs(player.getX() - enemy.getX()) < 1.75:
                player.jab()
    elif attackMode == "space":
        moveTowards(player, enemy)
        if 5 > abs(player.getX() - enemy.getX()) > 3 and random.random() < 0.2:
            player.proj()
        elif 2 < abs(player.getX() - enemy.getX()) < 3 and \
            abs(player.getX() - 6.25) < 3.5:
            player.smash()
    elif attackMode == "setup":
        moveTowards(player, enemy)
        if abs(player.getX() - enemy.getX()) < 3 and \
           abs(player.getY() - enemy.getY()) < 1.25:
            player.smash()
    elif attackMode == "kill":
        moveTowards(player, enemy)
        if abs(player.getX() - enemy.getX()) < 0.75 and \
           abs(player.getY() - enemy.getY()) < 0.75:
            player.recover()
    elif attackMode == "recovery":
        if player.getX() > 6.25:
            player.moveLeft()
        else:
            player.moveRight()
        if not player.jumped():
            player.jump()
        elif not player.recovered():
            player.recover()
        
    counter += 1
    switchMode(player, enemy)
    #print("THE POLICE")
