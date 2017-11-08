package beans;

import java.util.ArrayList;

public class Block {
	private int blockID;
	private ArrayList<BlockTransactions> blockTransactionList;
	
	public int getBlockID() {
		return blockID;
	}
	public void setBlockID(int blockID) {
		this.blockID = blockID;
	}
	public ArrayList<BlockTransactions> getBlockTransactionList() {
		return blockTransactionList;
	}
	public void setBlockTransactionList(ArrayList<BlockTransactions> blockTransactionList) {
		this.blockTransactionList = blockTransactionList;
	}
	
	
}
