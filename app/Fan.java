package app;

public final class Fan {
    private String name;
    private int listenings;

    public String getName() {
        return name;
    }

    public int getListenings() {
        return listenings;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }
}
