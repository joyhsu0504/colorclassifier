// 1  -> red
// -1 -> blue
//features -> RGB
import java.util.*;
public class ColorClassifier {
	public static void main(String[] args) {
		Feature f1 = new Feature(255, 0, 0);
		Feature f2 = new Feature(243, 88, 88);
		Feature f3 = new Feature(183, 25, 25);
		Feature f4 = new Feature(25, 52, 183);
		Feature f5 = new Feature(10, 49, 247);
		Feature f6 = new Feature(0, 0, 255);
		Feature[] data= {f1, f2, f3, f4, f5, f6};
		double[] labels = {1, 1, 1, -1, -1, -1};
		double a = 1;
		double b = -2;
		double c = -1;
		double d = 2;
		for(int iter = 0; iter <= 1000; iter++) {
			int i = (int)Math.floor(Math.random() * data.length);
			double x = data[i].first;
			double y = data[i].second;
			double z = data[i].third;
			double label = labels[i];
			double score = a*x + b*y + c*z + d;
			double pull = 0.0;
			if(iter % 25 == 0) {
				System.out.println("Iteration: " + iter + "\t Training accuracy: " + eval(data, labels, a, b, c, d));
				if(eval(data, labels, a, b, c, d) == 1.0) {
					System.out.println("a : " + a + " b: " + b + " c: " + c + " d: " + d);
					break;
				}
			}
			if(label == 1 && score < 1) {
				pull = 1;
			}
			if(label == -1 && score > -1) {
				pull = -1;
			}
		    double step_size = 0.01;
			a += step_size * (x * pull - a); // -a is from the regularization
			b += step_size * (y * pull - b); // -b is from the regularization
			c += step_size * (z * pull - c); // -b is from the regularization
			d += step_size * (1 * pull);
		}
		Scanner sc = new Scanner(System.in);
		System.out.print("R: ");
		double red = (double)sc.nextInt();
		System.out.print("G: ");
		double green = (double)sc.nextInt();
		System.out.print("B: ");
		double blue = (double)sc.nextInt();
		double temp = a*red + b*green + c*blue + d;
		if(temp > 0) {
			System.out.println("Red detected");
		} else {
			System.out.println("Blue detected");
		}
		
	}
	public static double eval(Feature[] data, double[] labels, double a, double b, double c, double d) {
		double num_correct = 0;
		for(int i = 0; i < data.length; i++) {
			double x = data[i].first;
			double y = data[i].second;
			double z = data[i].third;
			double true_label = labels[i];
			double predicted_label = (a*x + b*y + c*z + d) > 0 ? 1 : -1;
			if(predicted_label == true_label) {
				num_correct++;
		    }
		}
		return num_correct/data.length;
	}
}

class Feature {
	double first;
	double second;
	double third;
	public Feature(double one, double two, double three) {
		this.first = one;
		this.second = two;
		this.third = three;
	}
}