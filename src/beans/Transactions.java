package beans;

import com.opencsv.bean.CsvBindByPosition;

public class Transactions {
	
	@CsvBindByPosition(position = 0)
    private int txId;

    @CsvBindByPosition(position = 1)
    private int blockID;

    @CsvBindByPosition(position = 2)
    private int isCoinBase;

	public int getTxId() {
		return txId;
	}

	public void setTxId(int txId) {
		this.txId = txId;
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
    
}
