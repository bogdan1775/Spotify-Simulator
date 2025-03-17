package app.user;

import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Merch;
import app.audio.Files.Song;
import app.Admin;
import app.GenreStatistic;
import app.Notification;
import app.Page;
import app.SongWrapped;
import app.audio.Files.Album;
import app.AlbumWrapped;
import app.EpisodeWrapped;
import app.audio.LibraryEntry;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import visit.Visitable;
import visit.Visitor;

/**
 * The type User.
 */
public class User implements Visitable {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;

    private boolean online;
    private Page page;
    private String type;
    private boolean loaded;
    private ArrayList<Song> songRecommendation;
    private ArrayList<Playlist> playlistRecommendation;
    private ArrayList<Merch> merchbought;
    private double revenue;
    private ArrayList<User> subscribers;
    private ArrayList<Notification> notifications;
    private boolean premium;
    @Getter
    private int indexSong;

    /**
     * returneaza recomandarile de melodii
     *
     * @return songRecommendation
     */
    public ArrayList<Song> getSongRecommendation() {
        return songRecommendation;
    }

    /**
     * seteaza recomandarile de melodii
     *
     * @param songRecommendation
     */
    public void setSongRecommendation(final ArrayList<Song> songRecommendation) {
        this.songRecommendation = songRecommendation;
    }

    /**
     * returneaza recomandarile de playlisturi
     *
     * @return playlistRecommendation
     */
    public ArrayList<Playlist> getPlaylistRecommendation() {
        return playlistRecommendation;
    }

    /**
     * seteaza recomandarile de playlisturi
     *
     * @param playlistRecommendation
     */
    public void setPlaylistRecommendation(final ArrayList<Playlist> playlistRecommendation) {
        this.playlistRecommendation = playlistRecommendation;
    }

    /**
     * returneaza produsele cumparate
     *
     * @return merchbought
     */
    public ArrayList<Merch> getMerchbought() {
        return merchbought;
    }

    /**
     * seteaza produsele cumparate
     *
     * @param merchbought
     */
    public void setMerchbought(final ArrayList<Merch> merchbought) {
        this.merchbought = merchbought;
    }

    /**
     * returneaza venitul
     *
     * @return revenue
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * seteaza venitul
     *
     * @param revenue
     */
    public void setRevenue(final double revenue) {
        this.revenue = revenue;
    }

    /**
     * returneaza abonatii
     *
     * @return subscribers
     */
    public ArrayList<User> getSubscribers() {
        return subscribers;
    }

    /**
     * seteaza abonatii
     *
     * @param subscribers
     */
    public void setSubscribers(final ArrayList<User> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * returneaza notificarile
     *
     * @return notifications
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    /**
     * seteaza notificarile
     *
     * @param notifications
     */
    public void setNotifications(final ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * returneaza daca utilizatorul este premium sau nu
     *
     * @return premium
     */
    public boolean getPremium() {
        return premium;
    }

    /**
     * seteaza daca utilizatorul este premium sau nu
     *
     * @param premium
     */
    public void setPremium(final boolean premium) {
        this.premium = premium;
    }

    /**
     * returneaza playlisturile utilizatorului
     *
     * @return playlists
     */
    public void setIndexSong(final int indexSong) {
        this.indexSong = indexSong;
    }

    /**
     * returneaza daca utilizatorul este incarcat sau nu
     *
     * @return loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * seteaza daca utilizatorul este incarcat sau nu
     *
     * @param loaded
     */
    public void setLoaded(final boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * tipul utilizatorului
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * seteaza tipul utilizatorului
     *
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * returneaza pagina utilizatorului
     *
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * seteaza pagina utilizatorului
     *
     * @param page
     */
    public void setPage(final Page page) {
        this.page = page;
    }

    /**
     * returneaza daca utilizatorul este online sau nu
     *
     * @return the online
     */
    public boolean getOnline() {
        return online;
    }

    /**
     * seteaza daca utilizatorul este online sau nu
     *
     * @param online
     */
    public void setOnline(final boolean online) {
        this.online = online;
    }

    /**
     * returneaza playerul utilizatorului
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }


    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        online = true;
        page = new Page("Home");
        type = "normal";

        loaded = false;
        songRecommendation = new ArrayList<>();
        playlistRecommendation = new ArrayList<>();
        merchbought = new ArrayList<>();
        revenue = 0;
        subscribers = new ArrayList<>();
        notifications = new ArrayList<>();
        premium = false;
        indexSong = 0;
    }

    /**
     * Search array list.
     *
     * @param filters the filters
     * @param type2    the type
     * @return the array list
     */
    public ArrayList<String> search(final Filters filters, final String type2) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type2);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Select string.
     *
     * @param itemNumber the item number
     * @return the string
     */
    public String select(final int itemNumber) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }

        if (searchBar.getLastSearchType().equals("artist")) {
            this.getPage().setTypePage(selected.getName());
            return "Successfully selected %s's page.".formatted(selected.getName());
        }

        if (searchBar.getLastSearchType().equals("host")) {
            this.getPage().setTypePage(selected.getName());
            return "Successfully selected %s's page.".formatted(selected.getName());
        }

        return "Successfully selected %s.".formatted(selected.getName());
    }

    /**
     * Load string.
     *
     * @return the string
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("album") && !searchBar.getLastSearchType().equals("song")
                && ((AudioCollection) searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        if (player.getSource() != null) {
            for (Song song : player.getSource().getSongsListened()) {
                player.getSongsListened().add(song);
                if (this.getPremium()) {
                    player.getSongsPremium().add(song);
                } else {
                    player.getSongsFree().add(song);
                }
            }

            player.getSource().setSongsListened(new ArrayList<Song>());

            for (Episode episode : player.getSource().getEpisodesListened()) {
                player.getEpisodesListened().add(episode);
            }

            player.getSource().setEpisodesListened(new ArrayList<Episode>());

            for (String name : player.getSource().getHostListened()) {
                player.getHostListened().add(name);
            }

            player.getSource().setHostListened(new ArrayList<String>());

        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Play pause string.
     *
     * @return the string
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Repeat string.
     *
     * @return the string
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> {
                repeatStatus = "no repeat";
            }
            case REPEAT_ONCE -> {
                repeatStatus = "repeat once";
            }
            case REPEAT_ALL -> {
                repeatStatus = "repeat all";
            }
            case REPEAT_INFINITE -> {
                repeatStatus = "repeat infinite";
            }
            case REPEAT_CURRENT_SONG -> {
                repeatStatus = "repeat current song";
            }
            default -> {
                repeatStatus = "";
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Shuffle string.
     *
     * @param seed the seed
     * @return the string
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Forward string.
     *
     * @return the string
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Backward string.
     *
     * @return the string
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Like string.
     *
     * @return the string
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        // adaugat si pentru album
        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        return "Like registered successfully.";
    }

    /**
     * Next string.
     *
     * @return the string
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Prev string.
     *
     * @return the string
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Create playlist string.
     *
     * @param name      the name
     * @param timestamp the timestamp
     * @return the string
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Add remove in playlist string.
     *
     * @param id the id
     * @return the string
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switch playlist visibility string.
     *
     * @param playlistId the playlist id
     * @return the string
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Show playlists array list.
     *
     * @return the array list
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follow string.
     *
     * @return the string
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type2 = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type2.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();

        return "Playlist followed successfully.";
    }

    /**
     * Gets player stats.
     *
     * @return the player stats
     */
    public PlayerStats getPlayerStats() {
        return player.getStats();
    }

    /**
     * Show preferred songs array list.
     *
     * @return the array list
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Gets preferred genre.
     *
     * @return the preferred genre
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap" };
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Simulate time.
     *
     * @param time the time
     */
    public void simulateTime(final int time) {
        player.simulatePlayer(time);
    }

    /**
     * returneaza searchbarul utilizatorului
     */
    public SearchBar getSearchbar() {
        return searchBar;
    }

    /**
     * realizeaza accept
     */
    @Override
    public String accept(final Visitor visitor) {
        return visitor.visit(this);
    }

    /**
     * scade numarul de like-uri si follow-uri
     *
     * @param user
     */
    public static void decrease(final User user) {
        // decrementare numar like-uri melodii la care a dat like
        for (Song song : user.getLikedSongs()) {
            for (Song song2 : Admin.getSongsList()) {
                if (song2.getName().equals(song.getName())) {
                    int likes = song2.getLikes();
                    likes--;
                    song2.setLikes(likes);
                }
            }
        }

        // decrementare numar follow pentru playlist la care a dat follow
        for (User user2 : Admin.getUserList()) {
            for (Playlist playlist : user2.getPlaylists()) {
                for (Playlist playlist2 : user.getFollowedPlaylists()) {
                    if (playlist2.getName().equals(playlist.getName())) {
                        playlist.decreaseFollowers();
                    }
                }

            }
        }
    }

    /**
     * adauga melodiile ascultate in historicul userului
     *
     * @param user
     */
    public static void addSongs(final User user) {
        if (user.getPlayer().getSource() != null) {
            for (Song song : user.getPlayer().getSource().getSongsListened()) {
                user.getPlayer().getSongsListened().add(song);
                if (user.getPremium()) {
                    user.getPlayer().getSongsPremium().add(song);
                } else {
                    user.getPlayer().getSongsFree().add(song);
                }
            }

            user.getPlayer().getSource().setSongsListened(new ArrayList<Song>());
        }
    }

    /**
     * adauga episoadele ascultate in historicul userului
     *
     * @param user
     */
    public static void addEpisodes(final User user) {
        if (user.getPlayer().getSource() != null) {
            for (Episode episode : user.getPlayer().getSource().getEpisodesListened()) {
                user.getPlayer().getEpisodesListened().add(episode);
            }
            user.getPlayer().getSource().setEpisodesListened(new ArrayList<Episode>());

            for (String name : user.getPlayer().getSource().getHostListened()) {
                user.getPlayer().getHostListened().add(name);
            }
            user.getPlayer().getSource().setHostListened(new ArrayList<String>());
        }
    }

    /**
     * calculeaza numarul de listenings pentru fiecare melodie
     *
     * @param user
     */
    public static List<SongWrapped> calculateListeningsSongs(final User user2) {
        List<SongWrapped> songs = new ArrayList<>();
        for (Song song : user2.getPlayer().getSongsListened()) {
            // se creeaza un song de tipul SongWrapped
            SongWrapped songWrapped = new SongWrapped();
            songWrapped.setName(song.getName());
            songWrapped.setArtist(song.getArtist());
            songWrapped.setListenings(1);
            songWrapped.setAlbum(song.getAlbum());
            int ok = 0;

            for (SongWrapped songWrapped1 : songs) {
                // daca melodia exista deja in lista, se incrementeaza numarul de listenings
                if (songWrapped1.getName().equals(songWrapped.getName())
                        && songWrapped1.getArtist().equals(songWrapped.getArtist())) {
                    songWrapped1.setListenings(songWrapped1.getListenings() + 1);
                    ok = 1;
                    break;
                }
            }

            // daca melodia nu exista in lista, se adauga
            if (ok == 0) {
                songs.add(songWrapped);
            }
        }

        for (int i = 0; i < songs.size() - 1; i++) {
            for (int j = i + 1; j < songs.size(); j++) {
                if (songs.get(i).getName().equals(songs.get(j).getName())
                        && songs.get(i).getArtist().equals(songs.get(j).getArtist())
                        && songs.get(i).getAlbum().equals(songs.get(j).getAlbum())) {
                    songs.get(i).setListenings(songs.get(i).getListenings() / 2);
                }
            }
        }

        // se sorteaza lista de melodii in functie de numarul de listenings
        Collections.sort(songs, (song1, song2) -> {
            if (song1.getListenings() == song2.getListenings()) {
                return song1.getName().compareTo(song2.getName());
            } else {
                return song2.getListenings() - song1.getListenings();
            }
        });

        return songs;
    }

    /**
     * calculeaza numarul de listenings pentru fiecare gen
     * @param user2
     * @return
     */
    public static List<GenreStatistic> topGenres(final User user2) {
        List<String> genres = new ArrayList<>();
        List<GenreStatistic> genreStatistics = new ArrayList<>();

        // creez o lista cu genurile ascultate
        for (Song song : user2.getPlayer().getSongsListened()) {
            if (!genres.contains(song.getGenre())) {
                genres.add(song.getGenre());
            }
        }

        for (String genre : genres) {
            int listenings = 0;

            // calculez numarul de listenings pentru fiecare gen
            for (Song song : user2.getPlayer().getSongsListened()) {
                if (song.getGenre().equals(genre)) {
                    listenings += 1;
                }
            }
            GenreStatistic genreStatistic = new GenreStatistic();
            genreStatistic.setGenre(genre);
            genreStatistic.setListenings(listenings);
            genreStatistics.add(genreStatistic);

        }

        // sortez lista de genuri in functie de numarul de listenings
        Collections.sort(genreStatistics, (genre1, genre2) -> {
            if (genre1.getListenings() == genre2.getListenings()) {
                return genre1.getGenre().compareTo(genre2.getGenre());
            } else {
                return genre2.getListenings() - genre1.getListenings();
            }
        });

        return genreStatistics;
    }

    /**
     * calculeaza numarul de listenings pentru fiecare album
     *
     * @param user
     */
    public static List<AlbumWrapped> calculateListeningsAlbum(final User user2) {
        List<Album> albums = new ArrayList<>();
        for (Album album : Admin.getAlbumList()) {
            int listenings = 0;
            // calculez numarul de listenings pentru fiecare album
            for (Song song : user2.getPlayer().getSongsListened()) {
                if (song.getAlbum().equals(album.getName()) && song.getArtist().equals(album.getOwner())) {
                    listenings += 1;
                }
            }

            album.setListenings(listenings);

            // adaug in lista albumele care au cel putin un listening
            if (album.getListenings() > 0) {
                albums.add(album);
            }
        }

        // lista cu albumele care au acelasi nume combinat
        List<AlbumWrapped> albumWrappeds = new ArrayList<>();
        for (Album album : albums) {
            int ok = 0;
            for (AlbumWrapped albumWrapped : albumWrappeds) {
                if (albumWrapped.getName().equals(album.getName())) {
                    albumWrapped.setListenings(albumWrapped.getListenings() + album.getListenings());
                    ok = 1;
                    break;
                }
            }
            if (ok == 0) {
                AlbumWrapped albumWrapped = new AlbumWrapped();
                albumWrapped.setName(album.getName());
                albumWrapped.setListenings(album.getListenings());
                albumWrappeds.add(albumWrapped);
            }
        }

        // sortez lista de albume in functie de numarul de listenings
        Collections.sort(albumWrappeds, (album1, album2) -> {
            if (album1.getListenings() == album2.getListenings()) {
                return album1.getName().compareTo(album2.getName());
            } else {
                return album2.getListenings() - album1.getListenings();
            }
        });

        return albumWrappeds;
    }

    /**
     * calculeaza numarul de listenings pentru fiecare episode si creeaza o lista cu ele
     * @param user2
     * @return episodes
     */
    public static List<EpisodeWrapped> calculateTopEpisodes(final User user2) {
        List<EpisodeWrapped> episodes = new ArrayList<>();

        for (Episode episode : user2.getPlayer().getEpisodesListened()) {
            int ok = 0;
            // daca episodul exista deja in lista, se incrementeaza numarul de listenings
            for (EpisodeWrapped episodeWrapped : episodes) {
                if (episodeWrapped.getName().equals(episode.getName())) {
                    episodeWrapped.setListenings(episodeWrapped.getListenings() + 1);
                    ok = 1;
                }
            }

            // daca episodul nu exista in lista, se adauga
            if (ok == 0) {
                EpisodeWrapped episodeWrapped2 = new EpisodeWrapped();
                episodeWrapped2.setName(episode.getName());
                episodeWrapped2.setListenings(1);
                episodes.add(episodeWrapped2);
            }

        }

        // sortez lista de episoade in functie de numarul de listenings
        Collections.sort(episodes, (episode1, episode2) -> {
            if (episode1.getListenings() == episode2.getListenings()) {
                return episode1.getName().compareTo(episode2.getName());
            } else {
                return episode2.getListenings() - episode1.getListenings();
            }
        });

        return episodes;
    }

}
