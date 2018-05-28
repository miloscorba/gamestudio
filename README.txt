gamestudio
|
|---client 
|	|--- RunGame.class (main class)
|	|--- CommentRestService.class
|	|--- RatingRestService.class
|	|--- ScoreRestService.class
|
|---entity
|	|--- Comment.class
|	|--- Rating.class
|	|--- Rating.class
|---games 
|	|--- Game.class (interface)
|	|--- hangman 
|		|--- Hangman.class
|	|--- mines 
|	|	|--- consoleui
|	|	|	|--- ConsoleUI.class
|	|	|	|--- UserInterface
|	|	|	|--- WrongFormatException
|	|	|--- core
|	|	|	|--- Clue.class
|	|	|	|--- Field.class
|	|	|	|--- GameState.class
|	|	|	|--- Mine.class
|	|	|	|--- Tile.abstractClass
|	|	|--- settings
|	|	|	|--- Settings.class
|	|	|	|--- TimeWatch.class
|	|	|--- Minesweeper.class
|	|--- mines 
|	|	|--- consoleui
|	|	|	|--- ConsoleUI.class
|	|	|	|--- UserInterface
|	|	|--- core
|	|	|	|--- Direction.class
|	|	|	|--- Field.class
|	|	|	|--- GameState.class
|	|	|	|--- Stone.class
|	|	|	|--- StoneMoveable.class
|	|	|--- settings
|	|	|	|--- Settings.class
|	|	|	|--- TimeWatch.class
|---service
|	|--- AchievementBean.class
|	|--- CommentService.class (interface)
|	|--- CommentServiceImplJPA.class
|	|--- RatingService.class (interface)
|	|--- RatingServiceImplJPA.class
|	|--- ScoreService.class (interface)
|	|--- ScoreServiceImplJPA.class
|---webservice
|	|--- RestApplication.class
|	|--- CommentRestService.class
|	|--- RatingRestService.class
|	|--- ScoreRestService.class