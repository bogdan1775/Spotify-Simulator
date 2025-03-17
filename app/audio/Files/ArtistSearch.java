package app.audio.Files;

import app.audio.LibraryEntry;

public final class ArtistSearch extends LibraryEntry {
    private String type;

    public ArtistSearch(final String name, final String type) {
        super(name);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

}
