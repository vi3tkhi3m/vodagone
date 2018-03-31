package rest;

import rest.dto.SubscriptionRequest;
import services.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/abonnementen")
public class Subscription {

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAbonnement(@QueryParam("token") String token, SubscriptionRequest subscriptionRequest) {

        try {
            return Response.ok().entity(subscriptionService.addSubscriptionToUser(token, subscriptionRequest.getId())).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showSubscriptionDetailsFromUser(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok().entity(subscriptionService.getAbonnementDetailsForUser(token, id)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateSubscriptionFromUser(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok().entity(subscriptionService.terminateSubscriptionFromUser(token, id)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

    @POST
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response upgradeSubscriptionForUser(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok().entity(subscriptionService.upgradeSubscriptionForUser(token, id)).build();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }

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
