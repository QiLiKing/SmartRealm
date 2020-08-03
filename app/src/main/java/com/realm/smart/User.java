package com.realm.smart;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * QQ:1055329812
 * Created by QiLiKing on 2020/8/3.
 */
@RealmClass
public class User implements RealmModel {
    @PrimaryKey
    public String id;
    public String name;
    public int sex;
    public int age;

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", sex=" + sex +
            ", age=" + age +
            '}';
    }
}
