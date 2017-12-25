package deploy;

import javax.ws.rs.*;

@Path("planet")
public class PlanetRs {
    @GET
    public Planet get() {
        Planet planet = new Planet();
        planet.setName("Uranus");
        return planet;
    }
}