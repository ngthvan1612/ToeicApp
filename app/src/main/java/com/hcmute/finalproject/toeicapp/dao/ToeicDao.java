package com.hcmute.finalproject.toeicapp.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface ToeicDao<T> {
    @Insert
    List<Long> insert(T... items);

    @Update
    void update(T... items);

    @Delete
    void delete(T... items);
}
