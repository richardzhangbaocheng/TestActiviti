package serial;

import java.io.Serializable;

public class Person implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2618713648421935181L;
	private String name;
    private int age;
    public Person(){
        
    }
    public Person(String str, int n){
        System.out.println("Inside Person's Constructor");
        name = str;
        age = n;
    }
    String getName(){
        return name;
    }
    int getAge(){
        return age;
    }
}