AIName = "les hax"
AIAuthor = "1337 c0dr"
AITargetCharacter = "Birboi" # Choose from Jack, Birboi, Cam, Edgewardo, and W'all

def loop(player, enemy):
  
  if abs(player.getX() - enemy.getX()) > 2:
    if player.getX() < enemy.getX():
      player.moveRight()
    else:
      player.moveLeft()
  else:
    player.smash()