package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.entity.Client;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.jaxrs.JaxRsHttpRequest;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Action class for client management.
 *
 * @author Masaya Seko
 */
@Path("/client")
public class ClientAction {

    /**
     * Searches for clients.
     * @param request HTTP request
     * @param context Context for execution
     * @return Search results
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(JaxRsHttpRequest request, ExecutionContext context) {

        ClientSearchForm form = BeanUtil.createAndCopy(ClientSearchForm.class, request.getParamMap());
        ValidatorUtil.validate(form);

        ClientService service = new ClientService();
        Client condition = BeanUtil.createAndCopy(Client.class, form);
        return service.findClient(condition);
    }

    /**
     * Acquires client details.
     * @param request HTTP request
     * @param context Context for execution
     * @return Client details
     */
    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client show(JaxRsHttpRequest request, ExecutionContext context) {

        ClientGetForm form = BeanUtil.createAndCopy(ClientGetForm.class, request.getParamMap());
        ValidatorUtil.validate(form);

        ClientService service = new ClientService();
        Client condition = BeanUtil.createAndCopy(Client.class, form);
        return service.findClientById(condition.getClientId());
    }

    /**
     * Registers client.
     *
     * @param form Client registration form
     * @return Client details
     */
    @POST
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client register(ClientForm form) {
        ClientService service = new ClientService();
        Client client = BeanUtil.createAndCopy(Client.class, form);
        return service.registerClient(client);
    }
}
