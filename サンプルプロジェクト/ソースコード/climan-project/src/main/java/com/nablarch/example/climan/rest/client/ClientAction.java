package com.nablarch.example.climan.rest.client;

import com.nablarch.example.climan.entity.Client;
import nablarch.core.beans.BeanUtil;
import nablarch.core.validation.ee.ValidatorUtil;
import nablarch.fw.ExecutionContext;
import nablarch.fw.web.HttpRequest;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * 顧客管理を行うアクションクラス。
 *
 * @author Masaya Seko
 */
@Path("/client")
public class ClientAction {

    /**
     * 顧客を検索する。
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return 検索結果
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> list(HttpRequest request, ExecutionContext context) {

        ClientSearchForm form = BeanUtil.createAndCopy(ClientSearchForm.class, request.getParamMap());
        ValidatorUtil.validate(form);

        ClientService service = new ClientService();
        Client condition = BeanUtil.createAndCopy(Client.class, form);
        return service.findClient(condition);
    }

    /**
     * 顧客詳細を取得する。
     * @param request HTTPリクエスト
     * @param context 実行コンテキスト
     * @return 顧客詳細
     */
    @GET
    @Path("/{clientId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client show(HttpRequest request, ExecutionContext context) {

        ClientGetForm form = BeanUtil.createAndCopy(ClientGetForm.class, request.getParamMap());
        ValidatorUtil.validate(form);

        ClientService service = new ClientService();
        Client condition = BeanUtil.createAndCopy(Client.class, form);
        return service.findClientById(condition.getClientId());
    }

    /**
     * 顧客を登録する。
     *
     * @param form 顧客登録のフォーム
     * @return 登録した顧客詳細
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
