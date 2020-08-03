package com.realm.smart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.smart.realm.IRealmFactory;
import com.smart.realm.SmartRealm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;

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

        SmartRealm.insertOrUpdate(generateTestUser());

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(generateTestUser());
        }
        Log.i(TAG, "to insert:" + users);
        SmartRealm.insertOrUpdate(users);

        SmartRealm.read(scope -> scope.where(User.class).findAll());

        User user = SmartRealm.readBack(scope -> {
            User u = scope.where(User.class).findFirst();
            return scope.copyFromRealm(u);
        });
        Log.i(TAG, "user:" + user);

        List<User> users1 = SmartRealm.readBack(scope -> {
            RealmResults<User> us = scope.where(User.class).sort("id").findAll();
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
