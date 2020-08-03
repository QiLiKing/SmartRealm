package com.smart.realm;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
interface IAutoCloseableRealm extends AutoCloseable {
    void close();
}
