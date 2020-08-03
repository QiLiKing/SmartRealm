package com.smart.realm.core;

import androidx.annotation.Nullable;

import com.smart.realm.IReadScope;
import com.smart.realm.IRealmFactory;

import java.util.ArrayList;
import java.util.Iterator;
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
    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> realmObjects) {
        Class<E> clazz = findClass(realmObjects);
        if (clazz == null) {
            return new ArrayList<>(0);
        } else {
            return open(clazz).copyFromRealm(realmObjects, factory.getCopyDeep(clazz));
        }
    }

    @Override
    public <E extends RealmModel> List<E> copyFromRealm(Iterable<E> realmObjects, int maxDepth) {
        Class<E> clazz = findClass(realmObjects);
        if (clazz == null) {
            return new ArrayList<>(0);
        } else {
            return open(clazz).copyFromRealm(realmObjects, maxDepth);
        }
    }

    /**
     * @return null - objects is empty
     */
    @Nullable
    private static <E extends RealmModel> Class<E> findClass(Iterable<E> objects) {
        if (objects instanceof List) {
            List<E> list = (List<E>) objects;
            if (list.size() > 0) {
                E first = list.get(0);
                return (Class<E>) first.getClass();
            }
        } else {
            Iterator<E> iterator = objects.iterator();
            if (iterator.hasNext()) {
                E first = iterator.next();
                return (Class<E>) first.getClass();
            }
        }
        return null;
    }
}
