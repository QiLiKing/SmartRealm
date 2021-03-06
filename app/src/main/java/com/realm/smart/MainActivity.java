package com.realm.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.smart.realm.IRealmFactory;
import com.smart.realm.SmartRealm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext());
        final RealmConfiguration configuration = new RealmConfiguration.Builder()
            .name("Test.realm")
            .schemaVersion(1)
            .build();
        SmartRealm.init(new IRealmFactory() {
            @Override
            public RealmConfiguration getRealmConfiguration(Class<? extends RealmModel> table) {
                return configuration;
            }

            @Override
            public int getCopyDeep(Class<? extends RealmModel> table) {
                return 0;
            }
        });

//        Realm realm = SmartRealm.realmScope().open(User.class);
//        realm.beginTransaction();
//        realm.commitTransaction();
//        realm.close();

        SmartRealm.insertOrUpdate(generateTestUser());

        SmartRealm.insertOrUpdateAsync(generateTestUser());

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(generateTestUser());
        }
        Log.i(TAG, "to insert:" + users);
        SmartRealm.insertOrUpdate(users);

        SmartRealm.insertOrUpdateAsync(users);

        SmartRealm.read(scope -> scope.query(User.class).findAll());

        User user = SmartRealm.readBack(scope -> {
            User u = scope.query(User.class).findFirst();
            return scope.copyFromRealm(u);
        });
        Log.i(TAG, "user:" + user);

        List<User> users1 = SmartRealm.readBack(scope -> {
            RealmResults<User> us = scope.query(User.class).sort("id").findAll();
            return scope.copyFromRealm(us);
        });
        for (User u : users1) {
            Log.i(TAG, "users:" + u);
        }

        SmartRealm.write(scope -> {
            User u = scope.query(User.class).findFirst();
            if (u != null) {
                Log.i(TAG, u.id + " change to name2");
                u.name = "name2";
            }

            User unmanaged = scope.copyFromRealm(u);
            unmanaged.id = "aaaaaaa";
            User managed = scope.copyToRealmOrUpdate(unmanaged);
            managed.name = "ddddddd";
            unmanaged.name = "ccccccc";
        });

        SmartRealm.writeAsync(scope -> {
            User u = scope.query(User.class).equalTo("name", "name2").findFirst();
            if (u != null) {
                Log.i(TAG, u.id + " change name2 to name3");
                u.name = "name3";
            }

            for (int i = 0; i < 5; i++) {
                scope.insertOrUpdate(generateTestUser());
            }
        });

        users1 = SmartRealm.readBack(scope -> {
            RealmResults<User> us = scope.query(User.class).sort("id").findAll();
            return scope.copyFromRealm(us);
        });
        for (User u : users1) {
            Log.i(TAG, "users:" + u);
        }
    }

    private User generateTestUser() {
        Random random = new Random();
        User testUser = new User();
        testUser.id = "key" + random.nextInt(10);
        testUser.name = "name" + random.nextInt(100);
        testUser.sex = random.nextInt(2);
        testUser.age = random.nextInt(100);
        return testUser;
    }
}
