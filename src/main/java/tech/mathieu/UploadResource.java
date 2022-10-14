package tech.mathieu;

import io.smallrye.common.annotation.Blocking;
import org.jboss.resteasy.reactive.MultipartForm;
import tech.mathieu.book.BookService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.FileInputStream;
import java.io.IOException;


@Path("/api/upload/multi")
public class UploadResource {

    @Inject
    BookService bookService;

    @POST
    @Blocking
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void upload(@MultipartForm MultipartBody form) {
        form.file.forEach(file -> {
            try (var in = new FileInputStream(file)) {
                bookService.saveBook(in);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
