package rest;

import rest.dto.AbonnementRequest;
import services.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/abonnementen")
public class Abonnementen {

    @Inject
    SubscriptionService subscriptionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAbonnementen(@QueryParam("token") String token) {
        try {
            return Response.ok().entity(subscriptionService.getAllSubscriptionsFromUser(token)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response addAbonnement(@QueryParam("token") String token, AbonnementRequest abonnementRequest) {
//
//        try {
//            subscriptionService.
//        } catch (SQLException e) {
//
//        }
//        return Response.ok().entity(abonnementenDaoImpl.addSubscriptionToUser(token, abonnementRequest.getId())).build();
//
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response showSubscriptionDetailsFromUser(@QueryParam("token") String token, @PathParam("id") int id) {
//        return Response.ok().entity(abonnementenDaoImpl.getAbonnementDetailsForUser(token, id)).build();
//    }
//
//    @DELETE
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response terminateSubscriptionFromUser(@QueryParam("token") String token, @PathParam("id") int id) {
//        return Response.ok().entity(abonnementenDaoImpl.terminateSubscriptionFromUser(token, id)).build();
//    }
//
//    @POST
//    @Path("{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response upgradeSubscriptionForUser(@QueryParam("token") String token, @PathParam("id") int id) {
//        return Response.ok().entity(abonnementenDaoImpl.upgradeSubscriptionForUser(token, id)).build();
//    }
//
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAvailableSubscriptions(@QueryParam("token") String token, @QueryParam("filter") String filter) {
        try {
            return Response.ok().entity(subscriptionService.getAllAvailableSubscriptionsForUser(token).getAbonnement(filter)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

}
