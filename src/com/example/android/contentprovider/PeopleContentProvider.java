package com.example.android.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class PeopleContentProvider extends ContentProvider {

	private static final String authority="com.example.android_study_contentprovider";
	private static final int Code_persion=1;
	private static final int Code_persions=2;
	
	private static final String PATH_SINGLE="person/#";
	private static final String PATH_MULTIPLE="person";
	
	private DBOpenHelper dbHelper;

	private static final String DB_TABLE="person";
	
	private static final UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
	static{
		//single
		uriMatcher.addURI(authority, PATH_SINGLE, Code_persion);
		//mutiple
		uriMatcher.addURI(authority, PATH_MULTIPLE, Code_persions);
		
	}
	
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper=new DBOpenHelper(getContext());
		if(dbHelper==null){
			return false;
		}
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase database;
		database=dbHelper.getReadableDatabase();
		
		SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
		qb.setTables(DB_TABLE);
		
		switch(uriMatcher.match(uri)){
		case Code_persion:
			//ID = uri.getPathSegments().get(1);
			long id=ContentUris.parseId(uri);
				Log.d("", "--PersonContentProvider query() id="+id);
			qb.appendWhere(Person.KEY_ID+"="+id);			
			break;
		case Code_persions:
			qb.buildQuery(projection, selection, null, null, sortOrder, null);
			break;
		}
		
		Cursor c=qb.query(database,projection,selection,selectionArgs,null,null,sortOrder);
		c.setNotificationUri(getContext().getContentResolver(),uri);	
		return c;
	}

	
	private final String MIME_DIR_PREFIX="vnd.android.cursor.dir";
	private final String MIME_ITEM_PREFIX="vnd.android.cursor.item";
	private final String MIME_ITEM="vnd.example.person";
	
	private final String CONTENT_URI_STRING="content://"+authority+"/"+PATH_MULTIPLE;
	private final Uri CONTENT_URI=Uri.parse(CONTENT_URI_STRING);
	
	@Override
	public String getType(Uri uri) {
		switch(uriMatcher.match(uri)){
		case Code_persion:
			return MIME_ITEM_PREFIX+"/"+MIME_ITEM;			
		case Code_persions:
			return MIME_DIR_PREFIX+"/"+MIME_ITEM;
		default:
			throw new IllegalArgumentException("unkown uri :"+uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db =dbHelper.getWritableDatabase();
		long id =db.insert(DB_TABLE, null, values);
		if(id>0){
			Uri newUri=ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(newUri, null);
			db.close();
			return newUri;			
		}
		db.close();
		throw new SQLException("failed to insert row info "+uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
