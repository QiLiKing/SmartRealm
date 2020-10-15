package com.smart.realm.core;

import androidx.annotation.CallSuper;

import com.smart.realm.IRealmFactory;
import com.smart.realm.IRealmScope;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public class RealmScopeImpl implements IRealmScope {
    final HashMap<Integer, Realm> openedRealms = new HashMap<>();
    final IRealmFactory factory;

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

    @CallSuper
    @Override
    public void close() {
        for (Realm realm : openedRealms.values()) {
            realm.close();
        }
    }
}
