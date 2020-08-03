package com.smart.realm.core;

import com.smart.realm.IReadScope;
import com.smart.realm.IRealmFactory;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmQuery;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
@SuppressWarnings("unchecked")
public class ReadScopeImpl extends RealmScopeImpl implements IReadScope {
    public ReadScopeImpl(IRealmFactory factory) {
        super(factory);
    }

    @Override
    public <E extends RealmModel> RealmQuery<E> query(Class<E> table) {
        return open(table).where(table);
    }

    @Override
    public <E extends RealmModel> E copyFromRealm(E realmObject) {
        if (realmObject == null) {
            return null;
        }
        Class<E> clazz = (Class<E>) realmObject.getClass();
        return open(clazz).copyFromRealm(realmObject, factory.getCopyDeep(clazz));
    }

    @Override
    public <E extends RealmModel> E copyFromRealm(E realmObject, int maxDepth) {
        if (realmObject == null) {
            return null;
        }
        Class<E> clazz = (Class<E>) realmObject.getClass();
        return open(clazz).copyFromRealm(realmObject, maxDepth);
    }

    @Override
    public <E extends RealmModel> List<E> copyFromRealm(List<E> realmObjects) {
        if (realmObjects.isEmpty()) {
            return new ArrayList<>(0);
        }
        E e = realmObjects.get(0);
        Class<E> clazz = (Class<E>) e.getClass();
        return open(clazz).copyFromRealm(realmObjects, factory.getCopyDeep(clazz));
    }

    @Override
    public <E extends RealmModel> List<E> copyFromRealm(List<E> realmObjects, int maxDepth) {
        if (realmObjects.isEmpty()) {
            return new ArrayList<>(0);
        }
        E e = realmObjects.get(0);
        Class<E> clazz = (Class<E>) e.getClass();
        return open(clazz).copyFromRealm(realmObjects, maxDepth);
    }
}
