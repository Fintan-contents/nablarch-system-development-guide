package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.common.dao.DaoFactory;
import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import com.nablarch.example.climan.entity.Client;
import nablarch.common.dao.DaoContext;
import nablarch.core.repository.SystemRepository;

import java.util.List;

/**
 * Service class for client management.
 */
public class ClientService {

    /** {@link DaoContext} */
    private final DaoContext daoContext;

    /**
     * Generates services.
     */
    public ClientService() {
        this(DaoFactory.create());
    }

    /**
     * Generates services for testing.
     * @param daoContext {@link DaoContext} for testing
     */
    ClientService(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    /**
     * Searches for clients.
     *
     * Client names and industry codes can be specified as search conditions. Client IDs are not used even when specified.
     *
     * @param condition Search conditions
     * @return Search results. A blank this is returned if no search results can be found
     * @throws SearchResultUpperLimitException Occurs when search results exceed the maximum number
     */
    public List<Client> findClient(Client condition) {
        long limit = Long.parseLong(SystemRepository.get("app.common.search.limit"));
        long count = daoContext.countBySqlFile(Client.class, "FIND_CLIENT", condition);
        if (count > limit) {
            throw new SearchResultUpperLimitException(limit);
        }
        return daoContext.findAllBySqlFile(Client.class, "FIND_CLIENT", condition);
    }

    /**
     * Acquires clients.
     *
     * @param clientId Client ID
     * @return Clients
     * @throws nablarch.common.dao.NoDataException Occurs when no clients exist
     */
    public Client findClientById(Integer clientId) {
        return daoContext.findById(Client.class, clientId);
    }

    /**
     * Registers client.
     *
     * @param client Client to be registered
     * @throws DuplicateRegistrationException Occurs when the client name that is specified is already registered
     */
    public void registerClient(Client client) {
        long count = daoContext.countBySqlFile(Client.class, "FIND_CLIENT_BY_CLIENT_NAME", client);
        if (count > 0) {
            throw new DuplicateRegistrationException();
        }
        daoContext.insert(client);
    }
}
