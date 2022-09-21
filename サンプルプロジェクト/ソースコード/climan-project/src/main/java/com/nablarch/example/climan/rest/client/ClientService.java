package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.common.dao.DaoFactory;
import com.nablarch.example.climan.common.exception.DuplicateRegistrationException;
import com.nablarch.example.climan.common.exception.SearchResultUpperLimitException;
import com.nablarch.example.climan.entity.Client;
import nablarch.common.dao.DaoContext;
import nablarch.core.repository.SystemRepository;

import java.util.List;

/**
 * 顧客管理を行うサービスクラス。
 */
public class ClientService {

    /** {@link DaoContext} */
    private final DaoContext daoContext;

    /**
     * サービスを生成する。
     */
    public ClientService() {
        this(DaoFactory.create());
    }

    /**
     * テスト用にサービスを生成する。
     * @param daoContext テスト用の{@link DaoContext}
     */
    ClientService(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    /**
     * 顧客を検索する。
     *
     * 検索条件には顧客名と業種コードを指定できる。顧客IDは指定されても使用しない。
     *
     * @param condition 検索条件
     * @return 検索結果。見つからない場合は空のリスト
     * @throws SearchResultUpperLimitException 検索結果が上限件数を超える場合
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
     * 顧客を取得する。
     *
     * @param clientId 顧客ID
     * @return 顧客
     * @throws nablarch.common.dao.NoDataException 顧客が存在しない場合
     */
    public Client findClientById(Integer clientId) {
        return daoContext.findById(Client.class, clientId);
    }

    /**
     * 顧客を登録する。
     *
     * @param client 登録する顧客
     * @return 登録した顧客
     * @throws DuplicateRegistrationException 既に登録されている顧客と同じ顧客名が指定された場合
     */
    public Client registerClient(Client client) {
        long count = daoContext.countBySqlFile(Client.class, "FIND_CLIENT_BY_CLIENT_NAME", client);
        if (count > 0) {
            throw new DuplicateRegistrationException();
        }
        daoContext.insert(client);
        List<Client> result = daoContext.findAllBySqlFile(Client.class, "FIND_CLIENT_BY_CLIENT_NAME", client);
        return result.get(0);
    }
}
