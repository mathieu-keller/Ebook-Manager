package tech.mathieu;

import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.util.List;

public class MultipartBody {

  @FormParam("myFiles")
  @PartType(MediaType.APPLICATION_OCTET_STREAM)
  public List<File> file;

}
