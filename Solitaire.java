import java.util.NoSuchElementException;
import java.util.StringTokenizer;

class OverListException extends Exception { // Exception : the deck is
  // over the range (over 28)
  public OverListException() {
    super("Deck is Full ( Over 28 )");
  }
}

class UnderListException extends Exception { // Exception : the deck is
  // under the range (under 28)
  public UnderListException() {
    super("Deck is under 28");
  }
}

class JokerException extends Exception { // Exception : Except over two joker or
  // less than one joker
  public JokerException(String errorMsg1) {
    super(errorMsg1);
  }
}

public class Solitaire {

  private CardNode head, tail;

  public Solitaire() {
    // Default setting
    head = tail = null;
  }

  public boolean isEmpty() {
    // Define what is the empty
    return head == null;
  }

  public void makeDeck(StringTokenizer st)
      throws OverListException, UnderListException,
          JokerException { // Throw the data of deck.txt to
    // the list
    int count = 1, countA = 0, countB = 0;
    int temp = 0;

    if (count == 1) { // if the list is empty
      temp = Integer.parseInt(st.nextToken());
      head = tail = new CardNode(temp);
      count++;
    }
    while (st.hasMoreTokens()) { // add to tail
      temp = Integer.parseInt(st.nextToken());
      if (count < 29) {
        tail = tail.next = new CardNode(temp);
        if (tail.cardValue == 27) {
          countA++;
        } else if (tail.cardValue == 28) {
          countB++;
        }
        count++;
      } else { // if the deck is over 28
        throw new OverListException();
      }
    }

    if (count < 29) { // if the deck is under 28
      throw new UnderListException();
    } else if (countA > 1 || countA == 0) {
      // if the Joker A is more than one or less than one
      throw new JokerException("Joker A Exception, the number of Joker A : " + countA);
    } else if (countB > 1 || countB == 0) {
      // if the Joker B is more than one or less than one
      throw new JokerException("Joker B Exception, the number of Joker B : " + countB);
    }
  }

  public void jokerA() {

    CardNode current = head, current2 = head;
    CardNode temp1, temp2;

    if (head.cardValue != 27) { // if head is not JokerA
      while (current2.next.cardValue != 27) current2 = current2.next;
    }
    if (isEmpty()) { // if the list is empty
      throw new NoSuchElementException();

    } else if (current.cardValue == 27) { // if head is JokerA
      head = head.next;
      temp2 = head.next;
      head.next = new CardNode(current.cardValue, temp2);

    } else if (tail.cardValue == 27) { // if tail is JokerA
      while (current.next != tail) current = current.next;
      temp1 = current.next;
      current.next = null;
      tail = current;

      temp2 = head;
      head = head.next;

      head = new CardNode(temp1.cardValue, temp2);
      tail = tail.next = new CardNode(temp2.cardValue);

    } else if (current2.next.cardValue == 27 && current2.next.next == tail) {
      // if the Joker A is number two from the rear
      temp1 = current2.next;
      current2.next = tail;
      tail.next = temp1;
      tail = tail.next;
      tail.next = null;

    } else {
      // if the JokerA is not being head and tail
      while (current.next.cardValue != 27) {
        current = current.next;
      }
      temp1 = current.next;
      temp2 = current.next.next;

      current.next = temp2;
      temp1.next = temp2.next;
      temp2.next = temp1;
    }
  }

  public void jokerB() {

    CardNode current = head;
    CardNode temp1, temp2, temp3;

    current = head;
    if (isEmpty()) {
      throw new NoSuchElementException(); // If there are not elements in list
    } else if (head.cardValue == 28) { // If the Joker is in head
      temp1 = head;
      head = head.next;
      current = current.next.next;
      temp2 = current.next;
      current.next = new CardNode(temp1.cardValue, temp2);

    } else if (tail.cardValue == 28) { // if the Joker B is in tail
      while (current.next != tail) {
        current = current.next;
      }
      temp1 = current.next;
      current.next = null;
      tail = current;

      current = head.next;
      temp2 = current.next;

      current.next = new CardNode(temp1.cardValue, temp2.next);
      tail = tail.next = new CardNode(temp2.cardValue);

    } else {
      // Other case
      while (current.next.cardValue != 28) {
        current = current.next;
      }
      if (current.next.cardValue == 28 && current.next.next.next == tail) {
        // If Joker B is number 3 from the rear
        current = head;
        while (current.next.next != tail) {
          current = current.next;
        }
        temp1 = current.next; // temp1 = 27
        current.next = current.next.next;
        temp2 = head; // temp2 = 16
        head = head.next;
        head = new CardNode(temp1.cardValue, head); // add into head
        tail = tail.next = new CardNode(temp2.cardValue);

      } else if (current.next.next == tail
          && current.next.cardValue == 28) { // if the Joker B is number two from the rear
        temp1 = current.next;
        temp2 = head.next;
        temp3 = head;
        current.next = tail;
        head = head.next;
        tail = tail.next = temp3;
        head = new CardNode(temp1.cardValue, temp2); // add into head
        tail.next = null;
      } else {
        // if Joker B is not in head and tail
        temp1 = current.next;
        temp2 = current.next.next.next;

        current.next = temp1.next;
        temp3 = temp2.next;
        temp2.next = temp1;
        temp1.next = temp3;
      }
    }
  }

  public void tripleCut() {

    CardNode firstJoker = null;
    CardNode secondJoker = null;
    CardNode temp1 = null, temp2 = null, temp3 = null;
    CardNode current = head;
    CardNode last = tail;

    if (last.cardValue == 27 || last.cardValue == 28) { // if the tail is Joker A or Joker B

      secondJoker = tail;
      while (current.next != tail) {
        if (current.next.cardValue == 28 || current.next.cardValue == 27) {
          firstJoker = current;
          break;
        } else {
          current = current.next;
        }
      }
      if ((head.cardValue == 28 && tail.cardValue == 27)
          || (head.cardValue == 27 && tail.cardValue == 28)) {
        return;
      } else {
        temp1 = head;
        temp2 = firstJoker;
        head = firstJoker.next;
        secondJoker.next = temp1;
        tail = temp2;
        tail.next = null;
      }

    } else if (head.cardValue == 28 || head.cardValue == 27) { // if the head is Joker A or Joker B
      firstJoker = head;
      while (current.next != tail) {
        if (current.next.cardValue == 28 || current.next.cardValue == 27) {
          secondJoker = current;
          break;
        } else {
          current = current.next;
        }
      }
      temp1 = secondJoker.next.next; // t1 = 2
      temp2 = tail;
      temp3 = head;
      head = temp1;
      temp2.next = temp3;

      tail = secondJoker.next;
      tail.next = null;

    } else {
      // Point the Joker A and B--------------------------------
      while (current.next != tail) {
        if (current.next.cardValue == 27 || current.next.cardValue == 28) {
          firstJoker = current;
          break;
        } else {
          current = current.next;
        }
      }
      current = firstJoker.next.next;
      while (current.next != tail) {
        if (current.next.cardValue == 27 || current.next.cardValue == 28) {
          secondJoker = current;
          break;
        } else {
          current = current.next;
        }
      }

      // ----- if the next of first Joker (no matter is Joker A or Joker B) is a Joker
      if ((firstJoker.next.cardValue == 28 && firstJoker.next.next.cardValue == 27)
          || (firstJoker.next.cardValue == 27 && firstJoker.next.next.cardValue == 28)) {

        secondJoker = firstJoker.next.next;
        temp1 = head;
        temp2 = tail;
        head = secondJoker.next;

        temp2.next = firstJoker.next;
        secondJoker.next = temp1;
        tail = firstJoker;
        tail.next = null;

        // ----- if the head and tail is Joker (At the same time)------------------------
      } else if ((tail.cardValue != 27 && head.cardValue != 28)
          && (tail.cardValue != 28 && head.cardValue != 27)) {
        temp3 = firstJoker;
        temp1 = head;
        temp2 = tail;

        head = secondJoker.next.next;
        temp2.next = firstJoker.next;
        secondJoker.next.next = temp1;

        while (temp1 != temp3) temp1 = temp1.next;

        tail = temp1;
        tail.next = null;
        // -------------------------------------------------------------------------------
      }
    }
  }

  public void countCut() {

    CardNode current = head, temp = null;

    while (current.next != tail) {
      // point the number of 2 from the rear
      current = current.next;
    }
    for (int i = 0; i < tail.cardValue; i++) {
      temp = head; // Head is storing in temp
      head = head.next; // Delete the head
      current.next =
          new CardNode(temp.cardValue, tail); // Add to current ( The number 2 from the rear )
      current = current.next; // move the current
    }
  }

  public int gotTheKey() {
    // Get the key
    if (head.cardValue == 27 || head.cardValue == 28) {
      // if the Joker is in head ( 27 and 28 )
      return tail.cardValue;
    } else {
      // count the number of head , current will be pointed it
      CardNode current = head;
      for (int i = 0; i < head.cardValue; i++) {
        current = current.next;
      }
      return current.cardValue;
    }
  }

  public void encryption(Solitaire deck, char message[]) {
    // for Searching
    char index[] = {
      ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    int indexNumber = 0;
    int codeIndex = 0;
    CardNode current = deck.head; // Point to deck
    String encryptedMessage = "["; // Use for print out the encrypted message

    System.out.println("Being Encryption :");

    for (int i = 0;
        i < message.length;
        i++) { // if user's message equals with the character of index array
      // then find the character number is array
      for (int j = 0; j < index.length; j++) {
        if (message[i] == index[j]) {
          indexNumber = j;
          break;
        }
      }
      // Print the character of message in column one and print the index number in column two
      System.out.print(message[i] + "\t" + indexNumber);
      System.out.print("\t");
      // print the deck number
      System.out.print(current.cardValue);

      // counting for point the encryted character number
      codeIndex = indexNumber + current.cardValue;

      if (codeIndex <= 26) { // if Deck number add to character number is less than 26
        // Print the character number
        System.out.print("\t" + codeIndex);
        // Print the encryted character
        System.out.println("\t" + index[codeIndex]);
        encryptedMessage = encryptedMessage + " " + index[codeIndex];

      } else { // if Deck number + character number is more than 26
        codeIndex = indexNumber + current.cardValue;
        codeIndex = codeIndex - 26;
        // Print the character number
        System.out.print("\t" + codeIndex);
        // Print the encryted character
        System.out.println("\t" + index[codeIndex]);
        encryptedMessage = encryptedMessage + " " + index[codeIndex];
      }
      // Next deck
      current = current.next;
    }
    // Print all CharacterEncryted message
    System.out.println("Encrypted Message: " + encryptedMessage + " ] ");
  }

  public void decryption(Solitaire deck, char message[]) {
    char index[] = {
      ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
      'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    int indexNumber = 0;
    int codeIndex = 0;
    CardNode current = deck.head; // Point to deck
    String decryptedMessage = "[";
    System.out.println("Being Decryption :");

    for (int i = 0; i < message.length; i++) { // User's message length
      for (int j = 0; j < index.length; j++) {
        if (message[i]
            == index[j]) { // if encrypted message equals with the character of index array
          // then find the character number is array
          indexNumber = j;
          break;
        }
      }
      System.out.print(
          message[i]
              + "\t"
              + indexNumber); // Print the message character and character number of index array
      System.out.print("\t");
      // Print the deck number
      System.out.print(current.cardValue);
      // counting for point the decryted character number
      codeIndex = indexNumber - current.cardValue;

      if (codeIndex <= 0) {
        // if index number - deck number is less than 0
        codeIndex = codeIndex + 26;
        // Print the character number
        System.out.print("\t" + codeIndex);
        // Print the decrypted character
        System.out.println("\t" + index[codeIndex]);
        decryptedMessage = decryptedMessage + " " + index[codeIndex];

      } else {
        codeIndex = indexNumber - current.cardValue;
        // Print the character number
        System.out.print("\t" + codeIndex);
        // Print the character number
        System.out.println("\t" + index[codeIndex]);
        decryptedMessage = decryptedMessage + " " + index[codeIndex];
      }
      // next deck
      current = current.next;
    }
    // Finally , print all of message
    System.out.println("Decrypted Message: " + decryptedMessage + " ] ");
  }

  public void addToTail(int item) { // use to add to tail ( useing for insert deck number in Node)

    if (isEmpty()) {
      head = tail = new CardNode(item);
    } else {
      tail = tail.next = new CardNode(item);
    }
  }

  @Override
  public String toString() { // Show all of key

    String s = "[ ";
    CardNode current = head;
    while (current != null) {
      s += current.cardValue + " ";
      current = current.next;
    }
    return s + "]";
  }
}
