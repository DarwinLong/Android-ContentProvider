package com.example.android.contentprovider;

public class Person {
	private Integer _id;  
    private String name;  
    private String age;  
    
    public static String KEY_ID="_id";
      
    public Integer get_id() {  
        return _id;  
    }  
    public void set_id(Integer _id) {  
        this._id = _id;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getAge() {  
        return age;  
    }  
    public void setAge(String age) {  
        this.age = age;  
    }  
}
