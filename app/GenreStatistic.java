package app;

public final class GenreStatistic {
    private String genre;
    private int listenings;

    public GenreStatistic(final String genre, final int listenings) {
        this.genre = genre;
        this.listenings = listenings;
    }

    public GenreStatistic() {
        this.genre = null;
        this.listenings = 0;
    }

    public GenreStatistic(final String genre) {
        this.genre = genre;
        this.listenings = 0;
    }

    public String getGenre() {
        return genre;
    }

    public int getListenings() {
        return this.listenings;
    }

    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }

    /**
     * creste numarul de ascultari
     */
    public void addListenings(final int listeningss) {
        this.listenings += listeningss;
    }
}
