package rest;

import rest.dto.LoginRequest;
import rest.dto.LoginResponse;
import services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class Login {

    @Inject
    private LoginService loginService;

    private LoginResponse loginResponse = new LoginResponse();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {

        if(loginService.doLogin(request.getUser(), request.getPassword())) {
            loginResponse.setUser(request.getUser());
            loginResponse.setToken(loginService.getTokenFromUser(request.getUser()));
            return Response.ok().entity(loginResponse).build();
        } else {
            return Response.status(403).build();
        }
    }
}
