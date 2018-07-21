package nl.han.dea.controllers.subscription;

import nl.han.dea.dtos.subscription.SubscriptionRequest;
import nl.han.dea.services.subscription.SubscriptionService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/abonnementen")
public class Subscription {

    private final Logger LOGGER = Logger.getLogger(getClass().getName());

    @Inject
    SubscriptionService subscriptionService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptions(@QueryParam("token") String token) {
        try {
            return Response.ok().entity(subscriptionService.getAllSubscriptionsForUser(token)).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something wrong with getSubscription(). ", e);
        }
        return Response.status(403).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addSubscription(@QueryParam("token") String token, SubscriptionRequest subscriptionRequest) {

        try {
            return Response.ok().entity(subscriptionService.addSubscriptionToUser(token, subscriptionRequest.getId())).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something wrong with addSubscription(). ", e);
        }
        return Response.status(403).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showSubscriptionDetailsForUser(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok().entity(subscriptionService.getSubscriptionDetailsForUser(token, id)).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something wrong with showSubscriptionDetailsForUser(). ", e);
        }
        return Response.status(403).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response terminateSubscriptionForUser(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok().entity(subscriptionService.terminateSubscriptionFromUser(token, id)).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something wrong with terminateSubscriptionForUser(). ", e);
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
            LOGGER.log(Level.SEVERE, "Something wrong with upgradeSubscriptionForUser(). ", e);
        }
        return Response.status(403).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showAvailableSubscriptionsForUser(@QueryParam("token") String token, @QueryParam("filter") String filter) {
        try {
            return Response.ok().entity(subscriptionService.getAllAvailableSubscriptionsForUser(token).getAbonnement(filter)).build();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Something wrong with showAvailableSubscriptionsForUser(). ", e);
        }
        return Response.status(403).build();
    }

}
