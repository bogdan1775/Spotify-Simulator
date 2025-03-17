package app;

public final class SongWrapped {
    private String name;
    private String artist;
    private int listenings;
    private String album;

    public SongWrapped(final String name, final String artist, final int listenings) {
        this.name = name;
        this.artist = artist;
        this.listenings = listenings;
    }

    public SongWrapped() {
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(final String album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getListenings() {
        return listenings;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }

    /**
     * compara doua obiecte de tip SongWrapped
     * @param song
     * @return
     */
    public boolean equals(final SongWrapped song) {
        if (song.getName().equals(this.name) && song.getArtist().equals(this.artist)) {
            return true;
        }
        return false;
    }
}
