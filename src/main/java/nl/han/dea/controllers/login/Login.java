package nl.han.dea.controllers.login;

import nl.han.dea.dtos.login.LoginRequest;
import nl.han.dea.dtos.login.LoginResponse;
import nl.han.dea.services.login.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class Login {

    @Inject
    private LoginService loginService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) throws SQLException {
        LoginResponse loginResponse = new LoginResponse();

        if(loginService.doLogin(request.getUser(), request.getPassword())) {
            loginResponse.setUser(request.getUser());
            loginResponse.setToken(loginService.getTokenFromUser(request.getUser()));
            return Response.ok().entity(loginResponse).build();
        } else {
            return Response.status(403).build();
        }
    }
}
