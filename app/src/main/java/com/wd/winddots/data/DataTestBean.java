package com.wd.winddots.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "datatestbean")
public class DataTestBean {

    @PrimaryKey
    @NonNull
    public String testId;

    @ColumnInfo(name = "first_name")
    public String testFirstName;

    @ColumnInfo(name = "last_name")
    public String testLastName;
}
