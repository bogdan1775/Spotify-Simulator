package visit;

public interface Visitable {
    /**
     * accepta un visitor
     * @param visitor
     * @return
     */
    String accept(Visitor visitor);
}
