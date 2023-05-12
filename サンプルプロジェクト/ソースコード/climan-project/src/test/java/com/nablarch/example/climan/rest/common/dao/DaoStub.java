package com.nablarch.example.climan.rest.common.dao;

import nablarch.common.dao.DaoContext;
import nablarch.common.dao.EntityList;

import javax.persistence.OptimisticLockException;
import java.util.List;

/**
 * {@link DaoContext}のスタブ。
 *
 * @author Masaya Seko
 */
public class DaoStub implements DaoContext {
    @Override
    public <T> T findById(Class<T> entityClass, Object... id) {
        return null;
    }

    @Override
    public <T> T findByIdOrNull(Class<T> entityClass, Object... id) {
        return null;
    }

    @Override
    public <T> EntityList<T> findAll(Class<T> entityClass) {
        return null;
    }

    @Override
    public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId, Object params) {
        return null;
    }

    @Override
    public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId) {
        return null;
    }

    @Override
    public <T> T findBySqlFile(Class<T> entityClass, String sqlId, Object params) {
        return null;
    }

    @Override
    public <T> T findBySqlFileOrNull(Class<T> entityClass, String sqlId, Object params) {
        return null;
    }

    @Override
    public <T> long countBySqlFile(Class<T> entityClass, String sqlId, Object params) {
        return 0;
    }

    @Override
    public <T> int update(T entity) throws OptimisticLockException {
        return 0;
    }

    @Override
    public <T> void batchUpdate(List<T> entities) {

    }

    @Override
    public <T> void insert(T entity) {

    }

    @Override
    public <T> void batchInsert(List<T> entities) {

    }

    @Override
    public <T> int delete(T entity) {
        return 0;
    }

    @Override
    public <T> void batchDelete(List<T> entities) {

    }

    @Override
    public DaoContext page(long page) {
        return null;
    }

    @Override
    public DaoContext per(long per) {
        return null;
    }

    @Override
    public DaoContext defer() {
        return null;
    }
}
