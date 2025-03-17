package app.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.Admin;
import app.Fan;
import app.SongWrapped;
import app.audio.Collections.Playlist;
import app.audio.Files.Album;
import app.audio.Files.Song;
import app.player.PlayerSource;
import app.utils.Enums;
import fileio.input.CommandInput;
import visit.Visitor;

public final class Artist extends User {
    private int nrLikes;
    private int listenings;

    public int getListenings() {
        return listenings;
    }

    public void setListenings(final int listenings) {
        this.listenings = listenings;
    }

    /**
     * adauga ascultari la numarul de ascultari
     *
     * @param listeningss
     */
    public void addListenings(final int listeningss) {
        this.listenings += listeningss;
    }

    public Artist(final String username, final int age, final String city) {
        super(username, age, city);
        listenings = 0;
    }

    public int getNrLikes() {
        return nrLikes;
    }

    public void setNrLikes(final int nrLikes) {
        this.nrLikes = nrLikes;
    }

    /**
     * Verifica daca exista deja o melodie intr-un album
     *
     * @param songName
     * @param artistName
     * @return
     */
    public int verifSong(final String songName, final String artistName) {
        // verifica daca exista deja o melodie cu acelasi nume
        for (Album album : Admin.getAlbumList()) {
            if (album.getOwner().equals(artistName)) {
                for (Song song : album.getSongs()) {
                    if (song.getName().equals(songName)) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String accept(final Visitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Verifica daca un artist poate fi sters
     *
     * @param commandInput
     * @return
     */
    public static String deleteArtist(final CommandInput commandInput) {
        String message;
        // verificare daca cineva ii asculta melodia
        for (User user : Admin.getUserList()) {
            if (user.getPlayer() != null && user.getPlayer().getSource() != null) {
                PlayerSource playerSource = user.getPlayer().getSource();
                if (playerSource.getType() == Enums.PlayerSourceType.LIBRARY) {
                    if (((Song) playerSource.getAudioFile()).getArtist().equals(commandInput.getUsername())) {
                        message = commandInput.getUsername() + " can't be deleted.";
                        return message;
                    }
                }
            }
        }

        // verificare daca cineva ii asculta albumul
        for (User user : Admin.getUserList()) {
            if (user.getPlayer() != null && user.getPlayer().getSource() != null) {
                PlayerSource playerSource = user.getPlayer().getSource();
                if (playerSource.getType() == Enums.PlayerSourceType.ALBUM) {
                    if (((Album) playerSource.getAudioCollection()).getOwner().equals(commandInput.getUsername())) {
                        message = commandInput.getUsername() + " can't be deleted.";
                        return message;
                    }
                }
            }
        }

        // verificare daca cineva asculta playlistul lui
        for (User user : Admin.getUserList()) {
            if (user.getPlayer() != null && user.getPlayer().getSource() != null) {
                PlayerSource playerSource = user.getPlayer().getSource();
                if (playerSource.getType() == Enums.PlayerSourceType.PLAYLIST) {
                    if (((Playlist) playerSource.getAudioCollection()).getOwner().equals(commandInput.getUsername())) {
                        message = commandInput.getUsername() + " can't be deleted.";
                        return message;
                    }
                }
            }
        }

        // verificare daca cineva asculta un playlist care are o melodie de-a lui
        for (User user : Admin.getUserList()) {
            if (user.getPlayer() != null && user.getPlayer().getSource() != null) {
                PlayerSource playerSource = user.getPlayer().getSource();
                message = Artist.verifPlaylist(playerSource, commandInput.getUsername());
                if (message != null) {
                    return message;
                }
            }
        }

        return null;
    }

    /**
     * verificare daca cineva asculta un playlist care are o melodie de-a lui
     *
     * @param playerSource
     * @param name
     * @return
     */
    public static String verifPlaylist(final PlayerSource playerSource, final String name) {
        String message;
        if (playerSource.getType() == Enums.PlayerSourceType.PLAYLIST) {
            for (Song song : ((Playlist) playerSource.getAudioCollection()).getSongs()) {
                if (song.getArtist().equals(name)) {
                    message = name + " can't be deleted.";
                    return message;
                }
            }
        }
        return null;
    }

    /**
     * Sterge melodiile de la LikeSong
     *
     * @param artist
     */
    public static void deleteLikeSong(final Album album) {
        for (User user : Admin.getUserList()) {
            for (int i = 0; i < user.getLikedSongs().size(); i++) {
                Song song = user.getLikedSongs().get(i);
                if (album.getSongs().contains(song)) {
                    user.getLikedSongs().remove(i);
                    i--;
                }
            }

        }
    }

    /**
     * calculeaza numarul de listenings pentru melodiile unui artist
     *
     * @param artist
     * @return
     */
    public static List<SongWrapped> calculateListeningsSongs(final Artist artist) {
        List<SongWrapped> songs = new ArrayList<>();
        for (Song song : Admin.getSongsHistory()) {
            // creeaza un song de tipulSongWrapped
            SongWrapped songWrapped = new SongWrapped();
            songWrapped.setName(song.getName());
            songWrapped.setArtist(song.getArtist());
            songWrapped.setListenings(song.getListenings());
            int ok = 0;

            for (SongWrapped songWrapped1 : songs) {
                // daca se gaseste in lista, se aduna numarul de ascultari
                if (songWrapped1.getName().equals(songWrapped.getName())
                        && songWrapped1.getArtist().equals(songWrapped.getArtist())) {
                    songWrapped1.setListenings(songWrapped1.getListenings() + songWrapped.getListenings());
                    ok = 1;
                    break;
                }
            }

            // daca nu s-a gasit in lista, se adauga
            if (ok == 0) {
                songs.add(songWrapped);
            }
        }

        for (int i = 0; i < songs.size(); i++) {
            if (!songs.get(i).getArtist().equals(artist.getUsername())) {
                songs.remove(i);
                i--;
            }
        }

        // se sorteaza dupa numarul de ascultari
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
     * calculeaza numarul de ascultari pentru un artist
     *
     * @param artist
     * @return
     */
    public static List<Fan> calculateFans(final Artist artist) {
        List<Fan> fans = new ArrayList<>();
        for (User user : Admin.getUserList()) {
            User.addSongs(user);

            // sterge AdBreak-ul din lista de melodii ascultate
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

            // daca a ascultat macar o melodie a artistului, se adauga la lista de fani
            if (nr2 > 0) {
                Fan fan = new Fan();
                fan.setName(user.getUsername());
                fan.setListenings(nr2);
                fans.add(fan);
            }

        }

        // se ordoneaza lista cu fani
        Collections.sort(fans, (fan1, fan2) -> {
            if (fan1.getListenings() == fan2.getListenings()) {
                return fan1.getName().compareTo(fan2.getName());
            } else {
                return Integer.compare(fan2.getListenings(), fan1.getListenings());
            }
        });

        return fans;
    }

    /**
     * calculeaza numarul de ascultari pentru albumele unui artist
     *
     * @param artist
     * @return lista cu albume
     */
    public static List<Album> calculateTopAlbums(final Artist artist) {
        List<Album> albums = new ArrayList<>();
        for (Album album : Admin.getAlbumList()) {
            int listenings = 0;
            // se calculeaza pentru fiecare album numarul de ascultari
            for (Song song : Admin.getSongsHistory()) {
                if (song.getAlbum().equals(album.getName()) && song.getArtist().equals(album.getOwner())) {
                    listenings += song.getListenings();
                }
            }

            album.setListenings(listenings);

            // se adauga in lista doar albumele care au cel putin o ascultare
            if (album.getListenings() > 0) {
                albums.add(album);
            }
        }

        // se pastreaza doar albumele artistului
        for (int i = 0; i < albums.size(); i++) {
            if (!albums.get(i).getOwner().equals(artist.getUsername())) {
                albums.remove(i);
                i--;
            }
        }

        // se sorteaza dupa numarul de ascultari
        Collections.sort(albums, (album1, album2) -> {
            if (album1.getListenings() == album2.getListenings()) {
                return album1.getName().compareTo(album2.getName());
            } else {
                return album2.getListenings() - album1.getListenings();
            }
        });

        return albums;
    }

}
