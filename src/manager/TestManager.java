package manager;

import java.io.Serializable;

public class TestManager implements Serializable {
	private static final long serialVersionUID = 1L;
	public void add(int a, int b){
		System.out.println("A PLUS B equals: " + (a + b));
	}
}
