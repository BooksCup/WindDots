package com.wd.winddots.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataTestDao {
    @Query("SELECT * FROM datatestbean")
    List<DataTestBean> getAll();

    @Query("SELECT * FROM datatestbean WHERE testId IN (:userIds)")
    List<DataTestBean> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM datatestbean WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    DataTestBean findByName(String first, String last);

    @Insert
    void insertAll(DataTestBean... dataTestBeans);

    @Delete
    void delete(DataTestBean dataTestBean);
}
