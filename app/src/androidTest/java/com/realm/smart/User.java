package com.realm.smart;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return sex == user.sex &&
            age == user.age &&
            Objects.equals(id, user.id) &&
            Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sex, age);
    }

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
