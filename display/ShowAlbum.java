package display;

import java.util.ArrayList;

public final class ShowAlbum {
    private String name;
    private ArrayList<String> songs = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }

    public void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }
}
