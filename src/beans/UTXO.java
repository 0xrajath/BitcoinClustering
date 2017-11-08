package beans;

public class UTXO {
	private int outputID;
	private int inputTxID;	
	private int outputTxID;
	private long value;
	private int publicKey;
	
	public int getInputTxID() {
		return inputTxID;
	}
	public void setInputTxID(int inputTxID) {
		this.inputTxID = inputTxID;
	}
	public int getOutputID() {
		return outputID;
	}
	public void setOutputID(int outputID) {
		this.outputID = outputID;
	}
	public int getOutputTxID() {
		return outputTxID;
	}
	public void setOutputTxID(int outputTxID) {
		this.outputTxID = outputTxID;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public int getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(int publicKey) {
		this.publicKey = publicKey;
	}
	
	
}
