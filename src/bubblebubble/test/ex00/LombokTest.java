package bubblebubble.test.ex00;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Dog {
	private String name;
	
}

public class LombokTest {
	public static void main(String[] args) {
		Dog d = new Dog();
		System.out.println(d.getName());
	}
}
