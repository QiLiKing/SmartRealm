package com.smart.realm;

import io.realm.RealmConfiguration;
import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public interface IRealmFactory {
    RealmConfiguration getRealmConfiguration(Class<? extends RealmModel> table);

    int getCopyDeep(Class<? extends RealmModel> table);
}
