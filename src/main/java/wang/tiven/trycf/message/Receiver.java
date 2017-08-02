package wang.tiven.trycf.message;

import org.springframework.stereotype.Component;

@Component
public class Receiver {

	public void catchVillains(String villain) {
		try {
			// Assume hero catches the villain in 5 seconds
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Catched villain<" + villain + ">");
	}

}
