package beans;

import com.opencsv.bean.CsvBindByPosition;

public class Outputs {
	
	@CsvBindByPosition(position = 0)
    private int outputId;

    @CsvBindByPosition(position = 1)
    private int txID;

    @CsvBindByPosition(position = 2)
    private int publicKey;
    
    @CsvBindByPosition(position = 3)
    private long value;

	public int getOutputId() {
		return outputId;
	}

	public void setOutputId(int outputId) {
		this.outputId = outputId;
	}

	public int getTxID() {
		return txID;
	}

	public void setTxID(int txID) {
		this.txID = txID;
	}

	public int getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(int publicKey) {
		this.publicKey = publicKey;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
    
    
    
}
