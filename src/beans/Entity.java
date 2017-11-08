package beans;

import java.util.ArrayList;
import java.util.HashSet;

public class Entity {
	
	private int entityID; //Entity IDs start from 1
	private ArrayList<Address> associatedAddressList;
	private HashSet<Integer> associatedPublicKeySet;
	private long entityNetValue;
	
	public int getEntityID() {
		return entityID;
	}
	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}
	public ArrayList<Address> getAssociatedAddressList() {
		return associatedAddressList;
	}
	public void setAssociatedAddressList(ArrayList<Address> associatedAddressList) {
		this.associatedAddressList = associatedAddressList;
	}
	
	public HashSet<Integer> getAssociatedPublicKeySet() {
		return associatedPublicKeySet;
	}
	public void setAssociatedPublicKeySet(HashSet<Integer> associatedPublicKeySet) {
		this.associatedPublicKeySet = associatedPublicKeySet;
	}
	public long getEntityNetValue() {
		return entityNetValue;
	}
	public void setEntityNetValue(long entityNetValue) {
		this.entityNetValue = entityNetValue;
	}
	
	
}
