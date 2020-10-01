package shared.models-shared;

public class Book {
    private int _id;
    private String _title;
    private String _author;
    private int _availableCopies;

    public Book() {}

    public Book(int id, String title, String author, int availableCopies) {
        this._id = id;
        this._title = title;
        this._author = author;
        this._availableCopies = availableCopies;
    }

    public int getId() {
        return this._id;
    }

    public String getTitle() {
        return this._title;
    }

    public String getAuthor() {
        return this._author;
    }

    public int getAvailableCopies() {
        return this._availableCopies;
    }

    public void borrowCopyOfBook() {
        this._availableCopies--;
    }

    public void setId(int value) {
        this._id = value;
    }

    public void setTitle(String value) {
        this._title = value;
    }

    public void setAuthor(String value) {
        this._author = value;
    }

    public void setAvailableCopies(int value) {
        this._availableCopies = value;
    }

    public boolean hasAvailableCopies() {
        return this._availableCopies > 0;
    }

    @Override
    public String toString() {
        return "ID: " + this._id + " title: " + this._title + " author: " + this._author;
    }
}
