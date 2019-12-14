/**
 * This class represents a patron, which has a first name, last name, tendency to comic books, dramatic books
 * and educational books and an enjoyment threshold.
 */
public class Patron {

    /** The first name of the patron. */
    private final String firstName;

    /** The last name of the patron. */
    private final String lastName;

    /** The comic tendency of the patron. */
    private final int comic;

    /** The dramatic tendency of the patron. */
    private final int dramatic;

    /** The educational tendency of the patron. */
    private final int educational;

    /** The minimal score a book must be assigned by the patron in order for the patron to enjoy that book. */
    private final int enjoymentThreshold;

    /**
     * Creates a new patron with the given characteristic.
     * @param patronFirstName The first name of the patron.
     * @param patronLastName The last name of the patron
     * @param comicTendency The comic tendency of the patron.
     * @param dramaticTendency The dramatic tendency of the patron.
     * @param educationalTendency The educational tendency of the patron.
     * @param patronEnjoymentThreshold The minimal score a book must be assigned by the patron in order for
     *                                 the patron to enjoy that book.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold){
        this.firstName = patronFirstName;
        this.lastName = patronLastName;
        this.comic = comicTendency;
        this.dramatic = dramaticTendency;
        this.educational = educationalTendency;
        this.enjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * @param book The book to access.
     * @return Returns the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book){
        int comicScore = book.getComicValue() * this.comic;
        int dramaticScore = book.getDramaticValue() * this.dramatic;
        int educationalScore = book.getEducationalValue() * this.educational;
        return comicScore + dramaticScore + educationalScore;
    }

    /**
     * @return String representation of the patron, which is a sequence of its first and last name, separated
     * by a single white space.
     */
    String stringRepresentation(){
        return this.firstName + " " + this.lastName;
    }

    /**
     * @param book The book to access.
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book){
        return (this.getBookScore(book) >= this.enjoymentThreshold);
    }
}
