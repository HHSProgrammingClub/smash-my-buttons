AIName = "Super OP AI"
AIAuthor = "Teh most 1337 hax0r (Ben)"
AITargetCharacter = "Birboi" # Choose from Jack, Birboi, Cam, Edgewardo, and W'all

def loop(player, enemy):
	# Your AI code goes here!
	if player.getY() > 4:
		player.jump()
		
	if player.getX() > enemy.getX():
		player.moveLeft()
	elif player.getX() < enemy.getX():
		player.moveRight()

	if -1 < player.getX() - enemy.getX() < 1:
		player.signature()
