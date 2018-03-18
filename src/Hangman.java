import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hangman implements KeyListener {
	static String randomWord;
	BufferedReader reader;
	String fromIntToIndex;
	static List<String> listOfWords = new ArrayList<String>();
	static int inVal;
	static Stack<String> stackOfRandomlyAssortedWords = new Stack<String>();

	char guessedWord;

	static int score = 10;

	JFrame frame;
	JPanel panel;
	JLabel label;
	JLabel scoreLabel;

	static int randomIndex;

	String newWord = "";
	static String underscores = "";

	static boolean completed;

	static boolean running = false;

	static String hiddenWord = "";

	String wordToSolve = "";

	public Hangman() {
		buildWordList();

		// testIndexSearcher(reader);
		frame = new JFrame();
		panel = new JPanel();
		scoreLabel = new JLabel("" + score);
		label = new JLabel();
		panel.add(label);
		panel.add(scoreLabel);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		frame.setVisible(true);
		frame.pack();
		running = true;
		// frame.setResizable(false);
		displayNextWord();
	}

	private void displayNextWord() {
		wordToSolve = stackOfRandomlyAssortedWords.pop();
		hiddenWord = "";
		for (int i = 0; i < wordToSolve.length(); i++) {
			hiddenWord += "_";
		}
		label.setText(hiddenWord);
	}

	private void buildWordList() {
		String in = JOptionPane.showInputDialog("Put a random number here:");
		inVal = Integer.parseInt(in);
		for (int i = 0; i < inVal; i++) {
			try {
				reader = new BufferedReader(new FileReader("src/dictionary.txt"));
				int randomNumber = new Random().nextInt(3002) + inVal;
				int e = 0;
				String line = reader.readLine();
				while (line != null) {

					while (e++ == randomNumber) {
						stackOfRandomlyAssortedWords.push(line);
						try {
							playSound("grunt.wav");
						} catch (UnsupportedAudioFileException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					}
					line = reader.readLine();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.out.println(stackOfRandomlyAssortedWords);
	}

	private void testIndexSearcher(BufferedReader reader) {

		String in = JOptionPane.showInputDialog("Put a random number here:");
		inVal = Integer.parseInt(in);
		for (int i = 0; i < inVal; i++) {
			try {
				listOfWords.add(reader.readLine());
				System.out.println(listOfWords.size());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		randomize();
	}

	public static void randomize() {
		List<String> assortedWords = new ArrayList<String>();
		for (int i = 0; i < inVal; i++) {
			System.out.println(randomIndex);
			if (listOfWords.size() != 0) {
				randomIndex = new Random().nextInt(listOfWords.size());
			}
			assortedWords.add(listOfWords.get(randomIndex));
			stackOfRandomlyAssortedWords.push(listOfWords.get(randomIndex));
			// System.out.println(stackOfRandomlyAssortedWords.peek());
			listOfWords.remove(randomIndex);
		}
	}

	public static void main(String[] args) {
		new Hangman();

	}

	private String wordToSolve() {
		hiddenWord = "";
		underscores = "";
		randomWord = stackOfRandomlyAssortedWords.pop();
		for (int i = 0; i < randomWord.length(); i++) {
			hiddenWord += "_";
			System.out.println(randomWord.length());
		}
		for (int i = 0; i < randomWord.length(); i++) {
			underscores += "_";
		}
		return hiddenWord;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean checkIfPlayerHasLost(int score) {
		if (score < 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void update() {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		char c = e.getKeyChar();
		String newString = "";
		if (score > 0) {
			checkWon();
			boolean wrong = true;
			for (int i = 0; i < wordToSolve.length(); i++) {
				if (c == wordToSolve.charAt(i)) {
					newString += wordToSolve.charAt(i);
					wrong = false;
					score++;
					
				} else {
					newString += hiddenWord.charAt(i);

					// score++;
				}
			}
			if (wrong == true) {
				score--;
				for (int i = 0; i < 1000; i++)  {
				try {
					playSound("grunt.wav");
				} catch (UnsupportedAudioFileException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
			}
			hiddenWord = newString;
			label.setText(hiddenWord);
			scoreLabel.setText("" + score);
		} else {
			try {
				playSound("lose.wav");
			} catch (UnsupportedAudioFileException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			label.setText("U L00SE SKRUB1!!!1");
			scoreLabel.setText("");
		}

	}

	public void checkWon() {
		if (hiddenWord.equals(wordToSolve)) {
			displayNextWord();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private synchronized void playSound(String fileName) throws UnsupportedAudioFileException, IOException {
		URL url = getClass().getResource(fileName);
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			clip.open(stream);
			clip.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}