package com.smart.realm.core;

import com.smart.realm.IWriteScope;
import com.smart.realm.IRealmFactory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public class WriteScopeImpl extends ReadScopeImpl implements IWriteScope {
    public WriteScopeImpl(IRealmFactory factory) {
        super(factory);
    }

    @Override
    public void insertOrUpdate(RealmModel object) {
        if (object == null) {
            return;
        }
        Class<? extends RealmModel> clazz = object.getClass();
        Realm realm = open(clazz);
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(object);   //will cancel transaction in "close" method if error occurs
    }

    @Override
    public <E extends RealmModel> E copyToRealmOrUpdate(E object) {
        if (object == null) {
            return null;
        }
        Class<? extends RealmModel> clazz = object.getClass();
        Realm realm = open(clazz);
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        return realm.copyToRealmOrUpdate(object);   //will cancel transaction in "close" method if error occurs
    }

    @Override
    public void insertOrUpdate(List<? extends RealmModel> objects) {
        if (objects.isEmpty()) {
            return;
        }
        RealmModel first = objects.get(0);
        Class<? extends RealmModel> clazz = first.getClass();
        Realm realm = open(clazz);
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        realm.insertOrUpdate(objects);   //will cancel transaction in "close" method if error occurs
    }

    @Override
    public <E extends RealmModel> List<E> copyToRealmOrUpdate(List<E> objects) {
        if (objects.isEmpty()) {
            return new ArrayList<>(0);
        }
        RealmModel first = objects.get(0);
        Class<? extends RealmModel> clazz = first.getClass();
        Realm realm = open(clazz);
        if (!realm.isInTransaction()) {
            realm.beginTransaction();
        }
        return realm.copyToRealmOrUpdate(objects);   //will cancel transaction in "close" method if error occurs
    }

    @Override
    public void close() {
        for (Realm realm : openedRealms.values()) {
            try {
                realm.commitTransaction();
            } catch (Throwable t) {
                if (realm.isInTransaction()) {
                    realm.cancelTransaction();
                }
//                throw t;  fixme should throw exception? zhaoshuo
            }
        }
        super.close();
    }
}
