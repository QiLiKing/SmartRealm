package com.smart.realm;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmQuery;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public interface IReadScope extends IAutoCloseableRealm {
    /**
     * start a query transaction
     */
    <E extends RealmModel> RealmQuery<E> query(Class<E> table);

    /**
     * use default depth that you configured through {@link SmartRealm#init(IRealmFactory)}
     */
    <E extends RealmModel> E copyFromRealm(E realmObject);

    <E extends RealmModel> E copyFromRealm(E realmObject, int maxDepth);

    /**
     * use default depth that you configured through {@link SmartRealm#init(IRealmFactory)}
     */
    <E extends RealmModel> List<E> copyFromRealm(List<E> realmObjects);

    <E extends RealmModel> List<E> copyFromRealm(List<E> realmObjects, int maxDepth);
}
