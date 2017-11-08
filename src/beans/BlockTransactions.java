package beans;

import java.util.ArrayList;

public class BlockTransactions {
	private int txID;
	private int blockID;
	private int isCoinBase;
	
	private ArrayList<UTXO> inputUTXOList;
	private ArrayList<UTXO> outputUTXOList;
	
	public int getTxID() {
		return txID;
	}
	public void setTxID(int txID) {
		this.txID = txID;
	}
	public int getBlockID() {
		return blockID;
	}
	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}
	public int getIsCoinBase() {
		return isCoinBase;
	}
	public void setIsCoinBase(int isCoinBase) {
		this.isCoinBase = isCoinBase;
	}
	public ArrayList<UTXO> getInputUTXOList() {
		return inputUTXOList;
	}
	public void setInputUTXOList(ArrayList<UTXO> inputUTXOList) {
		this.inputUTXOList = inputUTXOList;
	}
	public ArrayList<UTXO> getOutputUTXOList() {
		return outputUTXOList;
	}
	public void setOutputUTXOList(ArrayList<UTXO> outputUTXOList) {
		this.outputUTXOList = outputUTXOList;
	}
	
	
}
