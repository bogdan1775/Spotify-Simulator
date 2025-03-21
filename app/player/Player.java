package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Player.
 */
public final class Player {
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    private PlayerSource source;
    @Getter
    private String type;
    private final int skipTime = 90;
    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();
    @Getter
    private List<Song> songsListened;
    @Getter
    private List<Song> songsFree;
    @Getter
    private List<Song> songsPremium;
    @Getter
    private boolean premium;
    @Getter
    private List<Episode> episodesListened;
    @Getter
    private List<String> hostListened;

    public void setType(final String type) {
        this.type = type;
    }

    public void setPremium(final boolean premium) {
        this.premium = premium;
    }

    public void setSongsListened(final List<Song> songsListened) {
        this.songsListened = songsListened;
    }

    public void setSongsFree(final List<Song> songsFree) {
        this.songsFree = songsFree;
    }

    public void setSongsPremium(final List<Song> songsPremium) {
        this.songsPremium = songsPremium;
    }

    public void setPaused(final boolean paused) {
        this.paused = paused;
    }

    public PlayerSource getSource() {
        return source;
    }

    /**
     * Instantiates a new Player.
     */
    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
        songsListened = new ArrayList<Song>();
        episodesListened = new ArrayList<Episode>();
        hostListened = new ArrayList<String>();

        songsFree = new ArrayList<Song>();
        songsPremium = new ArrayList<Song>();
        premium = false;
    }

    /**
     * Stop.
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        if (source != null) {
            for (Song song : source.getSongsListened()) {
                songsListened.add(song);
                if (this.premium) {
                    this.songsPremium.add(song);
                } else {
                    this.songsFree.add(song);
                }

            }
            source.setSongsListened(new ArrayList<Song>());

            for (Episode episode : source.getEpisodesListened()) {
                episodesListened.add(episode);
            }
            source.setEpisodesListened(new ArrayList<Episode>());

            for (String host : source.getHostListened()) {
                hostListened.add(host);
            }
            source.setHostListened(new ArrayList<String>());

        }
        source = null;
        shuffle = false;
    }

    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark;
            currentBookmark = new PodcastBookmark(source.getAudioCollection().getName(),
                    source.getIndex(),
                    source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     * Create source player source.
     *
     * @param type      the type
     * @param entry     the entry
     * @param bookmarks the bookmarks
     * @return the player source
     */
    public static PlayerSource createSource(final String type,
            final LibraryEntry entry,
            final List<PodcastBookmark> bookmarks) {
        if ("song".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(type)) {
            return createPodcastSource((AudioCollection) entry, bookmarks);
        } else if ("album".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.ALBUM, (AudioCollection) entry);
        }

        return null;
    }

    private static PlayerSource createPodcastSource(final AudioCollection collection,
            final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     * Sets source.
     *
     * @param entry      the entry
     * @param sourceType the sourceType
     */
    public void setSource(final LibraryEntry entry, final String sourceType) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        this.type = sourceType;
        this.source = createSource(type, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     * Pause.
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Shuffle.
     *
     * @param seed the seed
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        } else if (source.getType() == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     * Repeat enums . repeat mode.
     *
     * @return the enums . repeat mode
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     * Simulate player.
     *
     * @param time the time
     */
    public void simulatePlayer(final int time) {
        int elapsedTime = time;
        if (!paused) {
            while (elapsedTime >= source.getDuration()) {
                elapsedTime -= source.getDuration();
                if (this.getSource().getType() == Enums.PlayerSourceType.LIBRARY
                        && this.getSource().getAdBreak() != null) {

                    source.getSongsListened().add(((Song) source.getAdBreak()));
                    this.getSource().setAdBreak(null);
                    for (Song song : source.getSongsListened()) {
                        songsListened.add(song);
                        if (this.premium) {
                            this.songsPremium.add(song);
                        } else {
                            this.songsFree.add(song);
                        }
                    }
                    source.setSongsListened(new ArrayList<Song>());
                    source = null;
                    paused = true;
                    break;

                }
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-elapsedTime);
            }

        }
    }

    /**
     * Next.
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     * Prev.
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Skip next.
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(-skipTime);
        }
    }

    /**
     * Skip prev.
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(skipTime);
        }
    }

    /**
     * Gets current audio file.
     *
     * @return the current audio file
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * Gets paused.
     *
     * @return the paused
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Gets shuffle.
     *
     * @return the shuffle
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }

    /**
     * Gets current audio collection.
     *
     * @return the current audio collection
     */
    public AudioCollection getCurrentAudioCollection() {
        if (source == null) {
            return null;
        }
        return source.getAudioCollection();
    }
}
