package client;

import shared.models.Book;

import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {
        LibraryClient libraryClient = new LibraryClient();

        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        int bookId = 0;
        String bookTitle = "";
        String bookAuthor = "";
        int bookAvailableCopies = 0;

        printOptions();
        choice = scanner.nextInt();

        while(choice != 0) {
            switch (choice) {
                case 0:
                    break;
                case 1:
                    libraryClient.getAllBooks();
                    break;
                case 2:
                    libraryClient.getAllBorrowedBooks();
                    break;
                case 3:
                    System.out.println("Please select an id: ");
                    bookId = scanner.nextInt();
                    libraryClient.getBookById(bookId);
                    break;
                case 4:
                    System.out.println("Please select the title name: ");
                    scanner.nextLine();
                    bookTitle = scanner.nextLine();
                    libraryClient.getBookByTitle(bookTitle);
                    break;
                case 5:
                    scanner.nextLine();
                    System.out.println("Please select title of the book: ");
                    bookTitle = scanner.nextLine();
                    System.out.println("Please type the author of the book: ");
                    bookAuthor = scanner.nextLine();
                    System.out.println("Please type the available copies of the book: ");
                    bookAvailableCopies = scanner.nextInt();
                    libraryClient.createBook(bookTitle, bookAuthor, bookAvailableCopies);
                    break;
                case 6:
                    System.out.println("Please select id of the book you want to delete: ");
                    bookId = scanner.nextInt();
                    libraryClient.deleteBook(bookId);
                    break;
                case 7:
                    scanner.nextLine();
                    System.out.println("Please select the id of the book you want to update: ");
                    bookId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Please select title of the book: ");
                    bookTitle = scanner.nextLine();
                    System.out.println("Please type the author of the book: ");
                    bookAuthor = scanner.nextLine();
                    System.out.println("Please type the available copies of the book: ");
                    bookAvailableCopies = scanner.nextInt();
                    libraryClient.updateBook(new Book(bookId, bookTitle, bookAuthor, bookAvailableCopies));
                    break;
                case 8:
                    System.out.println("Please select the book you would like to borrow: ");
                    bookId = scanner.nextInt();
                    libraryClient.borrowBook(bookId);
                    break;
            }

            printOptions();
            choice = scanner.nextInt();
        }
    }

    private static void printOptions() {
        System.out.println("To exit press 0");
        System.out.println("To get all books press 1");
        System.out.println("To get all borrowed books press 2");
        System.out.println("To search for a book by id press 3");
        System.out.println("To search for a book by title press 4");
        System.out.println("To create a book press 5");
        System.out.println("To delete a book press 6");
        System.out.println("To update a book press 7");
        System.out.println("To borrow a book press 8");
    }
}
