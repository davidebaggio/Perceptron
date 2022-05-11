import java.util.Random;

/**
 * Perceptron
 */
public class Perceptron {

	public static final int HEIGHT = 20, WIDTH = 20, BIAS = 50, STEPS = 1000;

	private float[][] inputs, weights;

	public Perceptron() {
		this.inputs = new float[HEIGHT][WIDTH];
		this.weights = new float[HEIGHT][WIDTH];
	}

	public float feedForward() {
		float output = 0;
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				output += this.inputs[y][x] * this.weights[y][x];
			}
		}
		return output;
	}

	public void exite() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				this.weights[y][x] += this.inputs[y][x];
			}
		}
	}

	public void suppress() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				this.weights[y][x] -= this.inputs[y][x];
			}
		}
	}

	public Point[] randomRect() {
		Point[] rect = new Point[2];
		Random r = new Random();
		Point topLeft = new Point(r.nextInt(WIDTH), r.nextInt(HEIGHT));
		int w = r.nextInt(WIDTH - topLeft.x);
		int h = r.nextInt(HEIGHT - topLeft.y);
		Point botRight = new Point(topLeft.x + w, topLeft.y + h);
		rect[0] = topLeft;
		rect[1] = botRight;
		return rect;
	}

	public void fillRect(Point[] p, float value) {
		for (int y0 = p[0].y; y0 < p[1].y; y0++) {
			for (int x0 = p[0].x; x0 < p[1].x; x0++) {
				this.inputs[y0][x0] = value;
			}
		}
	}

	public Point randomCircle() {
		Random r = new Random();
		Point centre = new Point(r.nextInt(WIDTH / 2) + 5, r.nextInt(HEIGHT / 2) + 5);
		return centre;
	}

	public void fillCircle(Point p, int radius, float value) {
		for (int y0 = p.y - radius; y0 < p.y + radius; y0++) {
			for (int x0 = p.x - radius; x0 < p.x + radius; x0++) {
				int cx = x0 - p.x;
				int cy = y0 - p.y;
				if (cx * cx + cy * cy <= radius * radius)
					this.inputs[y0][x0] = value;
			}
		}
	}

	public void emptyInputs() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				this.inputs[y][x] = 0;
			}
		}
	}

	public static void main(String[] args) {

		Perceptron perc = new Perceptron();

		for (int k = 0; k < STEPS; k++) {
			perc.emptyInputs();

			perc.fillRect(perc.randomRect(), 1.0f);
			if (perc.feedForward() > BIAS)
				perc.suppress();

			/*
			 * for (int i = 0; i < HEIGHT; i++) {
			 * for (int j = 0; j < WIDTH; j++) {
			 * System.out.print(perc.inputs[i][j] + "\t");
			 * }
			 * System.out.println();
			 * }
			 */

			perc.emptyInputs();

			perc.fillCircle(perc.randomCircle(), 5, 1.0f);
			if (perc.feedForward() < BIAS)
				perc.exite();

			/*
			 * for (int i = 0; i < HEIGHT; i++) {
			 * for (int j = 0; j < WIDTH; j++) {
			 * System.out.print(perc.inputs[i][j] + "\t");
			 * }
			 * System.out.println();
			 * }
			 */

			System.out.print(perc.feedForward() + "\t");
		}
		System.out.println("\n\n");

		perc.emptyInputs();

		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				System.out.print(perc.weights[i][j] + "\t");
			}
			System.out.println();
		}

		// perc.fillRect(perc.randomRect(), 1.0f);
		perc.fillCircle(perc.randomCircle(), 5, 1.0f);
		float b = perc.feedForward();
		if (b < BIAS)
			System.out.println("E' un rettangolo con BIAS = " + b + " e BIAS di perceptron = " + BIAS);
		else
			System.out.println("E' un cerchio con BIAS = " + b + " e BIAS di perceptron = " + BIAS);

	}

}