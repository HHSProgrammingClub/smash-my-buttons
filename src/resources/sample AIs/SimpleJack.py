AIName = "Samurai Jack"
AIAuthor = "L G T Y Q Z"
AITargetCharacter = "Jack" # Choose from Jack, Birboi, Cam, Edgewardo, and W'all

def loop(player, enemy):
	# First we gotta check to see if they're onstage.
	# We can do this magic in Python:
	if 11 > player.getX() > 2:
		# If it's true, then they're onstage.
		# Let's follow them around.
		# If they're to the right, go right...
		if player.getX() < enemy.getX():
			player.moveRight()
		else:
			player.moveLeft()
		# If they're close up...
		if abs(player.getX() - enemy.getX()) < 1.5:
			# And at low damage, tilt.
			if enemy.getDamage() < 80:
				player.tilt()
			# High damage? Smash!
			else:
				player.smash()
		# If they're far away (but not too far), throw coffee.
		elif 6 > abs(player.getX() - enemy.getX()) > 2.5:
			player.proj()
	else:
		# If the top condition is false,
		# then they're offstage.
		# We gotta get back on!
		
		# If they're right of center, move left.
		if player.getX() > 6:
			player.moveLeft()
		# Otherwise, move right.
		else:
			player.moveRight()
		# If you've already used the jump,
		# Recover to gain height.
		if player.jumped():
			player.recover()
		player.jump()
