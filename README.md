# RandomSongApp
This is a simple android app that once connected to the api and is already authenticated using my own
spotify account to allow the user to access spotify's music library. For the code to work correctly
the following must be done:

1. INSTALL THE SPOTIFY APP
    This can be done by just looking up Spotify in the Play store and downloading it.

2. DOWNLOAD THE SPOTIFY ANDROID SDK FROM GITHUB:
    In the following link: https://github.com/spotify/android-sdk/releases
    v0.7.2 (The latest) and unzip the downloaded file.

3. ADD THE APP REMOTE SDK
    Import the file that was unzipped as a module into the IDE (Android Studio)
    New > Module
    In the New Module window, choose Import .JAR/AAR Package. Click Next.
    Press the "..." button and locate the spotify-app-remote-release-version.aar
    under the app-remote-lib folder in the unzipped bundle. Click Open and keep
    spotify-app-remote as the project name.
    Click Finish to import the .aar into your imported build of this project.

    Dependencies and import of dependencies are already included.

    After this and inputting own client ID app should be good to go!
    Displaying a simple Play button and choosing a random song from the Discover Weekly playlist.
