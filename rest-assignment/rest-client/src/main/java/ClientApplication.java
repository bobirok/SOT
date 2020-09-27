import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {
        LibraryClient libraryClient = new LibraryClient();

        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        int bookId = 0;
        String bookTitle = "";

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
                    System.out.println(bookTitle);
                    libraryClient.getBookByTitle(bookTitle);
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
