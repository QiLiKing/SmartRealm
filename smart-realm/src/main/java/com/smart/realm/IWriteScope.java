package com.smart.realm;

import java.util.List;

import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public interface IWriteScope extends IReadScope {
    /**
     * faster than {@link IWriteScope#copyToRealmOrUpdate(RealmModel)}
     */
    void insertOrUpdate(RealmModel object);

    /**
     * @return a MANAGED RealmObject with its properties backed by the Realm.
     */
    <E extends RealmModel> E copyToRealmOrUpdate(E object);

    /**
     * faster than {@link IWriteScope#copyToRealmOrUpdate(List)}
     */
    void insertOrUpdate(List<? extends RealmModel> objects);

    /**
     * @return a list of the converted RealmObjects that all has their properties MANAGED by the Realm.
     */
    <E extends RealmModel> List<E> copyToRealmOrUpdate(List<E> objects);
}
