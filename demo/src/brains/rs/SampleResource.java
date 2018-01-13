package brains.rs;


import brains.dao.PersonDAO;
import brains.entity.Person;
import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/brains/rs")
@Produces("application/json")
public class SampleResource {

    @Inject
    private PersonDAO dao;

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