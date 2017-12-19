package com.example.android_study_contentprovider;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

public class MainActivity extends Activity {
 	
	Uri CONTENT_URI=Uri.parse("content://com.example.android_study_contentprovider"+"/"+"person");
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int count=1;
    public void insert(View btn){
    	ContentResolver cr=getContentResolver();
    	ContentValues values=new ContentValues();
    	
    	values.put("name", "test_name01"+(count++));
    	values.put("age", 27+count++);    	
    	cr.insert(CONTENT_URI, values);
 
    	Toast.makeText(MainActivity.this, "Insert 完成！", 0).show();
    }
    
    public void query(View btn){
    	ContentResolver cr=this.getContentResolver();
    	Cursor c=cr.query(CONTENT_URI, null, null, null, null);
    	String result="";
    	if(c.moveToFirst()){
    		do{
    			String name =c.getString(c.getColumnIndex("name"));
    			String age= c.getString(c.getColumnIndex("age"));
    			result+="name:"+name+", age="+age+"\n";    			
    		}while(c.moveToNext());
    		
    	}else{
    		Toast.makeText(MainActivity.this, "表中无数据！", 0).show();
    	}
    	c.close();
    	
    	
    	Toast.makeText(MainActivity.this, "查询结果："+result, 0).show();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
