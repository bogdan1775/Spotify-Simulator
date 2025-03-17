package app;

import app.audio.LibraryEntry;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Album;
import app.audio.Files.Announcement;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Merch;
import app.audio.Files.Event;
import app.audio.Files.Song;
import app.player.PlayerSource;
import app.player.PlayerStats;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import app.utils.Enums;
import display.ShowPodcast;
import display.ShowWrappedArtist;
import display.ShowWrappedHost;
import display.ShowWrappedUser;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import app.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


import java.util.Objects;

import visit.Visitor;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Song> songsHistory = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;
    private static final int MONEY = 1000000;
    private static List<Album> albums = new ArrayList<>();
    private static List<Event> events = new ArrayList<>();
    private static List<Merch> merchs = new ArrayList<>();
    private static List<Announcement> announcements = new ArrayList<>();

    private Admin() {
    }

    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
        songsHistory = new ArrayList<>(songs);
    }

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                        episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public static List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public static List<Podcast> getPodcasts() {
        return new ArrayList<>(podcasts);
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            if (user.getOnline()) {
                user.simulateTime(elapsed);
            }
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merchs = new ArrayList<>();
        announcements = new ArrayList<>();
        timestamp = 0;
        songsHistory = new ArrayList<>();
    }

    /**
     * returneaza userii normali care sunt online
     *
     * @return
     */
    public static ArrayList<String> usersOnline() {
        ArrayList<String> result = new ArrayList<>();
        for (User user : users) {
            if (user.getOnline() && user.getType() == "normal") {
                result.add(user.getUsername());
            }

        }

        return result;
    }

    public static List<User> getUserList() {
        return users;
    }

    public static List<Song> getSongsList() {
        return songs;
    }

    public static List<Song> getSongsHistory() {
        return songsHistory;
    }

    public static List<Album> getAlbumList() {
        return albums;
    }

    public static List<Event> getEventList() {
        return events;
    }

    public static List<Merch> getMerchList() {
        return merchs;
    }

    public static List<Podcast> getPodcastList() {
        return podcasts;
    }

    public static List<Announcement> getAnnouncementList() {
        return announcements;
    }

    /**
     * adauga un utilizator
     *
     * @return rezultatul comenzii
     */
    public static String addUser(final CommandInput commandInput) {
        String message;
        String city = commandInput.getCity();
        String username = commandInput.getUsername();
        if (commandInput.getType().equals("artist")) {
            Artist artist = new Artist(username, commandInput.getAge(), city);
            artist.getPage().setTypePage("artist");
            artist.setType("artist");
            Admin.getUserList().add(artist);
            message = "The username " + username + " has been added successfully.";

        } else if (commandInput.getType().equals("user")) {
            User user2 = new User(username, commandInput.getAge(), city);
            user2.setType("normal");
            Admin.getUserList().add(user2);
            message = "The username " + username + " has been added successfully.";

        } else if (commandInput.getType().equals("host")) {
            Host host = new Host(commandInput.getUsername(), commandInput.getAge(), city);
            host.getPage().setTypePage("host");
            host.setType("host");
            Admin.getUserList().add(host);
            message = "The username " + username + " has been added successfully.";

        } else {
            message = "The type " + commandInput.getType() + " doesn't exist.";
        }
        return message;
    }

    /**
     * adauga un album
     *
     * @return
     */
    public static String addAlbum(final CommandInput commandInput) {
        String message;
        for (Album album : Admin.getAlbumList()) {
            if (album.getName().equals(commandInput.getName())
                    && album.getOwner().equals(commandInput.getUsername())) {
                message = commandInput.getUsername() + " has another album with the same name.";
                return message;
            }
        }

        // se verifica daca are de 2 ori o melodiei in album
        for (SongInput songInput : commandInput.getSongs()) {
            int sum = 0;
            for (SongInput songInput2 : commandInput.getSongs()) {
                if (songInput.getName().equals(songInput2.getName())) {
                    sum++;
                }
            }
            if (sum > 1) {
                message = commandInput.getUsername();
                message = message + " has the same song at least twice in this album.";
                return message;
            }
        }

        // creez albumul
        Album album = new Album(commandInput.getName());
        album.setName(commandInput.getName());
        album.setOwner(commandInput.getUsername());
        album.setReleaseYear(commandInput.getReleaseYear());
        album.setDescription(commandInput.getDescription());

        // adaug melodiile in album
        for (SongInput songInput : commandInput.getSongs()) {
            Song song = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist());
            Admin.getSongsList().add(song);
            album.getSongs().add(song);
            Admin.getSongsHistory().add(song);
        }

        Admin.getAlbumList().add(album);

        message = commandInput.getUsername() + " has added new album successfully.";

        Notification notification = new Notification();
        notification.setName("New Album");
        notification.setDescription("New Album from " + commandInput.getUsername() + ".");
        User user = Admin.getUser(commandInput.getUsername());
        for (User user2 : user.getSubscribers()) {
            user2.getNotifications().add(notification);
        }

        return message;
    }

    /**
     * printeaza pagina curenta a unui utilizator
     * folosesc Visitor pattern
     */
    public static String printCurrentPage(final CommandInput commandInput, final User user) {
        String message;
        Visitor visitor = new Page();
        message = user.accept(visitor);
        return message;
    }

    /**
     * adauga un eveniment
     *
     * @param commandInput
     */
    public static String addEvent(final CommandInput commandInput) {
        String message = null;
        for (Event event : Admin.getEventList()) {
            if (event.getName().equals(commandInput.getName())) {
                message = commandInput.getUsername() + " has another event with the same name.";
                return message;
            }
        }

        // verific daca data e valida
        message = Event.verificaData(commandInput);

        if (message != null) {
            return message;
        }

        // creez evenimentul
        Event event = new Event();
        event.setName(commandInput.getName());
        event.setOwner(commandInput.getUsername());
        event.setDescription(commandInput.getDescription());
        event.setDate(commandInput.getDate());

        Admin.getEventList().add(event);
        message = commandInput.getUsername() + " has added new event successfully.";

        // etapa3
        Notification notification = new Notification();
        notification.setName("New Event");
        notification.setDescription("New Event from " + commandInput.getUsername() + ".");
        User user = Admin.getUser(commandInput.getUsername());
        for (User user2 : user.getSubscribers()) {
            user2.getNotifications().add(notification);
        }

        return message;
    }

    /**
     * adauga un merch
     *
     * @param commandInput
     */
    public static String addMerch(final CommandInput commandInput) {
        String message;
        for (Merch merch : Admin.getMerchList()) {
            if (merch.getName().equals(commandInput.getName())) {
                message = commandInput.getUsername() + " has merchandise with the same name.";
                return message;
            }
        }

        // verific pretul
        if (commandInput.getPrice() < 0) {
            message = "Price for merchandise can not be negative.";
            return message;
        }

        // creez merch-ul
        Merch merch = new Merch();
        merch.setName(commandInput.getName());
        merch.setOwner(commandInput.getUsername());
        merch.setDescription(commandInput.getDescription());
        merch.setPrice(commandInput.getPrice());
        Admin.getMerchList().add(merch);
        message = commandInput.getUsername() + " has added new merchandise successfully.";

        // etapa3
        Notification notification = new Notification();
        notification.setName("New Merchandise");
        notification.setDescription("New Merchandise from " + commandInput.getUsername() + ".");
        User user = Admin.getUser(commandInput.getUsername());
        for (User user2 : user.getSubscribers()) {
            user2.getNotifications().add(notification);
        }

        return message;
    }

    /**
     * utitlizatorii online
     *
     * @param commandInput
     * @return
     */
    public static ArrayList<String> getAllUsers(final CommandInput commandInput) {
        ArrayList<String> result = new ArrayList<>();
        for (User user : Admin.getUserList()) {
            if (user.getType().equals("normal")) {
                result.add(user.getUsername());
            }
        }

        for (User user : Admin.getUserList()) {
            if (user.getType().equals("artist")) {
                result.add(user.getUsername());
            }
        }

        for (User user : Admin.getUserList()) {
            if (user.getType().equals("host")) {
                result.add(user.getUsername());
            }
        }

        return result;
    }

    /**
     * sterge un user
     *
     * @param commandInput
     * @return
     */
    public static String deleteUser(final CommandInput commandInput, final User user) {
        String message = null;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        // daca e artist se verifica daca poate fi sters
        message = Artist.deleteArtist(commandInput);
        if (message != null) {
            return message;
        }

        // daca e host se verifica daca poate fi sters
        message = Host.deleteHost(commandInput);
        if (message != null) {
            return message;
        }

        // verificare daca cineva se afla pe pagina lui
        for (User user2 : Admin.getUserList()) {
            if (user2.getType().equals("normal")) {
                if (user2.getPage().getTypePage().equals(user.getUsername())) {
                    message = commandInput.getUsername() + " can't be deleted.";
                    return message;
                }
            }
        }

        // sterg albumele lui
        List<Album> albums = Admin.getAlbumList();
        Album album;
        for (int k = 0; k < albums.size(); k++) {
            album = albums.get(k);
            if (album.getOwner().equals(commandInput.getUsername())) {
                for (Song song : album.getSongs()) {
                    Admin.getSongsList().remove(song);
                }
                Artist.deleteLikeSong(album);

                Admin.getAlbumList().remove(album);
            }
        }

        // sterge playlist de la FollowedPlaylists
        Playlist playlist;
        for (User user2 : Admin.getUserList()) {
            for (int i = 0; i < user2.getFollowedPlaylists().size(); i++) {
                playlist = user2.getFollowedPlaylists().get(i);
                if (playlist.getOwner().equals(commandInput.getUsername())) {
                    user2.getFollowedPlaylists().remove(i);
                    i--;
                }
            }

        }

        User.decrease(user);

        message = commandInput.getUsername() + " was successfully deleted.";
        Admin.getUserList().remove(user);
        return message;
    }

    /**
     * adauga un podcast
     *
     * @param commandInput
     */
    public static String addPodcast(final CommandInput commandInput, final User user) {
        String message;
        for (Podcast podcast : Admin.getPodcastList()) {
            if (podcast.getName().equals(commandInput.getName())) {
                message = commandInput.getUsername() + " has another podcast with the same name.";
                return message;
            }
        }

        // verific daca are un episod de 2 ori
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            int sum = 0;
            for (EpisodeInput episodeInput2 : commandInput.getEpisodes()) {
                if (episodeInput.getName().equals(episodeInput2.getName())) {
                    sum++;
                }
            }
            if (sum > 1) {
                message = commandInput.getUsername() + " has the same episode in this podcast.";
                return message;
            }
        }

        // adaug episoadele
        List<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            Episode episode = new Episode(episodeInput.getName(), episodeInput.getDuration(),
                    episodeInput.getDescription());
            episodes.add(episode);
        }

        Podcast podcast = new Podcast(commandInput.getName(), commandInput.getUsername(), episodes);
        Admin.getPodcastList().add(podcast);

        message = commandInput.getUsername() + " has added new podcast successfully.";

        return message;
    }

    /**
     * adauga un anunt
     *
     * @param commandInput
     * @return
     */
    public static String addAnnouncement(final CommandInput commandInput) {
        String message;
        for (Announcement announcement : Admin.getAnnouncementList()) {
            if (announcement.getName().equals(commandInput.getName())) {
                message = commandInput.getUsername();
                message = message + " has already added an announcement with this name.";
                return message;
            }
        }

        // creez anuntul
        Announcement announcement = new Announcement();
        announcement.setName(commandInput.getName());
        announcement.setOwner(commandInput.getUsername());
        announcement.setDescription(commandInput.getDescription());

        Admin.getAnnouncementList().add(announcement);
        message = commandInput.getUsername() + " has successfully added new announcement.";

        // etapa3
        Notification notification = new Notification();
        notification.setName("New Announcement");
        notification.setDescription("New Announcement from " + commandInput.getUsername() + ".");
        User user = Admin.getUser(commandInput.getUsername());
        for (User user2 : user.getSubscribers()) {
            user2.getNotifications().add(notification);
        }
        return message;
    }

    /**
     * sterge un anunt
     *
     * @param commandInput
     * @return
     */
    public static String removeAnnouncement(final CommandInput commandInput) {
        String message;
        for (Announcement announcement : Admin.getAnnouncementList()) {
            if (announcement.getName().equals(commandInput.getName())) {
                Admin.getAnnouncementList().remove(announcement);
                message = commandInput.getUsername();
                message = message + " has successfully deleted the announcement.";
                return message;
            }
        }

        message = commandInput.getUsername() + " has no announcement with the given name.";
        return message;
    }

    /**
     * afiseaza podcasturile unui host
     *
     * @param commandInput
     * @return
     */
    public static ArrayList<ShowPodcast> showPodcasts(final CommandInput commandInput) {
        ArrayList<ShowPodcast> showPodcasts = new ArrayList<>();
        for (Podcast podcast : Admin.getPodcastList()) {
            if (podcast.getOwner().equals(commandInput.getUsername())) {

                ShowPodcast showPodcast = new ShowPodcast();
                showPodcast.setName(podcast.getName());
                for (Episode episode : podcast.getEpisodes()) {
                    showPodcast.getEpisodes().add(episode.getName());
                }
                showPodcasts.add(showPodcast);
            }
        }
        return showPodcasts;
    }

    /**
     * sterge un album
     *
     * @param commandInput
     * @return
     */
    public static String removeAlbum(final CommandInput commandInput) {
        String username = commandInput.getUsername();
        String message;
        Album albumArtist2 = null;
        for (Album album : Admin.getAlbumList()) {
            if (album.getName().equals(commandInput.getName()) && album.getOwner().equals(commandInput.getUsername())) {
                albumArtist2 = album;
                break;

            }
        }
        final Album albumArtist = albumArtist2;
        if (albumArtist == null) {
            message = commandInput.getUsername() + " doesn't have an album with the given name.";
            return message;
        }

        if (getAudioCollectionsStream().anyMatch(collection -> collection == albumArtist)) {
            return "%s can't delete this album.".formatted(username);
        }

        for (Song song : albumArtist.getSongs()) {
            if (getAudioCollectionsStream().anyMatch(collection -> collection.containsTrack(song))
                    || getAudioFilesStream().anyMatch(audioFile -> audioFile == song)) {
                return "%s can't delete this album.".formatted(username);
            }
        }

        for (Song song : albumArtist.getSongs()) {
            users.forEach(user -> {
                user.getLikedSongs().remove(song);
                user.getPlaylists().forEach(playlist -> playlist.removeSong(song));
            });
            songs.remove(song);
        }

        Admin.getAlbumList().remove(albumArtist);
        message = commandInput.getUsername() + " deleted the album successfully.";

        return message;
    }

    private static Stream<AudioCollection> getAudioCollectionsStream() {
        return users.stream().map(User::getPlayer)
                .map(Player::getCurrentAudioCollection).filter(Objects::nonNull);
    }

    private static Stream<AudioFile> getAudioFilesStream() {
        return users.stream().map(User::getPlayer)
                .map(Player::getCurrentAudioFile).filter(Objects::nonNull);
    }

    /**
     * schimba pagina unui user
     *
     * @param commandInput
     * @return
     */
    public static String changePage(final CommandInput commandInput, final User user) {
        String message;
        if (!commandInput.getNextPage().equals("Home")
                && !commandInput.getNextPage().equals("LikedContent") && !commandInput.getNextPage().equals("Host")
                && !commandInput.getNextPage().equals("Artist")) {
            message = commandInput.getUsername() + " is trying to access a non-existent page.";
            return message;
        }

        if (commandInput.getNextPage().equals("Artist") || commandInput.getNextPage().equals("Host")) {
            String name;
            if (user.getPlayer().getSource() != null) {
                if (user.getPlayer().getSource().getType() == Enums.PlayerSourceType.LIBRARY) {
                    name = ((Song) user.getPlayer().getSource().getAudioFile()).getArtist();
                } else {
                    name = user.getPlayer().getSource().getAudioCollection().getOwner();
                }

                user.getPage().setTypePage(name);

                // adaugare pagina in istoric
                int index = user.getPage().getHistory().size();
                user.getPage().getHistory().add(name);
                user.getPage().setIndexPage(index);
            }

        } else {
            user.getPage().setTypePage(commandInput.getNextPage());

            // adaugare pagina in istoric
            int index = user.getPage().getHistory().size();
            user.getPage().getHistory().add(commandInput.getNextPage());
            user.getPage().setIndexPage(index);
        }

        message = commandInput.getUsername();
        message = message + " accessed " + commandInput.getNextPage() + " successfully.";
        return message;
    }

    /**
     * sterge un eveniment
     *
     * @param commandInput
     * @return
     */
    public static String removeEvent(final CommandInput commandInput) {
        String message;
        Event eventArtist = null;
        for (Event event : Admin.getEventList()) {
            if (event.getName().equals(commandInput.getName())) {
                eventArtist = event;
            }
        }

        if (eventArtist == null) {
            message = commandInput.getUsername() + " doesn't have an event with the given name.";
        } else {
            message = commandInput.getUsername() + " deleted the event successfully.";
            Admin.getEventList().remove(eventArtist);
        }
        return message;
    }

    /**
     * sterge un podcast
     *
     * @param commandInput
     * @return
     */
    public static String removePodcast(final CommandInput commandInput) {
        String message;
        Podcast podcastHPodcast = null;
        for (Podcast podcast : Admin.getPodcastList()) {
            if (podcast.getName().equals(commandInput.getName())) {
                podcastHPodcast = podcast;
            }
        }

        if (podcastHPodcast == null) {
            message = commandInput.getUsername() + " doesn't have a podcast with the given name.";
        } else {
            // verificare daca cineva asculta Podcastul
            for (User user2 : Admin.getUserList()) {
                if (user2.getPlayer() != null && user2.getPlayer().getSource() != null) {
                    PlayerSource playerSource = user2.getPlayer().getSource();
                    if (playerSource.getType() == Enums.PlayerSourceType.PODCAST) {
                        if (((Podcast) playerSource.getAudioCollection()).getOwner()
                                .equals(commandInput.getUsername())) {
                            message = commandInput.getUsername() + " can't delete this podcast.";
                            return message;
                        }

                    }
                }
            }
            message = commandInput.getUsername() + " deleted the podcast successfully.";
            Admin.getPodcastList().remove(podcastHPodcast);
        }
        return message;
    }

    /**
     * afiseaza top 5 albume
     *
     * @param commandInput
     */
    public static ArrayList<String> getTop5Albums(final CommandInput commandInput) {
        // calculez numarul de like-uri pentru un album
        for (Album album : Admin.getAlbumList()) {
            int likes = 0;
            for (Song song : album.getSongs()) {
                likes += song.getLikes();
            }
            album.setLikes(likes);
        }

        List<Album> albums = Admin.getAlbumList();
        // sortez albumele
        Collections.sort(albums, (album1, album2) -> {
            if (album1.getLikes() == album2.getLikes()) {
                return album1.getName().compareTo(album2.getName());
            } else {
                return album2.getLikes() - album1.getLikes();
            }
        });

        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < albums.size() && i < LIMIT; i++) {
            result.add(albums.get(i).getName());
        }

        return result;
    }

    /**
     * afiseaza top 5 artisti
     *
     * @param commandInput
     */
    public static ArrayList<String> getTop5Artists(final CommandInput commandInput) {
        // calculez numarul de like-uri pentru un album
        for (Album album : Admin.getAlbumList()) {
            int likes = 0;
            for (Song song : album.getSongs()) {
                likes += song.getLikes();
            }
            album.setLikes(likes);
        }

        List<Artist> artists = new ArrayList<>();
        // calculez numarul de like-uri pentru un artist
        for (User user : Admin.getUserList()) {
            if (user.getType().equals("artist")) {
                int likes = 0;
                for (Album album : Admin.getAlbumList()) {
                    if (album.getOwner().equals(user.getUsername())) {
                        likes += album.getLikes();
                    }
                }
                ((Artist) user).setNrLikes(likes);
                artists.add((Artist) user);
            }
        }

        // sortez artistii
        Collections.sort(artists, (artist1, artist2) -> {
            if (artist1.getNrLikes() == artist2.getNrLikes()) {
                return artist1.getUsername().compareTo(artist2.getUsername());
            } else {
                return artist2.getNrLikes() - artist1.getNrLikes();
            }
        });

        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < artists.size() && i < LIMIT; i++) {
            result.add(artists.get(i).getUsername());
        }
        return result;
    }

    // etapa3
    /**
     * wrapper pentru user
     * @param user2
     * @return ShowWrappedUser
     */
    public static ShowWrappedUser wrappedUser(final User user2) {
        // adaug melodiile in lista de melodii ascultate
        User.addSongs(user2);

        // sterg AdBreak-urile din istoricul de ascultari
        for (int i = 0; i < user2.getPlayer().getSongsListened().size(); i++) {
            Song song = user2.getPlayer().getSongsListened().get(i);
            if (song.getName().equals("Ad Break")) {
                user2.getPlayer().getSongsListened().remove(i);
                i--;
            }
        }

        // adaug episoadele in lista de episoade ascultate
        User.addEpisodes(user2);

        ShowWrappedUser show = new ShowWrappedUser();

        // calculez numarul de listenings pentru fiecare melodie
        List<SongWrapped> songs = User.calculateListeningsSongs(user2);

        int nr = 0;
        for (SongWrapped song : songs) {
            if (nr < LIMIT) {
                show.getTopSongs().put(song.getName(), song.getListenings());
            }
            nr++;

        }


        // lista cu albumele cu numarul de listenings
        List<AlbumWrapped> albumWrappeds = User.calculateListeningsAlbum(user2);

        nr = 0;
        for (AlbumWrapped album : albumWrappeds) {
            if (nr < LIMIT) {
                show.getTopAlbums().put(album.getName(), album.getListenings());
            }
            nr++;

        }

        // lista cu episoadele cu numarul de listenings
        List<EpisodeWrapped> episodes = User.calculateTopEpisodes(user2);


        nr = 0;
        for (EpisodeWrapped episode : episodes) {
            if (nr < LIMIT) {
                show.getTopEpisodes().put(episode.getName(), episode.getListenings());
            }
            nr++;

        }


        // pentru top artisti
        List<Artist> artists = new ArrayList<>();
        for (User user : Admin.users) {
            if (user.getType().equals("artist")) {
                int listenings = 0;
                for (Song song : user2.getPlayer().getSongsListened()) {
                    if (user.getUsername().equals(song.getArtist())) {
                        listenings += 1;
                    }
                }
                ((Artist) user).setListenings(listenings);
                if (listenings > 0) {
                    artists.add((Artist) user);
                }
            }
        }

        // sortez artistii in functie de listenings
        Collections.sort(artists, (artist1, artist2) -> {
            if (artist1.getListenings() == artist2.getListenings()) {
                return artist1.getUsername().compareTo(artist2.getUsername());
            } else {
                return artist2.getListenings() - artist1.getListenings();
            }
        });

        nr = 0;
        for (Artist artist : artists) {
            if (nr < LIMIT) {
                show.getTopArtists().put(artist.getUsername(), artist.getListenings());
            }
            nr++;
        }

        // lista cu genuri cu numarul de listenings
        List<GenreStatistic> genreStatistics = User.topGenres(user2);

        nr = 0;
        for (GenreStatistic genreStatistic : genreStatistics) {
            if (nr < LIMIT && genreStatistic.getListenings() > 0) {
                show.getTopGenres().put(genreStatistic.getGenre(), genreStatistic.getListenings());
            }
            nr++;
        }

        return show;
    }

    /**
     * wrapped pentru artist
     * @param artist
     * @return ShowWrappedArtist
     */
    public static ShowWrappedArtist wrappedArtist(final Artist artist) {
        ShowWrappedArtist show = new ShowWrappedArtist();

        // lista cu melodiile cu numarul de listenings
        List<SongWrapped> songs = Artist.calculateListeningsSongs(artist);

        int nr = 0;
        for (SongWrapped song : songs) {
            if (nr < LIMIT) {
                show.getTopSongs().put(song.getName(), song.getListenings());
            }
            nr++;

        }

        // lista cu albumele cu numarul de listenings
        List<Album> albums = Artist.calculateTopAlbums(artist);

        nr = 0;
        for (Album album : albums) {
            if (nr < LIMIT) {
                show.getTopAlbums().put(album.getName(), album.getListenings());
            }
            nr++;

        }

        // lista cu fanii cu numarul de listenings
        List<Fan> fans = Artist.calculateFans(artist);

        nr = 0;
        for (Fan fan : fans) {
            if (nr < LIMIT && fan.getListenings() > 0) {
                show.getTopFans().add(fan.getName());
            }
            nr++;
        }

        // pentru listeners
        show.setListeners(fans.size());

        return show;
    }

    /**
     * wrapped pentru host
     * @param host
     * @return ShowWrappedHost
     */
    public static ShowWrappedHost wrappedHost(final Host host) {
        ShowWrappedHost show = new ShowWrappedHost();
        List<EpisodeWrapped> episodes = new ArrayList<>();
        int listeners = 0;
        for (User user : Admin.users) {
            User.addEpisodes(user);

            // cresc numarul de listeners pentru host
            for (String name : user.getPlayer().getHostListened()) {
                if (name.equals(host.getUsername())) {
                    listeners++;
                    break;
                }
            }

            // calculez numarul de listenings pentru fiecare episod
            for (int i = 0; i < user.getPlayer().getHostListened().size(); i++) {
                String name = user.getPlayer().getHostListened().get(i);
                if (name.equals(host.getUsername())) {
                    int ok = 0;
                    for (EpisodeWrapped episodeWrapped : episodes) {
                        if (episodeWrapped.getName().equals(user.getPlayer().getEpisodesListened().get(i).getName())) {
                            episodeWrapped.setListenings(episodeWrapped.getListenings() + 1);
                            ok = 1;
                            break;
                        }
                    }

                    if (ok == 0) {
                        EpisodeWrapped episodeWrapped = new EpisodeWrapped();
                        episodeWrapped.setName(user.getPlayer().getEpisodesListened().get(i).getName());
                        episodeWrapped.setListenings(1);
                        episodes.add(episodeWrapped);
                    }
                }
            }
        }

        // sortez episoadele
        Collections.sort(episodes, (episode1, episode2) -> {
            if (episode1.getListenings() == episode2.getListenings()) {
                return episode1.getName().compareTo(episode2.getName());
            } else {
                return episode2.getListenings() - episode1.getListenings();
            }
        });

        int nr = 0;
        for (EpisodeWrapped episode : episodes) {
            if (nr < LIMIT) {
                show.getTopEpisodes().put(episode.getName(), episode.getListenings());
            }
            nr++;

        }

        show.setListeners(listeners);

        return show;
    }

    /**
     * face update la playlist
     * @param artist
     * @return 1 daca s-a putut face update, 0 altfel
     */
    public static int updateFanPlaylist(final Artist artist) {
        // top fans
        List<Fan> fans = new ArrayList<>();
        for (User user : Admin.users) {
            User.addSongs(user);
            // sterg AdBreak-urile din istoricul de ascultari
            for (int i = 0; i < user.getPlayer().getSongsListened().size(); i++) {
                Song song = user.getPlayer().getSongsListened().get(i);
                if (song.getName().equals("Ad Break")) {
                    user.getPlayer().getSongsListened().remove(i);
                    i--;
                }
            }

            List<Song> songListened = user.getPlayer().getSongsListened();
            int nr2 = 0;
            for (Song song : songListened) {
                if (song.getArtist().equals(artist.getUsername())) {
                    nr2++;
                }
            }
            if (nr2 > 0) {
                Fan fan = new Fan();
                fan.setName(user.getUsername());
                fan.setListenings(nr2);
                fans.add(fan);
            }

        }

        // sortez fanii
        Collections.sort(fans, (fan1, fan2) -> {
            if (fan1.getListenings() == fan2.getListenings()) {
                return fan1.getName().compareTo(fan2.getName());
            } else {
                return Integer.compare(fan2.getListenings(), fan1.getListenings());
            }
        });

        // daca nu exista fani nu se poate creea fanPlaylist
        if (fans.size() == 0) {
            return 0;
        }

        int nr = 0;
        for (Fan fan : fans) {
            User user = Admin.getUser(fan.getName());
            if (user.getLikedSongs().size() == 0) {
                nr++;
            }
        }

        // daca fanii nu au dat like la nicio melodie nu se poate creea fanPlaylist-ul
        if (nr == fans.size()) {
            return 0;
        }

        return 1;

    }

    /**
     * face update la recomandari
     * @param user
     * @param commandInput
     * @return message
     */
    public static String updateRecommendations(final User user, final CommandInput commandInput) {
        String message;
        if (commandInput.getRecommendationType().equals("random_song")) {
            PlayerStats stats = user.getPlayerStats();
            Song songListened = (Song) user.getPlayer().getSource().getAudioFile();
            int timePassed = songListened.getDuration() - stats.getRemainedTime();

            final Random rnd = new Random(timePassed);
            ArrayList<Song> songs = new ArrayList<>();
            for (Song song : Admin.songsHistory) {
                if (song.getGenre().equals(songListened.getGenre())) {
                    songs.add(song);
                }
            }

            // indexul melodii recomandate
            int index = rnd.nextInt(songs.size());

            user.getSongRecommendation().add(songs.get(index));

        } else if (commandInput.getRecommendationType().equals("random_playlist")) {
            if (user.getLikedSongs().size() == 0 && user.getPlaylists().size() == 0
                    && user.getFollowedPlaylists().size() == 0) {
                message = "No new recommendations were found";
                return message;
            }
            String namePlaylist = user.getUsername() + "'s recommendations";
            Playlist playlist;
            playlist = new Playlist(namePlaylist, user.getUsername(), commandInput.getTimestamp());

            user.getPlaylistRecommendation().add(playlist);

        } else {

            Song song = (Song) user.getPlayer().getSource().getAudioFile();
            String name = song.getArtist();
            User artist = Admin.getUser(name);
            int ok = Admin.updateFanPlaylist((Artist) artist);
            if (ok == 0) {
                message = "No new recommendations were found";
                return message;
            }
            name = ((Song) user.getPlayer().getSource().getAudioFile()).getArtist();
            name = name + " Fan Club recommendations";
            Playlist playlist;
            playlist = new Playlist(name, user.getUsername(), commandInput.getTimestamp());

            user.getPlaylistRecommendation().add(playlist);
        }

        message = "The recommendations for user "
                + commandInput.getUsername() + " have been updated successfully.";
        return message;

    }

    /**
     * incarca recomandarile
     * @param user
     * @param commandInput
     */
    public static void loadRecommendations(final User user, final CommandInput commandInput) {
        int index = user.getSongRecommendation().size() - 1;
        Song song2 = user.getSongRecommendation().get(index);

        // adaug melodiile in lista de melodii ascultate
        User.addSongs(user);
        // adaug episoadele in lista de episoade ascultate
        User.addEpisodes(user);

        user.getPlayer().setSource((LibraryEntry) song2, "song");
        user.getPlayer().pause();
    }

    /**
     * cumpara merch
     * @param user
     * @param commandInput
     * @return message
     */
    public static String buyMerch(final User user, final CommandInput commandInput) {
        String message = null;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        if (user.getPage().getTypePage().equals("Home")
            || user.getPage().getTypePage().equals("LikedContent")) {
            message = "Cannot buy merch from this page.";
            return message;
        }

        for (User user2 : Admin.users) {
            if (user2.getUsername().equals(user.getPage().getTypePage())) {
                if (!user2.getType().equals("artist")) {
                    message = "Cannot buy merch from this page.";
                    return message;
                }
            }
        }

        for (Merch merch : Admin.merchs) {
            if (merch.getName().equals(commandInput.getName())) {
                if (merch.getOwner().equals(user.getPage().getTypePage())) {
                    user.getMerchbought().add(merch);
                    message = commandInput.getUsername() + " has added new merch successfully.";
                    return message;
                }
            }
        }

        message = "The merch " + commandInput.getName() + " doesn't exist.";
        return message;
    }

    /**
     * creeaza o lista cu merch-urile
     * @param user
     * @return lista cu merch-uri
     */
    public static ArrayList<String> seeMerch(final User user) {
        ArrayList<String> result = new ArrayList<>();
        for (Merch merch : user.getMerchbought()) {
            result.add(merch.getName());
        }
        return result;
    }

    /**
     * se aboneaza
     * @param user
     * @param commandInput
     * @return message
     */
    public static String subscribe(final User user, final CommandInput commandInput) {
        String message = null;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        if (user.getPage().getTypePage().equals("Home")
            || user.getPage().getTypePage().equals("LikedContent")) {
            message = "To subscribe you need to be on the page of an artist or host.";
            return message;
        }

        String name;
        name = user.getPage().getTypePage();

        User user3 = null;
        for (User user2 : Admin.users) {
            if (user2.getUsername().equals(name)) {
                user3 = user2;
                break;
            }
        }
        int ok = 0;
        for (User user2 : user3.getSubscribers()) {
            if (user2.getUsername().equals(user.getUsername())) {
                ok = 1;
            }
        }

        if (ok == 0) {
            user3.getSubscribers().add(user);
            message = user.getUsername() + " subscribed to " + name + " successfully.";
            return message;
        } else {
            user3.getSubscribers().remove(user);
            message = user.getUsername() + " unsubscribed from " + name + " successfully.";
            return message;
        }
    }

    /**
     * obtine notificarile
     * @param user
     * @return ArrayList<Notification> notifications
     */
    public static ArrayList<Notification> getNotifications(final User user) {
        ArrayList<Notification> notifications = new ArrayList<>();
        for (Notification notification : user.getNotifications()) {
            notifications.add(notification);
        }
        user.setNotifications(new ArrayList<Notification>());
        return notifications;
    }

    /**
     * cumpara premium
     * @param user
     * @param commandInput
     * @return message
     */
    public static String buyPremium(final User user, final CommandInput commandInput) {
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        if (user.getPremium()) {
            message = commandInput.getUsername() + " is already a premium user.";
        } else {
            message = commandInput.getUsername() + " bought the subscription successfully.";

            // actualizare lista pana la momentul in care devine premium
            User.addSongs(user);
            user.setPremium(true);
            user.getPlayer().setPremium(true);
        }
        return message;
    }

    /**
     * calculeaza monetizarea pentru melodiile de la premium
     * @param user
     */
    public static void songMoney(final User user) {
        int nr = 0;
        nr = user.getPlayer().getSongsPremium().size();
        for (Song song : user.getPlayer().getSongsPremium()) {
            double revenue = (double) MONEY / nr;
            song.setRevenue(song.getRevenue() + revenue);
        }
        user.getPlayer().setSongsPremium(new ArrayList<Song>());

    }

    /**
     * anuleaza abonamentul premium
     * @param user
     * @param commandInput
     * @return message
     */
    public static String cancelPremium(final User user, final CommandInput commandInput) {
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        if (user.getPremium()) {
            message = commandInput.getUsername() + " cancelled the subscription successfully.";
            // actualizare lista pana la momentul in care anuleaza premium
            User.addSongs(user);
            user.setPremium(false);
            user.getPlayer().setPremium(false);
            Admin.songMoney(user);
        } else {
            message = commandInput.getUsername() + " is not a premium user.";
        }

        return message;
    }

    /**
     * operatia adBreak
     * @param user
     * @param commandInput
     * @return message
     */
    public static String adBreak(final User user, final CommandInput commandInput) {
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            return message;
        }

        if (user.getPlayer().getSource() == null) {
            message = user.getUsername() + " is not playing any music.";
            return message;
        }

        message = "Ad inserted successfully.";

        User.addSongs(user);

        // cauta Ad Break-ul
        Song song2 = null;
        for (Song song : Admin.songs) {
            if (song.getName().equals("Ad Break")) {
                song2 = song;
                break;
            }
        }
        // copie a Ad Break-ului
        Song song3 = new Song(song2.getName(), song2.getDuration(),
                song2.getAlbum(), song2.getTags(),
                song2.getLyrics(), song2.getGenre(), song2.getReleaseYear(), song2.getArtist());
        song3.setRevenue((double) commandInput.getPrice());
        user.getPlayer().getSource().setAdBreak(song3);

        // creez un album copie fara al retine
        if (user.getPlayer().getType().equals("album") && user.getPlayer().getSource() != null) {
            Player player = user.getPlayer();
            int nr = 0;
            nr = player.getSource().getIndex();
            nr++;
            Album album = new Album(((Album) player.getSource().getAudioCollection()).getName());
            album.setOwner(((Album) player.getSource().getAudioCollection()).getOwner());
            album.setSongs(((Album) player.getSource().getAudioCollection()).getSongs());
            album.getSongs().add(nr, song3);
            player.getSource().setAudioCollection(album);
        }

        return message;
    }

    /**
    * calculeaza monetizarea pentru melodiile de la free
    *
    * @param user the user
    */
    public static void calculateSongRevenue(final User user) {
        ArrayList<Song> songs = new ArrayList<>();

        // parcurge lista cu cantece
        for (Song song : user.getPlayer().getSongsFree()) {
            // cand intalneste un Ad Break calculeaza revenue-ul
            if (song.getName().equals("Ad Break")) {
                int nr = songs.size();
                for (Song song2 : songs) {
                    double revenue = (double) song.getRevenue() / nr;
                    song2.setRevenue(song2.getRevenue() + revenue);
                }
                songs = new ArrayList<>();
            } else {
                songs.add(song);
            }
        }
    }

    /**
     * endProgram
     * @return lista cu artistii
     */
    public static List<Artist> endProgram() {
        for (User user : Admin.getUserList()) {
            User.addSongs(user);
            Admin.songMoney(user);
        }

        for (User user : Admin.getUserList()) {
            Admin.calculateSongRevenue(user);
        }

        // se seteaza artistii cu care s-a interactionat
        for (int i = 0; i < Admin.getUserList().size(); i++) {
            User user = Admin.getUserList().get(i);
            for (Song song : user.getPlayer().getSongsListened()) {
                String nameArtist = song.getArtist();
                int ok = 0;
                for (User user2 : Admin.getUserList()) {
                    if (user2.getUsername().equals(nameArtist)) {
                        user2.setLoaded(true);
                        ok = 1;
                        break;
                    }
                }
                if (ok == 0) {
                    Artist artist = new Artist(nameArtist, 0, null);
                    artist.setLoaded(true);
                    Admin.getUserList().add(artist);
                }
            }
        }

        // se face o lista cu artistii cu care s-a interactionat
        List<Artist> artists = new ArrayList<>();
        for (User user : Admin.getUserList()) {
            if (user.isLoaded() && !user.getUsername().equals("GlobalWaves")) {
                artists.add((Artist) user);
            }
        }

        // se calculeaza revenue-ul pentru artisti
        for (User user : Admin.getUserList()) {
            if (user.getType().equals("artist")) {
                double revenue = 0;
                for (User user2 : Admin.getUserList()) {
                    for (Merch merch : user2.getMerchbought()) {
                        if (merch.getOwner().equals(user.getUsername())) {
                            revenue += merch.getPrice();
                        }
                    }
                }
                if (revenue > 0) {
                    user.setRevenue(revenue);
                    artists.add((Artist) user);
                }
            }
        }

        // se combina artistii daca apar de mai multe ori in lista
        for (int i = 0; i < artists.size() - 1; i++) {
            for (int j = i + 1; j < artists.size(); j++) {
                if (artists.get(i).getUsername().equals(artists.get(j).getUsername())) {
                    artists.get(i).setRevenue(artists.get(i).getRevenue()
                                              + artists.get(j).getRevenue());
                    artists.remove(j);
                    i--;
                    break;
                }
            }
        }

        for (Artist artist : artists) {
            double revenue = 0;
            for (Song song : Admin.getSongsHistory()) {
                if (song.getArtist().equals(artist.getUsername())) {
                    revenue += song.getRevenue();
                }
            }
            artist.setRevenue(artist.getRevenue() + revenue);
        }

        // sortez artistii
        Collections.sort(artists, (artist1, artist2) -> {
            if (artist1.getRevenue() == artist2.getRevenue()) {
                return artist1.getUsername().compareTo(artist2.getUsername());
            } else if (artist1.getRevenue() > artist2.getRevenue()) {
                return -1;
            } else {
                return 1;
                }
        });

        return artists;
    }

    /**
     * calculeaza cea mai buna melodie
     * @param songArtist
     * @return bestSong
     */
    public static Song calculateSongBest(final ArrayList<Song> songArtist) {
        Song songBest = null;
        double songBestRevenue = 0;
        if (songArtist.size() > 0) {
            songBest = songArtist.get(songArtist.size() - 1);
            songBestRevenue = songBest.getRevenue();
            for (int i = 0; i < songArtist.size() - 1; i++) {
                Song song1 = songArtist.get(i);
                double revenue1 = song1.getRevenue();
                for (int j = i + 1; j < songArtist.size(); j++) {
                    Song song2 = songArtist.get(j);
                    if (song1.getName().equals(song2.getName())) {
                        revenue1 += song2.getRevenue();
                    }
                }
                if (revenue1 > songBestRevenue) {
                    songBest = song1;
                    songBestRevenue = revenue1;
                } else if (revenue1 == songBestRevenue
                           && songBest.getName().compareTo(song1.getName()) > 0) {
                    songBest = song1;
                    songBestRevenue = revenue1;
                }

            }
        }
        if (songBest != null && songBestRevenue == 0) {
            songBest = null;
        }

        return songBest;
    }

}
