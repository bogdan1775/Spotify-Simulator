package app;

import java.util.ArrayList;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Album;
import app.audio.Files.Announcement;
import app.audio.Files.Episode;
import app.audio.Files.Event;
import app.audio.Files.Merch;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.User;
import app.user.Host;

import java.util.Collections;

import visit.Visitor;

public final class Page implements Visitor {
    private String typePage;
    private static final int LIMIT = 5;
    private ArrayList<String> history = new ArrayList<>();
    private int indexPage;

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(final ArrayList<String> history) {
        this.history = history;
    }

    public int getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(final int indexPage) {
        this.indexPage = indexPage;
    }

    /**
     * creste indicele paginii din istoric
     *
     */
    public void increaseIndexPage() {
        indexPage++;
    }

    /**
     * scade indicele paginii din istoric
     *
     * @param page
     */
    public void decreaseIndexPage() {
        indexPage--;
    }

    public Page(final String typePage) {
        this.typePage = typePage;
        indexPage = 0;
        history.add(typePage);
    }

    public Page() {
        indexPage = 0;
    }

    public String getTypePage() {
        return typePage;
    }

    public void setTypePage(final String typePage) {
        this.typePage = typePage;
    }

    /**
     * afiseaza pagina unui artist
     *
     * @param artist
     * @return
     */
    public String visit(final Artist artist) {
        String message;

        // retine albumle
        ArrayList<String> albums = new ArrayList<>();
        for (Album album : Admin.getAlbumList()) {
            if (album.getOwner().equals(artist.getUsername())) {
                albums.add(album.getName());
            }
        }

        message = "Albums:\n\t" + albums + "\n\nMerch:\n\t[";
        ArrayList<Merch> merchs = new ArrayList<>();

        // retie merchurile
        for (Merch merch : Admin.getMerchList()) {
            if (merch.getOwner().equals(artist.getUsername())) {
                merchs.add(merch);
            }
        }
        int nr = merchs.size();
        int nr2 = 1;
        for (Merch merch : merchs) {
            message = message + merch.getName() + " - "
                    + merch.getPrice() + ":\n\t" + merch.getDescription();

            if (nr2 < nr) {
                message = message + ", ";
            }
            nr2++;
        }
        message = message + "]\n\nEvents:\n\t[";
        ArrayList<Event> events2 = new ArrayList<>();
        // retine evenimentele
        for (Event event : Admin.getEventList()) {
            if (event.getOwner().equals(artist.getUsername())) {
                events2.add(event);
            }
        }

        nr = events2.size();
        nr2 = 1;
        for (Event event : events2) {
            message = message + event.getName() + " - " + event.getDate()
                    + ":\n\t" + event.getDescription();
            if (nr2 < nr) {
                message = message + ", ";
            }
            nr2++;
        }
        message = message + ']';

        return message;
    }

    /**
     * afiseaza pagina unui host
     *
     * @param host
     * @return
     */
    public String visit(final Host host) {
        String message;
        message = "Podcasts:\n\t[";

        // retine podcasturile
        ArrayList<Podcast> podcasts = new ArrayList<>();
        for (Podcast podcast : Admin.getPodcastList()) {
            if (podcast.getOwner().equals(host.getUsername())) {
                podcasts.add(podcast);
            }
        }

        int nr = podcasts.size();
        int nr2 = 1;
        for (Podcast podcast : podcasts) {
            message = message + podcast.getName() + ":\n\t[";
            ArrayList<Episode> episodes = new ArrayList<>();
            for (Episode episode : podcast.getEpisodes()) {
                episodes.add(episode);
            }

            int nrEpisod = episodes.size();
            int nrEpisod1 = 1;
            for (Episode episode : episodes) {
                message = message + episode.getName() + " - " + episode.getDescription();
                if (nrEpisod1 < nrEpisod) {
                    message = message + ", ";
                }
                nrEpisod1++;
            }
            message = message + "]\n";
            if (nr2 < nr) {
                message = message + ", ";
            }
            nr2++;
        }

        message = message + "]\n\nAnnouncements:\n\t[";

        ArrayList<Announcement> announcements = new ArrayList<>();

        // retine anunturile
        for (Announcement announcement : Admin.getAnnouncementList()) {
            if (announcement.getOwner().equals(host.getUsername())) {
                announcements.add(announcement);
            }
        }

        nr = announcements.size();
        nr2 = 1;

        for (Announcement announcement : announcements) {
            message = message + announcement.getName() + ":\n\t" + announcement.getDescription();
            if (nr2 < nr) {
                message = message + ", ";
            }
            nr2++;
        }

        /// anunt
        if (nr == 0) {
            message = message + "]";
        } else {
            message = message + "\n]";
        }

        return message;
    }

    /**
     * afiseaza pagina unui user
     *
     * @param user
     * @return
     */
    public String visit(final User user) {
        String message = null;

        if (user.getPage().getTypePage().equals("Home")) {
            ArrayList<Song> songs = new ArrayList<>();
            for (Song song : user.getLikedSongs()) {
                songs.add(song);
            }

            Collections.sort(songs, (song1, song2) -> {
                return song2.getLikes() - song1.getLikes();
            });

            ArrayList<String> likeSong = new ArrayList<>();
            int nr = 0;
            for (Song song : songs) {
                if (nr < LIMIT) {
                    likeSong.add(song.getName());
                }
                nr++;
            }
            ArrayList<String> followPList = new ArrayList<>();
            nr = 0;
            for (Playlist playlist : user.getFollowedPlaylists()) {
                if (nr < LIMIT) {
                    followPList.add(playlist.getName());
                }
                nr++;
            }

            message = "Liked songs:\n\t" + likeSong + "\n\nFollowed playlists:\n\t" + followPList;

            // pentru songRecommendation
            ArrayList<String> songRecommendations = new ArrayList<>();
            for (Song song : user.getSongRecommendation()) {
                songRecommendations.add(song.getName());
            }

            // pentru playlistRecommendation
            ArrayList<String> playlistRecommendations = new ArrayList<>();
            for (Playlist playlist : user.getPlaylistRecommendation()) {
                playlistRecommendations.add(playlist.getName());
            }

            // adaugare
            if (songRecommendations.size() > 0 || playlistRecommendations.size() > 0) {
                message = message + "\n\nSong recommendations:\n\t" + songRecommendations;
                message = message + "\n\nPlaylists recommendations:\n\t" + playlistRecommendations;
            }

            return message;

        } else if (user.getPage().getTypePage().equals("LikedContent")) {
            ArrayList<Song> likeSong = new ArrayList<>();
            for (Song song : user.getLikedSongs()) {
                likeSong.add(song);
            }
            ArrayList<Playlist> followPList = new ArrayList<>();
            for (Playlist playlist : user.getFollowedPlaylists()) {

                followPList.add(playlist);

            }

            message = "Liked songs:\n\t[";
            int nr = likeSong.size();
            int nr2 = 1;
            for (Song song : likeSong) {
                message = message + song.getName() + " - " + song.getArtist();
                if (nr2 < nr) {
                    message = message + ", ";
                }
                nr2++;
            }
            message = message + "]\n\nFollowed playlists:\n\t[";

            nr = followPList.size();
            nr2 = 1;
            for (Playlist playlist : followPList) {
                message = message + playlist.getName() + " - " + playlist.getOwner();
                if (nr2 < nr) {
                    message = message + ", ";
                }
                nr2++;
            }
            message = message + "]";

            return message;

        } else {
            User user3 = null;
            for (User user2 : Admin.getUserList()) {
                if (user2.getUsername().equals(user.getPage().getTypePage())) {
                    user3 = user2;
                    break;
                }
            }
            if (user3.getType().equals("artist")) {
                return visit((Artist) user3);
            } else if (user3.getType().equals("host")) {
                return visit((Host) user3);
            }

        }
        return message;
    }
}
