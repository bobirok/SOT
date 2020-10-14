package client;

import org.glassfish.jersey.client.ClientConfig;
import shared.models.Book;

import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;

public class LibraryClient {
    private String _url = "http://localhost:8081/library/";
    private ClientConfig _clientConfig;
    private Client _client;
    private URI _baseUri;
    private WebTarget _serviceTarget;

    public LibraryClient() {
        this._clientConfig = new ClientConfig();
        this._client = ClientBuilder.newClient(this._clientConfig);
        this._baseUri = UriBuilder.fromUri(this._url).build();
        this._serviceTarget = this._client.target(this._baseUri);
    }

    public void getAllBooks() {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book/all")
                .request().accept(MediaType.APPLICATION_JSON);

        Response response = requestBuilder.get();

        if(response.getStatus() == 200) {
            GenericType<ArrayList<Book>> genericType = new GenericType<ArrayList<Book>>(){};
            ArrayList<Book> entity = response.readEntity(genericType);

            for (int i = 0; i < entity.size(); i++) {
                System.out.println(entity.get(i).toString());
            }
            System.out.println();
        } else {
            System.out.println(response);
        }
    }

    public void getAllBorrowedBooks() {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book/borrowed")
                .request().accept(MediaType.APPLICATION_JSON);

        Response response = requestBuilder.get();

        if(response.getStatus() == 200) {
            GenericType<ArrayList<Book>> genericType = new GenericType<ArrayList<Book>>(){};
            ArrayList<Book> entity = response.readEntity(genericType);

            for (int i = 0; i < entity.size(); i++) {
                System.out.println(entity.get(i).toString());
            }
            System.out.println();
        } else {
            System.out.println(response);
        }
    }

    public void getBookById(int id) {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book/"+id)
                .request().accept(MediaType.APPLICATION_JSON);

        Response response = requestBuilder.get();

        if(response.getStatus() == 200) {
            Book product = response.readEntity(Book.class);

            System.out.println();
            System.out.println("You asked for the book:");
            System.out.println(product.toString());
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }

    public void getBookByTitle(String title) {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book/").queryParam("title", title)
                .request().accept(MediaType.APPLICATION_JSON);

        Response response = requestBuilder.get();

        if(response.getStatus() == 200) {
            Book product = response.readEntity(Book.class);

            System.out.println();
            System.out.println("You asked for product:");
            System.out.println(product.toString());
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }

    public void createBook(String title, String author, int availableCopies) {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book")
                .request().accept(MediaType.TEXT_PLAIN);

        Form form = new Form();
        form.param("title", title);
        form.param("author", String.valueOf(author));
        form.param("availableCopies", String.valueOf(availableCopies));

        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);
        Response response = requestBuilder.accept(MediaType.APPLICATION_JSON_TYPE).post(entity);

        if(response.getStatus() == 201) {
            System.out.println();
            System.out.println("Created...");
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }

    public void deleteBook(int id) {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book/delete").queryParam("id", id)
                .request().accept(MediaType.TEXT_PLAIN);

        Response response = requestBuilder.delete();

        if(response.getStatus() == 200) {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }

    public void updateBook(Book updatedBook) {
        Invocation.Builder requestBuilder = this._serviceTarget.path("book")
                .request().accept(MediaType.APPLICATION_JSON_TYPE);

        Response response = requestBuilder.put(Entity.json(updatedBook));

        if(response.getStatus() == 200) {
            System.out.println();
            Book book = response.readEntity(Book.class);
            System.out.println(book.toString());
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }

    public void borrowBook(int id) {
        Response response = _serviceTarget.path("book/"+id+"/borrow")
                .request().accept(MediaType.TEXT_PLAIN).post(Entity.json(null));

        if(response.getStatus() == 200) {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        } else {
            System.out.println();
            System.out.println(response.readEntity(String.class));
            System.out.println();
        }
    }
}
