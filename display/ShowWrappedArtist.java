package display;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public final class ShowWrappedArtist {
    private Map<String, Integer> topAlbums = new LinkedHashMap<>();
    private Map<String, Integer> topSongs = new LinkedHashMap<>();
    private List<String> topFans = new ArrayList<>();
    private int listeners;

    public Map<String, Integer> getTopSongs() {
        return topSongs;
    }

    public void setTopSongs(final Map<String, Integer> topSongs) {
        this.topSongs = topSongs;
    }

    public Map<String, Integer> getTopAlbums() {
        return topAlbums;
    }

    public void setTopAlbums(final Map<String, Integer> topAlbums) {
        this.topAlbums = topAlbums;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(final int listeners) {
        this.listeners = listeners;
    }

    public List<String> getTopFans() {
        return topFans;
    }

    public void setTopFans(final List<String> topFans) {
        this.topFans = topFans;
    }
}
