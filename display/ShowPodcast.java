package display;

import java.util.ArrayList;

public final class ShowPodcast {
    private String name;
    private ArrayList<String> episodes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final ArrayList<String> episodes) {
        this.episodes = episodes;
    }
}
