import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SolitaireEncryption {

	public static void main(String[] args) throws IOException {
		try {
			// IO Method
			FileReader fr = new FileReader(args[1]);
			BufferedReader br = new BufferedReader(fr);
			StringTokenizer st = new StringTokenizer(br.readLine());

			Solitaire ss = new Solitaire();
			Solitaire deck = new Solitaire();//Storage of Deck
			//The message of user's inputed
			StringBuffer temp = new StringBuffer(args[2]);

			for (int i = 0; i < temp.length(); i++) { 
				// Except Special word and Number
				if (temp.charAt(i) == ' ' || temp.charAt(i) == '@'
						|| temp.charAt(i) == '!' || temp.charAt(i) == '#'
						|| temp.charAt(i) == '$' || temp.charAt(i) == '%'
						|| temp.charAt(i) == '^' || temp.charAt(i) == '&'
						|| temp.charAt(i) == '*' || temp.charAt(i) == '('
						|| temp.charAt(i) == ')' || temp.charAt(i) == '-'
						|| temp.charAt(i) == '=' || temp.charAt(i) == '\\'
						|| temp.charAt(i) == '/' || temp.charAt(i) == '['
						|| temp.charAt(i) == ']' || temp.charAt(i) == '{'
						|| temp.charAt(i) == '}' || temp.charAt(i) == '<'
						|| temp.charAt(i) == '>' || temp.charAt(i) == '?'
						|| temp.charAt(i) == '.' || temp.charAt(i) == ','
						|| temp.charAt(i) == '~' || temp.charAt(i) == '+'
						|| temp.charAt(i) == '_' || temp.charAt(i) == ':'
						|| temp.charAt(i) == ';' || temp.charAt(i) == '\''
						|| temp.charAt(i) == '1' || temp.charAt(i) == '2'
						|| temp.charAt(i) == '3' || temp.charAt(i) == '4'
						|| temp.charAt(i) == '5' || temp.charAt(i) == '6'
						|| temp.charAt(i) == '7' || temp.charAt(i) == '8'
						|| temp.charAt(i) == '9' || temp.charAt(i) == '0') {
					temp.deleteCharAt(i);
					i--;
				}
			}
			String argsTemp = temp.toString();
			char message[] = new char[args[2].length()];
			message = argsTemp.toUpperCase().toCharArray();
			int length = message.length;

			ss.makeDeck(st);// Make Deck

			// We got 3 services : Print keystreams, do encryption and
			// decryption
			if (args[0].equals("keygen")) {
				// Print the key after move Joker A and B,
				// triple cut and count cut
				// then get all of key
				for (int i = 0; i <= length - 1; i++) {
					ss.jokerA();// Move joker A
					System.out.println("S1 :" + ss);
					ss.jokerB();// Move joker B
					System.out.println("S2 :" + ss);
					ss.tripleCut();// Move triple cut
					System.out.println("S3 :" + ss);
					ss.countCut();// Move count cut
					System.out.println("S4 :" + ss);
					// if get the Joker key, the key will be skipped
					if (ss.gotTheKey() == 28 || ss.gotTheKey() == 27) {
						// Excepted Joker
						System.out.println("KEY " + (i + 1) + ": "
								+ "Key Skipped");
						length++;
						continue;
					} else {
						System.out.println("KEY " + (i + 1) + ": "
								+ ss.gotTheKey());
					}
					// Put all of keygen into Linked list (Deck)
					deck.addToTail(ss.gotTheKey());
				}
				// Print keystream values
				System.out.println("Keystream values: " + deck);

			} else if (args[0].equals("en")) {
				// Do encryption : after input "en" , the deck will be
				// move joker A and B , triple cut and count cut
				// finally, put the deck and messages into encryption method
				for (int i = 0; i <= length; i++) {
					ss.jokerA();// Move joker A
					ss.jokerB();// Move joker B
					ss.tripleCut();// Move triple cut
					ss.countCut();// Move count cut
					if (ss.gotTheKey() == 28 || ss.gotTheKey() == 27) {
						// Excepted Joker
						length++;
						continue;
					} else {
						// Put all of keygen into Linked list (Deck)
						deck.addToTail(ss.gotTheKey());
					}
				}
				// Do encryption
				ss.encryption(deck, message);
			} else if (args[0].equals("de")) {
				// Do decryption : after input "de", the deck will be move
				// Joker A and B , triple cut and count cut, finally, put the
				// deck
				// and messages into decryption method
				for (int i = 0; i <= length; i++) {
					ss.jokerA();// Move joker A
					ss.jokerB();// Move joker B
					ss.tripleCut();// Move triple cut
					ss.countCut();// Move count cut
					if (ss.gotTheKey() == 28 || ss.gotTheKey() == 27) {
						// Excepted Joker
						length++;
						continue;
					} else {
						// Put all of keygen into Linked list (Deck)
						deck.addToTail(ss.gotTheKey());
					}
				}
				// Do decryption
				ss.decryption(deck, message);
			} else {
				// Exclude 3 services outside
				System.out.println("Error : " + args[0] + " is not valid");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Array index out of bounds
			e.printStackTrace();
		} catch (Exception e) {
			// Other Exceptions
			System.out.println(e);
		}
	}
}
