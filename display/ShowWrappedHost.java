package display;


import java.util.LinkedHashMap;
import java.util.Map;

public final class ShowWrappedHost {
    private Map<String, Integer> topEpisodes = new LinkedHashMap<>();
    private int listeners;

    public Map<String, Integer> getTopEpisodes() {
        return topEpisodes;
    }

    public void setTopEpisodes(final Map<String, Integer> topEpisodes) {
        this.topEpisodes = topEpisodes;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(final int listeners) {
        this.listeners = listeners;
    }
}
