package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import app.audio.Files.Song;

/**
 * The type Player source.
 */
public class PlayerSource {
    @Getter
    private Enums.PlayerSourceType type;
    @Getter
    private AudioCollection audioCollection;
    @Getter
    private AudioFile audioFile;
    @Getter
    private int index;
    private int indexShuffled;
    private int remainedDuration;
    private final List<Integer> indices = new ArrayList<>();
    private AudioFile adBreak;
    @Getter
    private List<Song> songsListened = new ArrayList<Song>();
    @Getter
    private List<Episode> episodesListened = new ArrayList<Episode>();
    @Getter
    private List<String> hostListened = new ArrayList<String>();

    /**
     * Sets index.
     *
     * @param index the index
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * Sets adBreak.
     *
     * @param adBreak the adBreak
     */
    public void setAdBreak(final AudioFile adBreak) {
        this.adBreak = adBreak;
    }

    /**
     * Gets adBreak.
     *
     * @return the adBreak
     */
    public AudioFile getAdBreak() {
        return adBreak;
    }

    /**
     * Sets remained duration.
     *
     * @param remainedDuration the remained duration
     */
    public void setRemainedDuration(final int remainedDuration) {
        this.remainedDuration = remainedDuration;
    }

    /**
     * Sets songs listened.
     *
     * @param songsListened the songs listened
     */
    public void setSongsListened(final List<Song> songsListened) {
        this.songsListened = songsListened;
    }

    /**
     * Sets episodes listened.
     *
     * @param episodesListened the episodes listened
     */
    public void setEpisodesListened(final List<Episode> episodesListened) {
        this.episodesListened = episodesListened;
    }

    /**
     * Sets hostListened.
     *
     * @param hostListened the hostListened
     */
    public void setHostListened(final List<String> hostListened) {
        this.hostListened = hostListened;
    }

    /**
     * Instantiates a new Player source.
     *
     * @param type      the type
     * @param audioFile the audio file
     */
    public PlayerSource(final Enums.PlayerSourceType type, final AudioFile audioFile) {
        this.type = type;
        this.audioFile = audioFile;
        this.remainedDuration = audioFile.getDuration();

        this.audioFile.increaseListenings();
        if (type == Enums.PlayerSourceType.LIBRARY) {
            songsListened.add((Song) audioFile);
        }

        this.adBreak = null;
    }

    /**
     * Instantiates a new Player source.
     *
     * @param type            the type
     * @param audioCollection the audio collection
     */
    public PlayerSource(final Enums.PlayerSourceType type, final AudioCollection audioCollection) {
        this.type = type;
        this.audioCollection = audioCollection;
        this.audioFile = audioCollection.getTrackByIndex(0);
        this.index = 0;
        this.indexShuffled = 0;
        this.remainedDuration = audioFile.getDuration();

        this.audioFile.increaseListenings();
        if (type == Enums.PlayerSourceType.PLAYLIST || type == Enums.PlayerSourceType.ALBUM) {
            songsListened.add((Song) audioFile);
        } else {
            episodesListened.add((Episode) audioFile);
            hostListened.add(audioCollection.getOwner());
        }

        this.adBreak = null;
    }

    /**
     * Instantiates a new Player source.
     *
     * @param type            the type
     * @param audioCollection the audio collection
     * @param bookmark        the bookmark
     */
    public PlayerSource(final Enums.PlayerSourceType type,
            final AudioCollection audioCollection,
            final PodcastBookmark bookmark) {
        this.type = type;
        this.audioCollection = audioCollection;
        this.index = bookmark.getId();
        this.remainedDuration = bookmark.getTimestamp();
        this.audioFile = audioCollection.getTrackByIndex(index);

        this.audioFile.increaseListenings();
        if (type == Enums.PlayerSourceType.PLAYLIST || type == Enums.PlayerSourceType.ALBUM) {
            songsListened.add((Song) audioFile);

        } else {
            episodesListened.add((Episode) audioFile);
            hostListened.add(audioCollection.getOwner());
        }
        this.adBreak = null;
    }

    /**
     * Gets duration.
     *
     * @return the duration
     */
    public int getDuration() {
        return remainedDuration;
    }

    /**
     * Sets next audio file.
     *
     * @param repeatMode the repeat mode
     * @param shuffle    the shuffle
     * @return the next audio file
     */
    public boolean setNextAudioFile(final Enums.RepeatMode repeatMode,
            final boolean shuffle) {
        boolean isPaused = false;

        if (type == Enums.PlayerSourceType.LIBRARY) {
            if (repeatMode != Enums.RepeatMode.NO_REPEAT) {
                remainedDuration = audioFile.getDuration();
            } else {
                remainedDuration = 0;
                isPaused = true;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE
                    || repeatMode == Enums.RepeatMode.REPEAT_CURRENT_SONG
                    || repeatMode == Enums.RepeatMode.REPEAT_INFINITE) {
                remainedDuration = audioFile.getDuration();
            } else if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
                if (shuffle) {
                    if (indexShuffled == indices.size() - 1) {
                        remainedDuration = 0;
                        isPaused = true;
                    } else {
                        indexShuffled++;

                        index = indices.get(indexShuffled);
                        updateAudioFile();
                        remainedDuration = audioFile.getDuration();
                    }
                } else {
                    if (index == audioCollection.getNumberOfTracks() - 1) {
                        remainedDuration = 0;
                        isPaused = true;
                    } else {
                        index++;
                        updateAudioFile();
                        remainedDuration = audioFile.getDuration();
                    }
                }
            } else if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                if (shuffle) {
                    indexShuffled = (indexShuffled + 1) % indices.size();
                    index = indices.get(indexShuffled);
                } else {
                    index = (index + 1) % audioCollection.getNumberOfTracks();
                }
                updateAudioFile();
                remainedDuration = audioFile.getDuration();
            }
        }

        return isPaused;
    }

    /**
     * Sets prev audio file.
     *
     * @param shuffle the shuffle
     */
    public void setPrevAudioFile(final boolean shuffle) {
        if (type == Enums.PlayerSourceType.LIBRARY) {
            remainedDuration = audioFile.getDuration();
        } else {
            if (remainedDuration != audioFile.getDuration()) {
                remainedDuration = audioFile.getDuration();
            } else {
                if (shuffle) {
                    if (indexShuffled > 0) {
                        indexShuffled--;
                    }
                    index = indices.get(indexShuffled);
                    updateAudioFile();
                    remainedDuration = audioFile.getDuration();
                } else {
                    if (index > 0) {
                        index--;
                    }
                    updateAudioFile();
                    remainedDuration = audioFile.getDuration();
                }
            }
        }
    }

    /**
     * Generate shuffle order.
     *
     * @param seed the seed
     */
    public void generateShuffleOrder(final Integer seed) {
        indices.clear();
        Random random = new Random(seed);
        for (int i = 0; i < audioCollection.getNumberOfTracks(); i++) {
            indices.add(i);
        }
        Collections.shuffle(indices, random);
    }

    /**
     * Update shuffle index.
     */
    public void updateShuffleIndex() {
        for (int i = 0; i < indices.size(); i++) {
            if (indices.get(i) == index) {
                indexShuffled = i;
                break;
            }
        }
    }

    /**
     * Skip.
     *
     * @param duration the duration
     */
    public void skip(final int duration) {
        remainedDuration += duration;
        if (remainedDuration > audioFile.getDuration()) {
            remainedDuration = 0;
            index++;
            updateAudioFile();
        } else if (remainedDuration < 0) {
            remainedDuration = 0;
        }
    }

    private void updateAudioFile() {
        setAudioFile(audioCollection.getTrackByIndex(index));

        audioCollection.getTrackByIndex(index).increaseListenings();

        if (type == Enums.PlayerSourceType.PLAYLIST || type == Enums.PlayerSourceType.ALBUM) {
            songsListened.add((Song) audioFile);
        } else {
            episodesListened.add((Episode) audioFile);
            hostListened.add(audioCollection.getOwner());
        }
    }

    /**
     * Sets audio file.
     *
     * @param audioFile the audio file
     */
    public void setAudioFile(final AudioFile audioFile) {
        this.audioFile = audioFile;
    }

    /**
     * Sets audio collection.
     *
     * @param audioCollection2 the audio collection 2
     */
    public void setAudioCollection(final AudioCollection audioCollection2) {
        this.audioCollection = audioCollection2;
    }

}
