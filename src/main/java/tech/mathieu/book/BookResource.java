package tech.mathieu.book;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/book")
public class BookResource {

    @Inject
    BookService bookService;

    @Path("{book-title}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public BookDto getBook(@PathParam("book-title") String bookTitle) {
        return bookService.getBookDto(bookTitle);
    }

}
