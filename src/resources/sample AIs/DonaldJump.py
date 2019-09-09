AIName = "Donald Jump"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "W'all"

def moveTowards(player, enemy):
    if player.getX() > enemy.getX():
        player.moveLeft()
    else:
        player.moveRight()

def moveAway(player, enemy):
    if player.getX() > enemy.getX():
        player.moveRight()
    else:
        player.moveLeft()

def enemyNearEdge(enemy):
    return (enemy.getX() < 3 or enemy.getX() > 10)

def enemyOffstage(enemy):
    return (enemy.getX() < 1 or enemy.getX() > 12)


def loop(player, enemy):
    distanceX = abs(player.getX() - enemy.getX())
    distanceY = abs(player.getY() - enemy.getY())
    if 11 > player.getX() > 2:
        moveTowards(player, enemy)
        if enemy.getY() < player.getY():
            player.jump()
        if 4 > distanceX > 2 and \
        enemy.currentStateDuration() > 0.25 and \
        8 > player.getX() > 5 and \
        distanceY < 2:
            player.signature()
        elif 4 > distanceX > 2 and \
        9 > player.getX() > 4 and player.getY() > 0:
            player.recover()
        elif distanceX < 2 and \
        player.getY() - enemy.getY() > -2:
            if enemyOffstage(enemy):
                player.proj()
            elif enemyNearEdge(enemy):
                if enemy.getDamage() < 30:
                    player.jab()
                else:
                    moveAway(player, enemy)
                    player.smash()
            else:
                if distanceX < 1.3:
                    moveAway(player, enemy)
                    player.smash()
                else:
                    player.proj()
    else:
        if player.getX() > 6:
            player.moveLeft()
        else:
            player.moveRight()
        if player.getY() > 0 and player.jumped():
            player.recover()
        player.jump()
