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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Action class for client management.
 *
 * @author TIS
 */
public class ClientAction {

    /**
     * Searches for clients.
     * @param request: HTTP request
     * @param context: Context for execution
     * @return: Search results
     */
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
     * @param request: HTTP request
     * @param context: Context for execution
     * @return: Client details
     */
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
     * @param form: Client registration form
     * @return: HTTP response
     */
    @Valid
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse register(ClientForm form) {
        ClientService service = new ClientService();
        Client client = BeanUtil.createAndCopy(Client.class, form);
        service.registerClient(client);
        return new HttpResponse().setStatusCode(HttpResponse.Status.CREATED.getStatusCode());
    }
}
