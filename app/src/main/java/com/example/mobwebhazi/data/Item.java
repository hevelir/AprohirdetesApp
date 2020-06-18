package com.example.mobwebhazi.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
@Entity(tableName = "item")
public class Item {
        public enum Category {
            INGATLAN, JÁRMŰ, HÁZTARTÁS, ELEKTRONIKA, SPORT, RUHÁZAT, ÜZLET;

            @TypeConverter
            public static Category getByOrdinal(int ordinal) {
                Category ret = null;
                for (Category cat : Category.values()) {
                    if (cat.ordinal() == ordinal) {
                        ret = cat;
                        break;
                    }
                }
                return ret;
            }

            @TypeConverter
            public static int toInt(Category category) {
                return category.ordinal();
            }
        }

        @ColumnInfo(name = "userFK")
        public String user;

        @ColumnInfo(name = "id")
        @PrimaryKey(autoGenerate = true)
        public Long id;

        @ColumnInfo(name = "name")
        public String name;

        @ColumnInfo(name = "description")
        public String description;

        @ColumnInfo(name = "category")
        public Category category;

        @ColumnInfo(name="price")
        public int price;

        @ColumnInfo(name = "is_bought")
        public boolean isBought;

}
