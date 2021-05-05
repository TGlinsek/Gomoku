package logika;

public enum Polje {
	PRAZNO, BELO, CRNO;
	

	@Override
	public String toString() {
		if (this == Polje.BELO) {
			return "b";
		} else if (this == Polje.CRNO) {
			return "è";
		} else {
			return " ";
		}
	}
}
