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

import java.util.*;

import static nablarch.test.Assertion.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ClientServiceのテストクラス。
 */
class ClientServiceTest {

    @BeforeAll
    static void setUp() {
        ConfigLoader.load();
    }

    /**
     * 顧客検索で検索結果が上限件数を超えない場合のテスト。
     *
     * 検索結果が返されること。
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
     * 顧客検索で検索結果が上限件数を超える場合のテスト。
     *
     * {@link SearchResultUpperLimitException}が送出され、上限件数が設定されていること。
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
     * 顧客詳細取得で検索結果が１件存在する場合のテスト。
     *
     * 検索結果が返されること。
     */
    @Test
    void testFindClientById() {
        final Map<String, Boolean> invoked = new HashMap<String, Boolean>();
        Client client = new Client();
        client.setClientId(1);
        client.setClientName("顧客１");
        client.setIndustryCode("01");
        ClientService sut = new ClientService(new DaoStub() {
            @SuppressWarnings({ "unchecked" })
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                invoked.put("findById", true);
                return (T)  client;
            }

            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return Long.valueOf(SystemRepository.get("app.common.search.limit"));
            }
        });

        Client result = sut.findClientById(1);
        assertTrue(invoked.get("findById"));
        assertEquals(client, result);
    }

    /**
     * 顧客詳細取得で検索結果存在しない場合のテスト。
     *
     * {@link NoDataException}が送出されること。
     */
    @Test
    void testFindClientByIdForNoDataException() {
        final Map<String, Boolean> invoked = new HashMap<String, Boolean>();
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                invoked.put("findById", true);
                throw new NoDataException();
            }
        });

        try {
            sut.findClientById(0);
            fail();
        } catch (NoDataException e) {
            assertTrue(invoked.get("findById"));
        }
    }

    /**
     * 顧客登録で既に登録されているデータと重複しない場合のテスト。
     *
     * 登録されること。
     */
    @Test
    void testRegisterClient() {

        final Map<String, Boolean> invoked = new HashMap<>();
        final Client client = new Client();
        client.setClientId(1);
        client.setClientName("顧客１");
        client.setIndustryCode("01");

        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return 0;
            }

            @Override
            public <T> void insert(T entity) {
                invoked.put("insert", true);
            }

            @Override
            public <T> T findBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return (T) client;
            }
        });

        Client result = sut.registerClient(new Client());

        assertTrue(invoked.containsKey("insert"));
        assertEquals(client, result);
    }

    /**
     * 顧客登録で既に登録されているデータと重複する場合のテスト。
     *
     * {@link DuplicateRegistrationException}が送出される。
     */
    @Test
    void testRegisterClientForDuplicateRegistrationException() {
        ClientService sut = new ClientService(new DaoStub() {
            @Override
            public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return 1;
            }
        });

        Assertions.assertThrows(DuplicateRegistrationException.class, () -> sut.registerClient(new Client()));
    }
}
