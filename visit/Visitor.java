package visit;

import app.user.Artist;
import app.user.User;
import app.user.Host;

public interface Visitor {
    /**
     * viziteaza un user
     * @param user
     * @return
     */
    String visit(User user);

    /**
     * viziteaza un artist
     * @param artist
     * @return
     */
    String visit(Artist artist);

    /**
     * viziteaza un host
     * @param host
     * @return
     */
    String visit(Host host);
}
