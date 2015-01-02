Mutibo – A movie quiz game

The app starts with an animated splash screen. The animation consists of the name Mutibo drifting
to the middle of the screen (unfortunately you can't see it in my video because my emulator is too
slow). The main screen shows three buttons: Log in, Show moviesets and Start Game. After a
successful login, an Intentservice is started which refreshes the data in the ContentProvider with the
data from the Spring-based service. The synchronization scheme is kept relatively simple since the
new data always replaces the old data. There are four different entities: MovieSet, Movie, Rating
and User. All the movie sets are loaded into the ContentProvider and replace existing movie sets.
The movie titles are a static list. Only the movie titles used in the movie sets are loaded into the
ContentProvider. The average rating per movie set is also loaded into the ContentProvider. The
average is calculated in a query in the cloud service. All ratings entered by the user in a game are
also stored in the ContentProvider. The synchronization service sends the ratings to the cloud
service. If a user has rated the same movie set before, the cloud service will replace the old rating
with the new one. In this fashion, only a one-way synchronization is needed: the movie set data
from the cloud service completely replaces the movie set data in the ContentProvider and the
ratings in the ContentProvider replace the ratings in the cloud service. There is no risk of update
conflicts because the ratings are stored per movieset and per user. A different user can't update the
same rating record.
When a new game is started, a query in the ContentProvider selects 10 moviesets at random (using
the randomizer RandomInteger in the package util. The database in the cloud service contains a
very long list of movie titles. Only the titles used in movie sets are loaded into the ContentProvider.
The original idea behind the separate entity for movie titles was that the administrator could add
new movie sets, selecting titles from the provided list. Unfortunately I did not have time to
complete that. The user can ask for a hint at most three times during one game. The hint is shown in
a text field at the bottom of the screen. The user can submit an answer for each question once during
the game. After submitting, the explanation is shown at the bottom of the screen (if a hint was
shown, it will replace the hint). After that the user can optionally rate the movie set by clicking in
the rating bar. When the game is over, the results are stored in the ContentProvider. When the user
leaves the game with the Back-button, the synchronization service is started again which will send
the ratings and the score to the cloud service.
I developed the app in Android Studio because it has integrated support for Gradle which also
means that the app has the new project structure introduced with Android Studio. The Spring-based
service was developed in Eclipse and uses MySql as a database. I have a lot of experience with
developing applications for relational databases, so I felt comfortable with MySql.