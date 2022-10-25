package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import com.nablarch.example.climan.entity.Client;
import com.nablarch.example.climan.rest.common.dao.DaoStub;
import com.nablarch.example.climan.rest.common.repository.ConfigLoader;
import nablarch.common.dao.EntityList;
import nablarch.common.dao.NoDataException;
import nablarch.core.repository.SystemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ClientService test class.
 */
class ClientServiceTest {

    @BeforeAll
    static void setUp() {
        ConfigLoader.load();
    }

    /**
     * Test executed when the search results in a client search do not exceed the maximum number.
     *
     * Search results are returned.
     */
    @Test
    void testFindClient() {
        final Map<String, Boolean> invoked = new HashMap<>();
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("findAllBySqlFile", true);
                return new EntityList(Arrays.asList(new Client(), new Client()));
            }

            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("countBySqlFile", true);
                return Long.parseLong(SystemRepository.get("app.common.search.limit"));
            }
        });

        List<Client> result = sut.findClient(new Client());
        assertTrue(invoked.get("countBySqlFile"));
        assertTrue(invoked.get("findAllBySqlFile"));
        assertEquals(2, result.size());
    }

    /**
     * Test executed when the search results in a client search exceed the maximum number.
     *
     * {@link SearchResultUpperLimitException} must be sent and a maximum number must be set.
     */
    @Test
    void testFindClientForSearchResultUpperLimitError() {
        final Map<String, Boolean> invoked = new HashMap<>();
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("countBySqlFile", true);
                return Long.parseLong(SystemRepository.get("app.common.search.limit")) + 1;
            }
        });

        SearchResultUpperLimitException e = Assertions.assertThrows(SearchResultUpperLimitException.class, () -> sut.findClient(new Client()));
        assertTrue(invoked.get("countBySqlFile"));
        long expected = Long.parseLong(SystemRepository.get("app.common.search.limit"));
        assertEquals(expected, e.getLimit());
    }

    /**
     * Test when there is one search result in the client detail retrieval.
     *
     * The search result should be returned.
     */
    @Test
    void testFindClientById() {
        final Map<String, Boolean> invoked = new HashMap<>();
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        client.setIndustryCode("01");
        ClientService sut = new ClientService(new DaoStub() {
            @SuppressWarnings({ "unchecked" })
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                invoked.put("findById", true);
                return (T) client;
            }
        });

        Client result = sut.findClientById(1);
        assertTrue(invoked.get("findById"));
        assertEquals(client, result);
    }

    /**
     * Test for no search result in client detail retrieval.
     *
     * {@link NoDataException} is sent.
     */
    @Test
    void testFindClientByIdThrowsNoDataException() {
        final Map<String, Boolean> invoked = new HashMap<>();
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                invoked.put("findById", true);
                throw new NoDataException();
            }
        });

        Assertions.assertThrows(NoDataException.class, () -> sut.findClientById(0));
        assertTrue(invoked.get("findById"));
    }

    /**
     * Test executed when data has not already been registered in the client registration process.
     *
     * Must be registered.
     */
    @Test
    void testRegisterClient() {

        final Map<String, Boolean> invoked = new HashMap<>();
        final Client client = new Client();
        client.setClientId(1);
        client.setClientName("Client1");
        client.setIndustryCode("01");

        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("countBySqlFile", true);
                return 0;
            }

            @Override
            public <T> void insert(T entity) {
                invoked.put("insert", true);
            }

            @Override
            public <T> T findBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("findBySqlFile", true);
                return (T) client;
            }
        });

        Client result = sut.registerClient(new Client());

        assertTrue(invoked.get("countBySqlFile"));
        assertTrue(invoked.get("insert"));
        assertTrue(invoked.get("findBySqlFile"));
        assertEquals(client, result);
    }

    /**
     * Test executed when data has already been registered in the client registration process.
     *
     * {@link DuplicateRegistrationException} is sent.
     */
    @Test
    void testRegisterClientForDuplicateRegistrationException() {
        final Map<String, Boolean> invoked = new HashMap<>();
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                invoked.put("countBySqlFile", true);
                return 1;
            }
        });

        Assertions.assertThrows(DuplicateRegistrationException.class, () -> sut.registerClient(new Client()));
        assertTrue(invoked.get("countBySqlFile"));
    }
}
