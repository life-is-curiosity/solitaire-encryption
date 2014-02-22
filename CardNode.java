
class CardNode {
	int cardValue;
	CardNode next;
	
	public CardNode(){};

	public CardNode(int cardValue) {
		this.cardValue = cardValue;
	}

	public CardNode(int cardValue, CardNode next) {
		this.cardValue = cardValue;
		this.next = next;
	}
}