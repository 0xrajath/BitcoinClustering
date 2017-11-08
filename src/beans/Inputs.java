package beans;

import com.opencsv.bean.CsvBindByPosition;

public class Inputs {
	
	@CsvBindByPosition(position = 0)
    private int inputId;

    @CsvBindByPosition(position = 1)
    private int txID;

    @CsvBindByPosition(position = 2)
    private int spendingOutputID;

	public int getInputId() {
		return inputId;
	}

	public void setInputId(int inputId) {
		this.inputId = inputId;
	}

	public int getTxID() {
		return txID;
	}

	public void setTxID(int txID) {
		this.txID = txID;
	}

	public int getSpendingOutputID() {
		return spendingOutputID;
	}

	public void setSpendingOutputID(int spendingOutputID) {
		this.spendingOutputID = spendingOutputID;
	}
    
    
	
}
