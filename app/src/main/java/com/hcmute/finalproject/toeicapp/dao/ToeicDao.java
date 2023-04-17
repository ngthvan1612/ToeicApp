package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

public interface ToeicDao<T> {
    @Insert
    int[] insert(T... items);

    @Update
    void update(T... items);

    @Delete
    void delete(T... items);
}
