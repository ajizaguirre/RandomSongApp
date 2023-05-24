package com.example.randomsongapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "your_client_id"; //private client id. can use your own
    private static final String REDIRECT_URI = "com.yourdomain.ramndomsongapp://callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.play_button);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRandomSong();
            }

            private void playRandomSong() {
                // Get a list of all available tracks.
                mSpotifyAppRemote.playerApi().getPlaylists().get("spotify:playlist:37i9dQZF1DXcBWIqq4r7XQ").getItems().addOnSuccessListener(playlistItems -> {
                    // Get a random track from the list.
                    int randomTrackIndex = (int) (Math.random() * playlistItems.size());
                    Track track = playlistItems.get(randomTrackIndex);

                    // Play the track.
                    mSpotifyAppRemote.playerApi().play(track);
                });
            }

            @Override
            protected void onStart() {
                super.onStart();
                ConnectionParams connectionParams =
                        new ConnectionParams.Builder(CLIENT_ID)
                                .setRedirectUri(REDIRECT_URI)
                                .showAuthView(true)
                                .build();

                SpotifyAppRemote.connect(this, connectionParams,
                        new Connector.ConnectionListener() {

                            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                                mSpotifyAppRemote = spotifyAppRemote;
                                Log.d("MainActivity", "Connected! Yay!");

                                // Now you can start interacting with App Remote
                                connected();

                            }

                            public void onFailure(Throwable throwable) {
                                Log.e("MyActivity", throwable.getMessage(), throwable);

                                // Something went wrong when attempting to connect! Handle errors here
                            }
                        });
            }

            @Override
            protected void onStop() {
                super.onStop();
                SpotifyAppRemote.disconnect(mSpotifyAppRemote);
            }

            private void connected() {
                // Play a playlist
                mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

                // Subscribe to PlayerState
                mSpotifyAppRemote.getPlayerApi()
                        .subscribeToPlayerState()
                        .setEventCallback(playerState -> {
                            final Track track = playerState.track;
                            if (track != null) {
                                Log.d("MainActivity", track.name + " by " + track.artist.name);
                            }
                        });
    }
}