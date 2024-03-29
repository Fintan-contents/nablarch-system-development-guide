package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.common.validation.BindingResult;
import com.nablarch.example.climan.common.validation.FormBinder;
import com.nablarch.example.climan.entity.Client;
import nablarch.core.beans.BeanUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;
import nablarch.fw.web.HttpResponse;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
    public List<Client> list(HttpRequest request, ExecutionContext context) {

        BindingResult<ClientSearchForm> bindingResult = FormBinder.from(request, context).to(ClientSearchForm.class);
        bindingResult.abortIfInvalid();
        ClientSearchForm form = bindingResult.getValidForm();

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
    public Client show(HttpRequest request, ExecutionContext context) {

        BindingResult<ClientGetForm> bindingResult = FormBinder.from(request, context).to(ClientGetForm.class);
        bindingResult.abortIfInvalid();
        ClientGetForm form = bindingResult.getValidForm();

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
