import random
AIName = "The Tilter"
AIAuthor = "LGTYQZ"
AITargetCharacter = "Jimmy" # Choose from Jack, Birboi, Cam, Edgewardo, and W'all

def loop(player, enemy):
    if 11 > player.getX() > 2:
        if player.getX() > enemy.getX():
            player.moveLeft()
        else:
            player.moveRight()
        player.jump()
    else:
        if player.getX() < 2:
            player.moveRight()
        if player.getX() > 11:
            player.moveLeft()
        if player.jumped():
            player.recover()
        else:
            player.jump()
    if enemy.getDamage() < 70:
        if abs(player.getX() - enemy.getX()) < 1 and \
        abs(player.getY() - enemy.getY()) < 1.5:
            player.tilt()
    else:
        if abs(player.getX() - enemy.getX()) < 1.25 and \
        abs(player.getY() - enemy.getY()) < 1.5:
            if random.random() < 0.5:
                player.jab()
            else:
                player.smash()
