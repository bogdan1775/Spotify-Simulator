package app.audio.Files;

import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioFile extends LibraryEntry {
    private final Integer duration;
    private int listenings;

    /**
     * creste numarul de ascultari
     */
    public void increaseListenings() {
        this.listenings++;
    }

    /**
     * seteaza numarul de ascultari
     *
     * @param listeings
     */
    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }

    public AudioFile(final String name, final Integer duration) {
        super(name);
        this.duration = duration;
        this.listenings = 0;
    }
}
