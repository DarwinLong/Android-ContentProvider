package com.example.android.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {  
    
  public static final String DATABASE_NAME = "person.db"; //数据库名称  
  private static final int DATABASE_VERSION = 1;//数据库版本  
    
  public DBOpenHelper(Context context) {  
      super(context, DATABASE_NAME, null, DATABASE_VERSION);  
      // TODO Auto-generated constructor stub  
  }  

  @Override  
  public void onCreate(SQLiteDatabase db) {  
          
      db.execSQL("CREATE TABLE person (_id integer primary key autoincrement, name varchar(20), age varchar(10))");  
  }  

  @Override  
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  
      // TODO Auto-generated method stub  
      db.execSQL("DROP TABLE IF EXISTS person");  
      onCreate(db);  
  }  

}  