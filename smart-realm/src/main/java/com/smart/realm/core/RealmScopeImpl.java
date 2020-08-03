package com.smart.realm.core;

import com.smart.realm.IRealmFactory;
import com.smart.realm.IRealmScope;

import java.util.Collection;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public class RealmScopeImpl implements IRealmScope {
    protected final HashMap<Integer, Realm> openedRealms = new HashMap<>();
    protected final IRealmFactory factory;

    public RealmScopeImpl(IRealmFactory factory) {
        this.factory = factory;
    }

    @Override
    public Realm open(Class<? extends RealmModel> table) {
        RealmConfiguration configuration = factory.getRealmConfiguration(table);
        final int hashCode = configuration.hashCode();
        Realm realm = openedRealms.get(hashCode);
        if (realm == null || realm.isClosed()) {
            realm = Realm.getInstance(configuration);
            openedRealms.put(hashCode, realm);
        }
        return realm;
    }

    @Override
    public void close() {
        if (openedRealms.size() > 0) {
            Collection<Realm> realms = openedRealms.values();
            for (Realm realm : realms) {
                realm.close();
            }
        }
    }
}
