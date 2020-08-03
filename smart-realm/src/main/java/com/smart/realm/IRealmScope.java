package com.smart.realm;

import java.io.Closeable;

import io.realm.Realm;
import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public interface IRealmScope extends IAutoCloseableRealm {
    Realm open(Class<? extends RealmModel> table);
}
