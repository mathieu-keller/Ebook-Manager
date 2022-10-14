package tech.mathieu.subject;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/subjects")
public class SubjectResource {

  @Inject
  SubjectService subjectService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<SubjectDto> getSubjects() {
    return subjectService.getDtos();
  }
}
