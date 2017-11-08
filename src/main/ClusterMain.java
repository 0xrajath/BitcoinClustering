package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.CSVReader;
import beans.*;

public class ClusterMain {
	
	//Lists generated from initial CSVs
	private static ArrayList<Inputs> inputList = new ArrayList<Inputs>();
	private static ArrayList<Outputs> outputList = new ArrayList<Outputs>();
	private static ArrayList<Transactions> transactionList = new ArrayList<Transactions>();
	
	//New Data Structures generated for working on
	private static ArrayList<Block> blockList = new ArrayList<Block>();
	private static ArrayList<BlockTransactions> blocktransactionsList = new ArrayList<BlockTransactions>();
	private static HashMap<Integer, UTXO> outputIDtoUTXO = new HashMap<Integer, UTXO>();	
	private static HashMap<Integer, Integer> publicKeyToEntityID = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Entity> EntityIDtoEntity = new HashMap<Integer, Entity>();
	private static int entityIDGlobal=0;
	private static ArrayList<Address> addressList = new ArrayList<Address>();
	private static ArrayList<Integer> publicKeyList = new ArrayList<Integer>();
	
	private static void copyFromCSV(){
		
		try {
			FileReader inputfileReader;
			inputfileReader = new FileReader("resources/inputs.csv");
			
			CSVReader inputReader = new CSVReader(inputfileReader);
			String [] nextLine1;
			
		     while ((nextLine1 = inputReader.readNext()) != null) {		        
		    	 	
		    	 	Inputs inputs = new Inputs();		    	 	
		    	 	inputs.setInputId(Integer.parseInt(nextLine1[0]));
		        inputs.setTxID(Integer.parseInt(nextLine1[1]));
		        inputs.setSpendingOutputID(Integer.parseInt(nextLine1[2]));
		        
		        inputList.add(inputs);
		        
		     }
		     
		    inputReader.close();
		     
		     
		    FileReader outputfileReader;
		    outputfileReader = new FileReader("resources/outputs.csv");
			
			CSVReader outputReader = new CSVReader(outputfileReader);
			String [] nextLine2;
			
		     while ((nextLine2 = outputReader.readNext()) != null) {		        
		    	 	
		    	 	Outputs outputs = new Outputs();	    	 	
		    	 	outputs.setOutputId(Integer.parseInt(nextLine2[0]));
		        outputs.setTxID(Integer.parseInt(nextLine2[1]));
		        outputs.setPublicKey(Integer.parseInt(nextLine2[2]));
		        outputs.setValue(Long.parseLong(nextLine2[3]));
		        
		        outputList.add(outputs);
		        
		     }
		     
		     outputReader.close();
		     
		    
		     FileReader txnfileReader;
			 txnfileReader = new FileReader("resources/transactions.csv");
			
			 CSVReader txnReader = new CSVReader(txnfileReader);
			 String [] nextLine3;
			
		     while ((nextLine3 = txnReader.readNext()) != null) {		        
		    	 	
		    	 	Transactions txn = new Transactions();		    	 	
		    	 	txn.setTxId(Integer.parseInt(nextLine3[0]));
		        txn.setBlockID(Integer.parseInt(nextLine3[1]));
		        txn.setIsCoinBase(Integer.parseInt(nextLine3[2]));
		        
		        transactionList.add(txn);
		        
		     }
		     
		     txnReader.close();
		     
		     
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void initializePOJOs(){
		
		//Looping through transactions.csv and populating BlockTransactions(ID, BlockID and isCoinBase) POJO
		for(Transactions txn: transactionList) {
			BlockTransactions blockTransactions = new BlockTransactions();
	
			blockTransactions.setTxID(txn.getTxId());
			blockTransactions.setBlockID(txn.getBlockID());
			blockTransactions.setIsCoinBase(txn.getIsCoinBase());
			blocktransactionsList.add(blockTransactions);
	
		}
		
		
		//Looping through outputs.csv and populating UTXO(outputID, outputTxID, Value and PublicKey) and Address(PublicKey) POJOs and outputIDtoUTXO and publicKeyToEntity Maps
		for(Outputs outputs: outputList) {

			//Making entries for UTXO POJO
			UTXO utxo = new UTXO();
			//Should I initialize utxoID?? Or do I even need it??
			utxo.setOutputID(outputs.getOutputId());
			utxo.setOutputTxID(outputs.getTxID());
			utxo.setValue(outputs.getValue());
			int utxoAddress = outputs.getPublicKey();
			utxo.setPublicKey(utxoAddress);
			
			
			//Adding outputId(key) and utxo(value) to Map
			outputIDtoUTXO.put(outputs.getOutputId(), utxo);
			
			
			//Setting up PublicKey(key) to Entity(value) Map
			if(!(publicKeyToEntityID.containsKey(utxoAddress))) {
				publicKeyToEntityID.put(utxoAddress, 0); 
			}
			
			
			//Making entries for Address POJO
			boolean addressInAddresslist = false;			
			if(!(addressList.isEmpty())) {
				for(Address address: addressList) {
					if(address.getPublicKey()==utxoAddress) {
						addressInAddresslist=true;
						break;
					}
				}
				if(!addressInAddresslist) {
					Address address = new Address();
					address.setPublicKey(utxoAddress);
					addressList.add(address);
				}
			}else {
				Address address = new Address();
				address.setPublicKey(utxoAddress);
				addressList.add(address);
			}		
			
		}
		
		
		//Looping through inputs.csv and populating InputTxID field of each UTXO POJO
		for(Inputs inputs: inputList){
			int outputID = inputs.getSpendingOutputID();
			if(outputIDtoUTXO.containsKey(outputID)) {
				UTXO utxo = outputIDtoUTXO.get(outputID);
				utxo.setInputTxID(inputs.getTxID());
				outputIDtoUTXO.replace(outputID, utxo);
			}
		}
		
		
		//Looping through UTXOs to populate Input and Output UTXO lists of BlockTransactions POJOs and populating currentValue of Address POJOs
		for (UTXO utxo : outputIDtoUTXO.values()) {
		    
			//Setting currentValue of Address POJO
			for(Address address: addressList) {
		    		if(utxo.getPublicKey()==address.getPublicKey()) {
		    			//Checking if UTXO has never been spent, since inputTxID will be 0 by default in that case
		    			if(utxo.getInputTxID() == 0){
		    				address.setCurrentValue(address.getCurrentValue()+utxo.getValue());
		    			}
		    		}
		    }
		    
			//Adding respective UTXOs to InputUTXO list of BlockTransactions POJO
			for(BlockTransactions blockTxns: blocktransactionsList){
				if(utxo.getInputTxID()==blockTxns.getTxID()){
					blockTxns.getInputUTXOList().add(utxo); //Do we have to check if The UTXO already exists in list??
				}
			}
			
			//Adding respective UTXOs to OutputUTXO list of BlockTransactions POJO
			for(BlockTransactions blockTxns: blocktransactionsList){
				if(utxo.getOutputTxID()==blockTxns.getTxID()){
					blockTxns.getOutputUTXOList().add(utxo); //Do we have to check if The UTXO already exists in list??
				}
			}
		    
		}
		
		//Looping through BlockTransactions to populate Block POJOs
		for(BlockTransactions blockTxns: blocktransactionsList){
			
			Block block = new Block();
			block.setBlockID(blockTxns.getBlockID());
			boolean blockInBlocklist = false;
			
			if(!(blockList.isEmpty())) {
				for(Block blk: blockList) {
					if(blk.getBlockID()==block.getBlockID()) {
						blk.getBlockTransactionList().add(blockTxns);
						blockInBlocklist=true;
						break;
					}
				}
				if(!blockInBlocklist) {
					block.getBlockTransactionList().add(blockTxns);
					blockList.add(block);
				}
			}else {
				block.getBlockTransactionList().add(blockTxns);
				blockList.add(block);
			}
		
		}
		
		//Populating Entity POJOs
		for(Block block: blockList) {
			for(BlockTransactions blockTxns: block.getBlockTransactionList()) {
				//Considering case for 1-to-1 Serial Control Idiom, n-to-1 Serial Control Idiom or Joint Control Idiom. 
				if(blockTxns.getOutputUTXOList().size() == 1 && blockTxns.getInputUTXOList().size()>0) { //Input List > 0 since there can be a block where there is only coinbase txn.
					
					Set<Integer> entityIDSet = new HashSet<>();
					
					//Loop through OutputUTXO list and add if any existing entities to entityIDSet
					for(UTXO utxo: blockTxns.getInputUTXOList()) {
						int entityID = publicKeyToEntityID.get(utxo.getPublicKey());
						if(entityID != 0){
							entityIDSet.add(entityID); //Adds no duplicate elements since its a HashSet.
						}
					}	
					//Loop through InputUTXO list and add if any existing entities to entityIDSet
					for(UTXO utxo: blockTxns.getOutputUTXOList()) {
						int entityID = publicKeyToEntityID.get(utxo.getPublicKey());
						if(entityID != 0){
							entityIDSet.add(entityID); //Adds no duplicate elements since its a HashSet.
						}
					}	
						
					
					if(entityIDSet.isEmpty()) { //If no linked entity found, create new entity and map it to these public keys
						entityIDGlobal++;
						Entity entity = new Entity();
						entity.setEntityID(entityIDGlobal);
						
						for(UTXO utxo: blockTxns.getInputUTXOList()) {
							entity.getAssociatedPublicKeySet().add(utxo.getPublicKey());
							publicKeyToEntityID.put(utxo.getPublicKey(), entityIDGlobal);
						}
						
						for(UTXO utxo: blockTxns.getOutputUTXOList()) {
							entity.getAssociatedPublicKeySet().add(utxo.getPublicKey());
							publicKeyToEntityID.put(utxo.getPublicKey(), entityIDGlobal);
						}
						
						EntityIDtoEntity.put(entityIDGlobal, entity);						
						
					}else { //If linked entities found, create new entity by merging all their associated public keys
						entityIDGlobal++;
						Entity entity = new Entity();
						entity.setEntityID(entityIDGlobal);
						
						for(UTXO utxo: blockTxns.getInputUTXOList()) {						
							HashSet<Integer> associatedPublicKeySet = (EntityIDtoEntity.get(publicKeyToEntityID.get(utxo.getPublicKey()))).getAssociatedPublicKeySet();
							entity.getAssociatedPublicKeySet().removeAll(associatedPublicKeySet);
							entity.getAssociatedPublicKeySet().addAll(associatedPublicKeySet);
							//Deleting and rewriting old entities which have already been merged into new entity
							EntityIDtoEntity.remove(publicKeyToEntityID.get(utxo.getPublicKey()));				
							publicKeyToEntityID.put(utxo.getPublicKey(), entityIDGlobal);
						}
						
						for(UTXO utxo: blockTxns.getOutputUTXOList()) {						
							HashSet<Integer> associatedPublicKeySet = (EntityIDtoEntity.get(publicKeyToEntityID.get(utxo.getPublicKey()))).getAssociatedPublicKeySet();
							entity.getAssociatedPublicKeySet().removeAll(associatedPublicKeySet);
							entity.getAssociatedPublicKeySet().addAll(associatedPublicKeySet);
							//Deleting and rewriting old entities which have already been merged into new entity
							EntityIDtoEntity.remove(publicKeyToEntityID.get(utxo.getPublicKey()));				
							publicKeyToEntityID.put(utxo.getPublicKey(), entityIDGlobal);
						}
						
						
					}
						
						//This else part Might throw some Null pointer exception from here. Come back and check later!!!!!!!!!!
								
				}
			}
		}
		
		//Populating List of associated addresses in Entity POJOs
		for(Entity entity: EntityIDtoEntity.values()) {			
			for(int publicKey: entity.getAssociatedPublicKeySet()) {
				for(Address address: addressList) {
					if(address.getPublicKey()==publicKey) {
						entity.getAssociatedAddressList().add(address);
						break;
					}
				}
			}
		}
		
		//Populating Net value in Entity POJOs
		for(Entity entity: EntityIDtoEntity.values()) {				
			for(Address address: entity.getAssociatedAddressList()) {
				entity.setEntityNetValue(entity.getEntityNetValue()+address.getCurrentValue());
			}
		}
		
	}
	
	
	//Method to find richest entity in first 100,001 bitcoin blocks
	private static void richestEntity() {
		long greatestValue=0;
		int greatestValueHoldingEntity=0;
		
		for(Entity entity: EntityIDtoEntity.values()) {				
			if(entity.getEntityNetValue()>greatestValue) {
				greatestValue = entity.getEntityNetValue();
				greatestValueHoldingEntity = entity.getEntityID();
			}
		}
		
		Entity entity = EntityIDtoEntity.get(greatestValueHoldingEntity);
		
		System.out.println("The entity controlling the most total (unspent) bitcoins is: Entity ID "+ greatestValueHoldingEntity);
		System.out.println("This entity controls: "+ entity.getEntityNetValue()+" Bitcoins");
		System.out.println("This entity controls the following addresses: ");
		
		int lowestAddress=1000000;		
		for(int publicKey: entity.getAssociatedPublicKeySet()){
			System.out.println(publicKey);		
			if(publicKey < lowestAddress){
				lowestAddress=publicKey;
			}
		}
		
		System.out.println("Lowest address of entity is: "+lowestAddress);
			
		for(Address address: entity.getAssociatedAddressList()) {
			if(lowestAddress==address.getPublicKey()) {
				System.out.println("Total value of Bitcoins held by lowest address of this entity: "+address.getCurrentValue());
				break;
			}
		}		
		
	}
	
	
	
	public static void main(String[] args) {
		
		copyFromCSV();
		
		initializePOJOs();
		
		//Finding Richest Entity
		richestEntity();
		
					
	}

}
