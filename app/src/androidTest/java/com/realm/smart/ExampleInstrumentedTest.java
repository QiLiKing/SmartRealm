package com.realm.smart;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smart.realm.IRealmFactory;
import com.smart.realm.SmartRealm;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.realm.smart", appContext.getPackageName());

        initRealm(appContext);

        testSingleWrite();
    }

    @Test
    public void testSingleWrite() {
        User user = generateTestUser();
        SmartRealm.insertOrUpdate(user);
        final User saved = SmartRealm.readBack(scope -> scope.query(User.class).equalTo("id", user.id).findFirst());
        assertEquals(user, saved);

        /* test write */
        user.name = "AAA";
        SmartRealm.write(scope -> {
            scope.insertOrUpdate(user);
            user.name = "BBB";
        });
        User check = SmartRealm.readBack(scope -> scope.query(User.class).equalTo("id", user.id).findFirst());
        assertEquals("AAA", check.name);

        user.name = "CCC";
        SmartRealm.write(scope -> {
            User tmp = scope.copyToRealmOrUpdate(user);
            user.name = "DDD";
            tmp.name = "EEE";
        });
        check = SmartRealm.readBack(scope -> scope.query(User.class).equalTo("id", user.id).findFirst());
        assertEquals("EEE", check.name);

        user.name = "FFF";
        SmartRealm.write(scope -> {
            User tmp = scope.query(User.class).equalTo("id", user.id).findFirst();
            if (tmp != null) {
                tmp.name = "GGG";
            }
        });
        check = SmartRealm.readBack(scope -> scope.query(User.class).equalTo("id", user.id).findFirst());
        assertEquals("GGG", check.name);

        /* test writeBack without self copy */
        user.name = "CCC";
        check = SmartRealm.writeBack(scope -> {
            User tmp = scope.copyToRealmOrUpdate(user);
            user.name = "DDD";
            tmp.name = "EEE";
            return tmp;
        });
        assertEquals("EEE", check.name);

        user.name = "FFF";
        check = SmartRealm.writeBack(scope -> {
            User tmp = scope.query(User.class).equalTo("id", user.id).findFirst();
            if (tmp != null) {
                tmp.name = "GGG";
            }
            return tmp;
        });
        assertEquals("GGG", check.name);

        /* test writeBack with self copy */
        user.name = "CCC";
        check = SmartRealm.writeBack(scope -> {
            User tmp = scope.copyToRealmOrUpdate(user);
            user.name = "DDD";
            tmp.name = "EEE";
            return scope.copyFromRealm(tmp);
        });
        assertEquals("EEE", check.name);

        user.name = "FFF";
        check = SmartRealm.writeBack(scope -> {
            User tmp = scope.query(User.class).equalTo("id", user.id).findFirst();
            if (tmp != null) {
                tmp.name = "GGG";
            }
            return scope.copyFromRealm(tmp);
        });
        assertEquals("GGG", check.name);
    }

    private void initRealm(Context context) {
        Realm.init(context);
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
