package library.resources;

import library.controllers.LibraryController;
import library.exceptions.BookExistsException;
import library.exceptions.BookNotFoundException;
import library.exceptions.BookOutOfOrderException;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Singleton
@Path("/library")
public class LibraryResources {
    private ArrayList<Book> _books = new ArrayList<Book>();
    private ArrayList<Book> _borrowedBooks = new ArrayList<>();
    private LibraryController _libraryController = new LibraryController(_books, _borrowedBooks);

    @Path("/book/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
        ArrayList<Book> libraryBooks = this._libraryController.getAllBooks();

        return Response.ok(libraryBooks).build();
    }

    @Path("/book/borrowed")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBorrowedBooks() {
        ArrayList<Book> libraryBorrowedBooks = this._libraryController.getAllBorrowedBooks();

        return Response.ok(libraryBorrowedBooks).build();
    }

    @Path("/book/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id) {
        try {
            Book book = this._libraryController.getBookById(id);

            return Response.ok(book).build();
        } catch(BookNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @Path("/book")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByTitle(@QueryParam("title") String title) {
        try {
            Book book = this._libraryController.getBookByTitle(title);

            return Response.ok(book).build();
        } catch(BookNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @Path("/book")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBook(
            @FormParam("title") String title,
            @FormParam("author") String author,
            @FormParam("availableCopies") int availableCopies
    ) {
        try {
            this._libraryController.createBook(title, author, availableCopies);

            return Response.status(201).entity("Created...").build();
        } catch(BookExistsException e) {
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

    @Path("/book")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book) {
        try {
            System.out.println(book);
            Book updatedBook = this._libraryController.updateBook(book);

            return Response.ok(updatedBook).build();
        } catch(BookNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @Path("/book")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(@QueryParam("id") int id) {
        try {
            this._libraryController.deleteBook(id);

            return Response.ok("Deleted...").build();
        } catch(BookNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @Path("/book/{id}/borrow")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response borrowBook(@PathParam("id") int id) {
        try {
            this._libraryController.borrowCopyOfBook(id);

            return Response.ok("Borrowed...").build();
        } catch(BookNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch(BookOutOfOrderException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
