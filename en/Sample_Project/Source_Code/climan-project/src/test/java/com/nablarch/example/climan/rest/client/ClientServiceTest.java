package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import com.nablarch.example.climan.entity.Client;
import com.nablarch.example.climan.rest.common.dao.DaoStub;
import com.nablarch.example.climan.rest.common.repository.ConfigLoader;
import nablarch.common.dao.EntityList;
import nablarch.core.repository.SystemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static nablarch.test.Assertion.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
     * Test executed when the search results in a client search exceed the maximum number.
     *
     * {@link SearchResultUpperLimitException} must be sent and a maximum number must be set.
     */
    @Test
    void testFindClientForSearchResultUpperLimitError() {

        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return Long.valueOf(SystemRepository.get("app.common.search.limit")) + 1;
            }
        });

        try {
            sut.findClient(new Client());
            fail();
        } catch (SearchResultUpperLimitException e) {
            long expected = Long.valueOf(SystemRepository.get("app.common.search.limit"));
            assertEquals(expected, e.getLimit());
        }
    }

    /**
     * Test executed when the search results in a client search do not exceed the maximum number.
     *
     * Search results are returned.
     */
    @Test
    void testFindClient() {

        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return new EntityList(Arrays.asList(new Client(), new Client()));
            }

            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return Long.valueOf(SystemRepository.get("app.common.search.limit"));
            }
        });

        List<Client> result = sut.findClient(new Client());
        assertEquals(2, result.size());
    }

    /**
     * Test executed when data has already been registered in the client registration process.
     *
     * {@link DuplicateRegistrationException} is sent.
     */
    @Test
    void testRegisterClientForDuplicateRegistrationException() {
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return 1;
            }
        });

        assertThrows(DuplicateRegistrationException.class, () -> sut.registerClient(new Client()));
    }

    /**
     * Test executed when data has not already been registered in the client registration process.
     *
     * Must be registered.
     */
    @Test
    void testRegisterClient() {

        final Map<String, Boolean> invoked = new HashMap<>();

        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return 0;
            }

            @Override
            public <T> void insert(T entity) {
                invoked.put("insert", true);
            }
        });

        sut.registerClient(new Client());

        assertTrue(invoked.containsKey("insert"));
    }
}
