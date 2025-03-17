package display;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ShowWrappedUser {
    private Map<String, Integer> topArtists = new LinkedHashMap<>();
    private Map<String, Integer> topGenres = new LinkedHashMap<>();
    private Map<String, Integer> topSongs = new LinkedHashMap<>();
    private Map<String, Integer> topAlbums = new LinkedHashMap<>();
    private Map<String, Integer> topEpisodes = new LinkedHashMap<>();

    public Map<String, Integer> getTopArtists() {
        return topArtists;
    }

    public void setTopArtists(final Map<String, Integer> topArtists) {
        this.topArtists = topArtists;
    }

    public Map<String, Integer> getTopGenres() {
        return topGenres;
    }

    public void setTopGenres(final Map<String, Integer> topGenres) {
        this.topGenres = topGenres;
    }

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

    public Map<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(final Map<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

}
