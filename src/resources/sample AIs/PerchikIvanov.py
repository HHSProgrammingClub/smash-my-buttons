import random
AIName = "Perchik Ivanov"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Cam"

def moveTowards(player, enemy):
    if player.getX() > enemy.getX():
        player.moveLeft()
    else:
        player.moveRight()

def turnTowardsEdge(player):
    if player.getX() > 6:
        player.moveRight()
    else:
        player.moveLeft()

def loop(player, enemy):
    distanceX = abs(player.getX() - enemy.getX())
    distanceY = abs(player.getY() - enemy.getY())
    if 11 > player.getX() > 2:
        moveTowards(player, enemy)
        if distanceX < 1.5 and enemy.getHitstun() > 0.4 \
        and distanceY < 1:
            if random.random() > (enemy.getDamage()/90):
                player.recover()
            else:
                player.tilt()
        elif distanceX < 2.5 and enemy.getHitstun() < 0.1:
            player.smash()
    else:
        if player.getX() > 6:
            player.moveLeft()
        else:
            player.moveRight()
        if player.getY() > -1 and player.jumped():
            player.recover()
        player.jump()
