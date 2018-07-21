package nl.han.dea.controllers.subscribers;

import nl.han.dea.dtos.subscriber.ShareRequest;
import nl.han.dea.services.subscriber.SubscriberService;

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
        if(!subscriberService.getAllAvailableSubscribersForUser(token).getSubscribers().isEmpty()) {
            return Response.ok().entity(subscriberService.getAllAvailableSubscribersForUser(token).getSubscribers()).build();
        }
        return Response.status(403).build();
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shareSubscriptionWithSubscriber(@QueryParam("token") String token, @PathParam("id") int subscriber_id, ShareRequest shareRequest) {
        if(subscriberService.shareSubscriptionWithSubscriber(token, shareRequest.getId(), subscriber_id)) {
            Response.ok().build();
        }
        return Response.status(403).build();
    }


}
