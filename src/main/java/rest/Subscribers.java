package rest;

import rest.dto.ShareRequest;
import services.SubscriberService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/abonnees")
public class Subscribers {

    @Inject
    SubscriberService subscriberService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSubscribers(@QueryParam("token") String token) {
        return Response.ok().entity(subscriberService.getAllAvailableSubscribers(token).getSubscribers()).build();
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shareSubscriptionWithSubscriber(@QueryParam("token") String token, @PathParam("id") int subscriber_id, ShareRequest shareRequest) {
        return Response.ok().entity(subscriberService.shareSubscriptionWithSubscriber(token, shareRequest.getId(), subscriber_id)).build();
    }


}
