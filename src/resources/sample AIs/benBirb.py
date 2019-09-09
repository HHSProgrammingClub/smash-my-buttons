AIName = "Birb, Destroyer of Worlds"
AIAuthor = "Ben"
AITargetCharacter = "Birboi"

def loop(player, enemy):

	# if you're really low, recover
	if player.getY() > 7:
		player.recover()

	# Follow the enemy, but don't go off the edge of the stage
	if enemy.getX() > player.getX() and player.getX() < 12:
		player.moveRight()
	elif enemy.getX() < player.getX() and player.getX() > 0:
		player.moveLeft()
	
	# jump when you've fallen off the stage
	if player.getY() > 5:
		player.jump()
	# otherwise, if you're close to the enemy
	elif abs(player.getX() - enemy.getX()) < 1.3:
		# and off the stage
		if not 0 < enemy.getX() < 12:
			# tilt to hopefully meteor the enemy
			player.tilt()
		# if you are on the stage and the enemy is below you, signature
		elif player.getY() < enemy.getY():
			player.signature()
	# if all else fails, smash
	elif 1 < player.getX() < 11 and player.getY() > 3:
		player.smash()


