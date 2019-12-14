/**
 * This class represents a patron, which has a first name, last name, tendency to comic books, dramatic books
 * and educational books and an enjoyment threshold.
 */
public class Library {

    /** The maximal number of books this library can hold. */
    private final int maxBookCap;

    /** The maximal number of books this library allows a single patron to borrow at the same time. */
    private final int maxBorBooks;

    /** The maximal number of registered patrons this library can handle. */
    private final int maxPatronCap;

    /** An array of all the books in this library. */
    private Book[] booksArray;

    /** An array of all the patron the registered to this library. */
    private Patron[] patronsArray;


    /**
     * Creates a new library with the given parameters.
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow at
     *                          the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        this.maxBookCap = maxBookCapacity;
        this.maxBorBooks = maxBorrowedBooks;
        this.maxPatronCap = maxPatronCapacity;
        this.booksArray = new Book[this.maxBookCap];
        this.patronsArray = new Patron[this.maxPatronCap];
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        for (int i = 0; i < this.maxBookCap; i++) {
            if (this.booksArray[i] == book) {
                return i;
            } else if (this.booksArray[i] == null) {
                booksArray[i] = book;
                return i;
            }
        } return -1;
    }

    /**
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if (bookId < 0 || bookId >= this.maxBookCap) {
            return false;
        } return (this.booksArray[bookId] != null);
    }

    /**
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < this.maxBookCap; i++) {
            if (this.booksArray[i] == book) {
                return i;
            }
        } return -1;
    }

    /**
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (this.isBookIdValid(bookId)) {
            return (this.booksArray[bookId].getCurrentBorrowerId() == -1);
        } else {
            return false;
        }
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     *         registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        for (int i = 0; i < this.maxPatronCap; i++) {
            if (this.patronsArray[i] == patron) {
                return i;
            } else if (this.patronsArray[i] == null) {
                patronsArray[i] = patron;
                return i;
            }
        } return -1;
    }

    /**
     * @param patronId The id to check
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if (patronId < 0 || patronId >= this.maxPatronCap) {
            return false;
        } return (this.patronsArray[patronId] != null);
    }

    /**
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < this.maxPatronCap; i++) {
            if (this.patronsArray[i] == patron) {
                return i;
            }
        } return -1;
    }

    /**
     * @param patronId Patron id to check.
     * @return true if this patron can borrow more books (if he didn't borrow the maximum, false otherwise.
     */
    private boolean patronCanBorrow(int patronId) {
        if (!isPatronIdValid(patronId)) {
            return false;
        }
        int counter = 0;
        for (int i = 0; i < this.maxBookCap; i++) {
            if (this.booksArray[i] != null && this.booksArray[i].getCurrentBorrowerId() == patronId) {
                counter++;
            }
        } return (counter < this.maxBorBooks);
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (isBookIdValid(bookId) && isPatronIdValid(patronId)) {
            Patron patron = this.patronsArray[patronId];
            Book book = this.booksArray[bookId];
            if (this.isBookAvailable(bookId) && this.patronCanBorrow(patronId) &&
                    patron.willEnjoyBook(book)) {
                book.setBorrowerId(patronId);
                return true;
            }
        }return false;
    }

    /**
     * Return the given book to the library.
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId){
        this.booksArray[bookId].returnBook();
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId){
        Patron patron = this.patronsArray[patronId];
        Book bestBook = null;
        int bestScore = 0;
        for (int i=0; i<this.maxBookCap; i++){
            Book book = this.booksArray[i];
            int bookScore = patron.getBookScore(book);
            if (this.isBookAvailable(i) && patron.willEnjoyBook(book) && (bookScore > bestScore)) {
                bestBook = book;
                bestScore = bookScore;
            }
        } return bestBook;
    }
}
