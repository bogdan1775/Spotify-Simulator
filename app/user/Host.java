package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.player.PlayerSource;
import app.utils.Enums;
import fileio.input.CommandInput;
import visit.Visitor;

public final class Host extends User {
    public Host(final String username, final int age, final String city) {
        super(username, age, city);
    }

    @Override
    public String accept(final Visitor visitor) {
        return visitor.visit(this);
    }

    /**
     * Verifica daca un host poate fi sters
     *
     * @param commandInput
     * @return
     */
    public static String deleteHost(final CommandInput commandInput) {
        String message;

        // verificare daca cineva ii asculta podcastul
        for (User user2 : Admin.getUserList()) {
            if (user2.getPlayer() != null && user2.getPlayer().getSource() != null) {
                PlayerSource playerSource = user2.getPlayer().getSource();
                if (playerSource.getType() == Enums.PlayerSourceType.PODCAST) {
                    if (((Podcast) playerSource.getAudioCollection()).getOwner().equals(commandInput.getUsername())) {
                        message = commandInput.getUsername() + " can't be deleted.";
                        return message;
                    }
                }
            }
        }

        return null;
    }
}
