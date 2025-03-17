package app;

import app.audio.Collections.PlaylistOutput;
import app.audio.Files.Album;
import app.audio.Files.Merch;
import app.audio.Files.Song;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.User;
import display.ShowAlbum;
import display.ShowPodcast;
import display.ShowWrappedUser;
import display.ShowWrappedArtist;
import display.ShowWrappedHost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    private static final double HUNDRED = 100.0;

    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.getOnline()) {
            Filters filters = new Filters(commandInput.getFilters());
            String type = commandInput.getType();

            ArrayList<String> results = user.search(filters, type);
            String message = "Search returned " + results.size() + " results";

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);
            objectNode.put("results", objectMapper.valueToTree(results));
            return objectNode;
        } else {
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            String message = commandInput.getUsername() + " is offline.";
            objectNode.put("message", message);
            ArrayList<String> results = new ArrayList<>();
            objectNode.put("results", objectMapper.valueToTree(results));
            return objectNode;
        }

    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());

        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user.getOnline()) {
            message = user.like();
        } else {
            message = commandInput.getUsername() + " is offline.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.createPlaylist(commandInput.getPlaylistName(),
                commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * schimba statusul unui user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (user.getType().equals("normal")) {
            user.setOnline(!user.getOnline());
            message = commandInput.getUsername() + " has changed status successfully.";
        } else {
            message = commandInput.getUsername() + " is not a normal user.";
        }
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza utilizatorii online
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        ArrayList<String> result = Admin.usersOnline();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(result));

        return objectNode;
    }

    /**
     * adauga un user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "bogdan";
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = Admin.addUser(commandInput);
        } else {
            message = "The username " + commandInput.getUsername() + " is already taken.";
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * adauga un artist
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = Admin.addAlbum(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza albumele unui artist
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<ShowAlbum> showAlbums = new ArrayList<>();
        for (Album album : Admin.getAlbumList()) {
            if (album.getOwner().equals(commandInput.getUsername())) {
                ShowAlbum showAlbum = new ShowAlbum();
                showAlbum.setName(album.getName());
                for (Song song : album.getSongs()) {
                    showAlbum.getSongs().add(song.getName());
                }
                showAlbums.add(showAlbum);
            }
        }
        objectNode.put("result", objectMapper.valueToTree(showAlbums));

        return objectNode;
    }

    /**
     * afiseaza pagina unui user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (!user.getOnline()) {
            message = commandInput.getUsername() + " is offline.";
            objectNode.put("message", message);
            return objectNode;
        }

        message = Admin.printCurrentPage(commandInput, user);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * adauga un eveniment
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = null;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = Admin.addEvent(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * adauga un merch
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = Admin.addMerch(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza toti userii
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<String> result = new ArrayList<>();
        result = Admin.getAllUsers(commandInput);
        objectNode.put("result", objectMapper.valueToTree(result));

        return objectNode;
    }

    /**
     * sterge un user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        message = Admin.deleteUser(commandInput, user);
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * adauga un podcast
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = Admin.addPodcast(commandInput, user);

        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * adauga un anunt
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = Admin.addAnnouncement(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * sterge un anunt
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = Admin.removeAnnouncement(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza podcasturile unui host
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<ShowPodcast> showPodcasts = new ArrayList<>();
        showPodcasts = Admin.showPodcasts(commandInput);
        objectNode.put("result", objectMapper.valueToTree(showPodcasts));

        return objectNode;
    }

    /**
     * sterge un album
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = Admin.removeAlbum(commandInput);
        }
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * schimba pagina unui user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        message = Admin.changePage(commandInput, user);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * sterge un eveniment
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = Admin.removeEvent(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * sterge un podcast
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message;
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = Admin.removePodcast(commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza top 5 albume
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<String> result = new ArrayList<>();
        result = Admin.getTop5Albums(commandInput);

        objectNode.put("result", objectMapper.valueToTree(result));
        return objectNode;
    }

    /**
     * afiseaza top 5 artisti
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());

        ArrayList<String> result = new ArrayList<>();
        result = Admin.getTop5Artists(commandInput);

        objectNode.put("result", objectMapper.valueToTree(result));
        return objectNode;
    }

    // etapa3
    /**
     * wrapped
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode wrapped(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());
        if (user == null) {
            objectNode.put("message", "No data to show for user " + commandInput.getUsername() + ".");
            return objectNode;
        }

        if (user.getType().equals("normal")) {
            ShowWrappedUser show = Admin.wrappedUser(user);
            if (show.getTopSongs().isEmpty() && show.getTopEpisodes().isEmpty()) {
                objectNode.put("message", "No data to show for user " + commandInput.getUsername() + ".");
                return objectNode;
            }
            objectNode.put("result", objectMapper.valueToTree(show));

        } else if (user.getType().equals("artist")) {
            ShowWrappedArtist show = Admin.wrappedArtist((Artist) user);
            if (show.getListeners() == 0) {
                objectNode.put("message", "No data to show for artist " + commandInput.getUsername() + ".");
                return objectNode;
            }
            if (show.getTopSongs().isEmpty()) {
                objectNode.put("message", "No data to show for artist " + commandInput.getUsername() + ".");
                return objectNode;
            }
            objectNode.put("result", objectMapper.valueToTree(show));

        } else {
            ShowWrappedHost show = Admin.wrappedHost((Host) user);
            objectNode.put("result", objectMapper.valueToTree(show));
        }

        return objectNode;
    }

    /**
     * afiseaza toti artistii
     *
     * @return the object node
     */
    public static ObjectNode endProgram() {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", "endProgram");

        // lista cu artistii cu care s-a intereactionat
        List<Artist> artists = Admin.endProgram();

        Map<String, EndProgram> map = new LinkedHashMap<>();
        int nr = 1;
        for (Artist artist : artists) {
            EndProgram endP = new EndProgram();
            double revenue = 0;
            ArrayList<Song> songArtist = new ArrayList<>();

            // se calculeaza revenue-ul pentru fiecare melodiei a artistului
            for (Song song : Admin.getSongsHistory()) {
                if (song.getArtist().equals(artist.getUsername())) {
                    revenue += song.getRevenue();
                    songArtist.add(song);
                }

            }

            // se calculeaza cea mai profitabila melodie
            Song songBest = Admin.calculateSongBest(songArtist);

            double songRevenue = Math.round(revenue * HUNDRED) / HUNDRED;
            endP.setSongRevenue(songRevenue);

            // se calculeaza revenue-ul pentru merch
            double revenueMerch = 0;
            for (User user2 : Admin.getUserList()) {
                for (Merch merch : user2.getMerchbought()) {
                    if (merch.getOwner().equals(artist.getUsername())) {
                        revenueMerch += merch.getPrice();
                    }
                }
            }

            endP.setMerchRevenue(revenueMerch);

            if (songBest == null) {
                endP.setMostProfitableSong("N/A");
            } else {
                endP.setMostProfitableSong(songBest.getName());
            }
            endP.setRanking(nr++);

            map.put(artist.getUsername(), endP);

        }

        objectNode.put("result", objectMapper.valueToTree(map));
        return objectNode;
    }

    /**
     * updateRecommendations
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode updateRecommendations(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            objectNode.put("message", message);
            return objectNode;

        } else if (!user.getType().equals("normal")) {
            message = commandInput.getUsername() + " is not a normal user";
            objectNode.put("message", message);
            return objectNode;
        }

        message = Admin.updateRecommendations(user, commandInput);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * loadRecommendations
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode loadRecommendations(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());
        String message;
        if (!user.getOnline()) {
            message = commandInput.getUsername() + " is offline.";

        } else if (user.getSongRecommendation().size() == 0) {
            message = "No recommendations available.";

        } else {
            message = "Playback loaded successfully.";
            Admin.loadRecommendations(user, commandInput);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * previousPage
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode previousPage(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        Page page = user.getPage();

        // se verifica daca mai exista pagini unde poate ajunge
        if (page.getIndexPage() == 0) {
            message = "There are no pages left to go back.";

        } else {
            message = "The user " + user.getUsername() + " has navigated successfully to the previous page.";
            page.decreaseIndexPage();
            // se actualizeaza tipul paginii
            String type = page.getHistory().get(page.getIndexPage());
            user.getPage().setTypePage(type);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * nextPage
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode nextPage(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        Page page = user.getPage();

        // se verifica daca mai exista pagini unde poate ajunge
        if (page.getIndexPage() >= page.getHistory().size() - 1) {
            message = "There are no pages left to go forward.";

        } else {
            message = "The user " + user.getUsername() + " has navigated successfully to the next page.";
            page.increaseIndexPage();
            // se actualizeaza tipul paginii
            String type = page.getHistory().get(page.getIndexPage());
            user.getPage().setTypePage(type);
        }

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * cumparul un merch
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode buyMerch(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        message = Admin.buyMerch(user, commandInput);

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * afiseaza merchurile cumparate
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode seeMerch(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
            objectNode.put("message", message);
            return objectNode;
        }

        ArrayList<String> result;
        result = Admin.seeMerch(user);
        objectNode.put("result", objectMapper.valueToTree(result));

        return objectNode;
    }

    /**
     * da subscribe
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode subscribe(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        message = Admin.subscribe(user, commandInput);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * afiseaza notificarile unui user
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode getNotifications(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        ArrayList<Notification> notifications = new ArrayList<>();
        notifications = Admin.getNotifications(user);
        objectNode.put("notifications", objectMapper.valueToTree(notifications));

        return objectNode;
    }

    /**
     * cumpara premium
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode buyPremium(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        message = Admin.buyPremium(user, commandInput);

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * anuleaza premium
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode cancelPremium(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        message = Admin.cancelPremium(user, commandInput);

        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * adauga un adBreak
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode adBreak(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());

        User user = Admin.getUser(commandInput.getUsername());

        String message;
        message = Admin.adBreak(user, commandInput);

        objectNode.put("message", message);

        return objectNode;
    }

}
