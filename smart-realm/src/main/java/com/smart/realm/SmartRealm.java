package com.smart.realm;

import com.smart.realm.core.ReadScopeImpl;
import com.smart.realm.core.RealmScopeImpl;
import com.smart.realm.core.WriteScopeImpl;

import java.util.List;

import io.realm.RealmModel;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
public final class SmartRealm {
    private static IRealmFactory mRealmFactory;

    public static void init(IRealmFactory realmFactory) {
        mRealmFactory = realmFactory;
    }

    public static IRealmScope realmScope() {
        return new RealmScopeImpl(mRealmFactory);
    }

    public static IReadScope readScope() {
        return new ReadScopeImpl(mRealmFactory);
    }

    public static IWriteScope writeScope() {
        return new WriteScopeImpl(mRealmFactory);
    }

    public static void read(IVoidExecutable<IReadScope> executable) {
        try (IReadScope readScope = readScope()) {
            executable.execute(readScope);
        }
    }

    /**
     * @return MUST copyFromRealm by yourself if it contains managed object
     */
    public static <R> R readBack(IValueExecutable<IReadScope, R> executable) {
        try (IReadScope readScope = readScope()) {
            return executable.execute(readScope);
        }
    }

    public static void write(IVoidExecutable<IWriteScope> executable) {
        try (IWriteScope readWriteScope = writeScope()) {
            executable.execute(readWriteScope);
        }
    }

    /**
     * @return MUST copyFromRealm by yourself if it contains managed object
     */
    public static <R> R writeBack(IValueExecutable<IWriteScope, R> executable) {
        try (IWriteScope readWriteScope = writeScope()) {
            return executable.execute(readWriteScope);
        }
    }

    public static void insertOrUpdate(RealmModel object) {
        write(scope -> scope.insertOrUpdate(object));
    }

    public static void insertOrUpdate(List<? extends RealmModel> objects) {
        write(scope -> scope.insertOrUpdate(objects));
    }
}
