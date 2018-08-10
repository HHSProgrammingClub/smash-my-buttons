AIName = "Buffalolololo"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Edgewardo"

import random
counter = 0
attackMode = "ranged"
def loop(player, enemy):
    global counter
    global attackMode
    horizDistance = abs(player.getX() - enemy.getX())
    vertDistance = abs(player.getY() - enemy.getY())
    if attackMode == "ranged":
        if counter % 20 == 0:
            player.jump()
        if vertDistance < 0.75 and horizDistance > 3:
            if counter % 5 == 0:
                if player.getX() > enemy.getX():
                    player.moveLeft()
                else:
                    player.moveRight()
                player.proj()
            else:
                if player.getX() > enemy.getX() and player.getX() < 11:
                    player.moveLeft()
                elif player.getX() < enemy.getX() and player.getX() > 3:
                    player.moveRight()
        elif vertDistance < 0.75 and horizDistance > 1:
            if player.getX() > enemy.getX():
                player.moveLeft()
            else:
                player.moveRight()
            if enemy.getDamage() < 100:
                player.tilt()
            else:
                attackMode = "melee"
        elif vertDistance < 1 and horizDistance < 1:
            attackMode = "melee"
        elif 5 > (enemy.getY() + (enemy.getYVel() ** 2)/2) - \
             player.getY() > 4 and horizDistance < 1:
            # Lookin' for that elusive explosion kill
            player.recover()
    elif attackMode == "melee":
        if player.getX() > enemy.getX():
            player.moveLeft()
        else:
            player.moveRight()
        if player.getY() > enemy.getY():
            player.jump()
        if vertDistance < 0.75 and horizDistance < 1.2:
            if random.random() < 0.5:
                player.jab()
            else:
                player.smash()
        elif horizDistance > 3:
            attackMode = "ranged"
    elif attackMode == "recovery":
        if player.getX() > 7:
            player.moveLeft()
        else:
            player.moveRight()
        if not player.jumped():
            player.jump()
        elif not player.recovered():
            player.recover()
        else:
            player.tilt()
        
        if abs(7 - player.getX()) < 4 and player.getY() <= 6:
            attackMode = "ranged"
    counter += 1
    if player.getY() > 6 or abs(player.getX() - 7) > 5:
        attackMode = "recovery"
    #print("THE POLICE")
