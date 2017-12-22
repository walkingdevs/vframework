import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/rs")
public class UserResource {
    private final PersonDAO dao;

    public UserResource(PersonDAO dao) {
        this.dao = dao;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Person findPerson(@PathParam("id") LongParam id) {
        return dao.findById(id.get());
    }

    @GET
    @Path("/post/{name}")
    @Timed
    @UnitOfWork
    public Long postPerson(@PathParam("name") String name) {
        return dao.create(name);
    }
}
