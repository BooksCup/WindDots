package com.wd.winddots.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DataTestBean.class}, version = 1,exportSchema = true)
public abstract  class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "cfpu_db";

    private static AppDatabase databaseInstance;

    public static synchronized AppDatabase getDatabaseInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    //为了防止数据库升级失败导致崩溃，加入该方法可以在出现异常时创建数据表而不崩溃，但表中数据会丢失
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseInstance;
    }

    public abstract DataTestDao dataTestDao();
}
