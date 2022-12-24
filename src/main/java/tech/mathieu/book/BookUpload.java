package tech.mathieu.book;

import java.io.InputStream;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;

public class BookUpload {
  @FormParam("file")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  private InputStream file;

  public InputStream getFile() {
    return file;
  }

  public void setFile(InputStream file) {
    this.file = file;
  }
}
