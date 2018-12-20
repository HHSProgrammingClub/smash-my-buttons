AIName = "Scrub Ryu"
AIAuthor = "LGTYQZ"
AITargetCharacter = "Jimmy" # Choose from Jack, Birboi, Cam, Edgewardo, and W'all

def loop(player, enemy):
	if player.getX() > enemy.getX():
		player.moveLeft()
	else:
		player.moveRight()
	if abs(player.getX() - enemy.getX()) < 1:
		player.recover()
