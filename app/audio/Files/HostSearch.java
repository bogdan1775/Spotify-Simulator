package app.audio.Files;

import app.audio.LibraryEntry;

public final class HostSearch extends LibraryEntry {
    private String type;

    public HostSearch(final String name, final String type) {
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
