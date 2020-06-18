package com.example.mobwebhazi.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item WHERE item.userFK != :username")
    List<Item> getOthers(String username);

    @Query("SELECT * FROM item WHERE item.userFK == :uname")
    List<Item> getUserItems(String uname);

    @Query("DELETE FROM item")
    void deleteAll();

    @Query("SELECT * FROM item WHERE item.userFK != :username ORDER BY price ASC")
    List<Item> getOthersAscending(String username);

    @Query("SELECT * FROM item WHERE item.userFK != :username ORDER BY price DESC")
    List<Item> getOthersDescending(String username);

    @Query("SELECT * FROM item WHERE item.userFK == :username ORDER BY price ASC")
    List<Item> getUserItemsAscending(String username);

    @Query("SELECT * FROM item WHERE item.userFK == :username ORDER BY price DESC")
    List<Item> getUserItemsDescending(String username);

    @Insert
    long insert(Item Items);

    @Update
    void update(Item Item);

    @Delete
    void deleteItem(Item Item);
}
