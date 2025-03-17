package app.audio.Files;

import java.util.ArrayList;
import app.audio.Collections.AudioCollection;

public final class Album extends AudioCollection {
    private String name;
    private String owner;
    private int releaseYear;
    private String description;
    private ArrayList<Song> songs = new ArrayList<>();
    private int likes;
    private int listenings;

    public int getListenings() {
        return listenings;
    }

    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }

    public Album(final String name) {
        super(name, null);
        likes = 0;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(final int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }
    @Override
    public boolean containsTrack(final AudioFile track) {
        return songs.contains(track);
    }
}
