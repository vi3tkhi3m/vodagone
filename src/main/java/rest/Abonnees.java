//package rest;
//
//import datasource.AbonneesDaoImpl;
//import datasource.util.DatabaseProperties;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//@Path("/abonnees")
//public class Abonnees {
//
////    private AbonneesDaoImpl abonneesDaoImpl = new AbonneesDaoImpl(new DatabaseProperties());
////
////    @GET
////    @Produces(MediaType.APPLICATION_JSON)
////    public Response getAllSubscribers(@QueryParam("token") String token) {
////        return Response.ok().entity(abonneesDaoImpl.getAllSubscribers(token).getSubscribers()).build();
////    }
////
////    @POST
////    @Path("{id}")
////    @Consumes(MediaType.APPLICATION_JSON)
////    public Response shareSubscriptionWithSubscriber(@QueryParam("token") String token, @PathParam("id") int user_id) {
////        return Response.status(200).build();
////    }
//
//
//}
