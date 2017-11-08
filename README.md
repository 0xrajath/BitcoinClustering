Bitcoin is not truly anonymous as it claims to be. 
It is rather pseudoanonymous as the addresses can be linked through certain de-anonymization techniques and understanding of the bitcoin transaction graph.

In this project, I assume two idioms of use:
1) Joint control: when two addresses provide common inputs to a single transaction, they are likely controlled by the same entity.
2) Serial control: whenever a transaction has a single output, the output address is usually controlled by the same entity that owns the input address. The reasoning here is that it is rare for one entity to own exactly the right amount of bitcoin in a single UTXO that it wants to pay another entity, so typically a change address is used to return the leftovers to the original owner. It can be difficult to identify change addresses reliably, but if there is no change address typically the money is simply being cycled from one key to another by the same owner (or distinct UTXOs are being consolidated into a single larger UTXO).


The resources folder contains 3 CSVs having simplified data for the first 100,001 blocks of bitcoin:
1) inputs.csv: id (Input Id), tx_id (Trasaction Id), output_id (Related UTXO/Output being spent)
2) outputs.csv: id (Output Id/UTXO Id), tx_id (Transaction Id), pk_id (Public Key/Address), value(Value associated with UTXO in Satoshis)
3) transactions.csv: id (Transaction Id), block_id(Block Id containing respective transaction), is_coinbase(Coinbase txn or not)

Note: Bitcoin Mining Reward during this period was 50BTC.

Goal:
To Find Richest Entity during first 100,001 blocks of Bitcoin who holds the most amount of BTC in his associated addresses.

To Run:
Import 'BitcoinClustering' as a Java Project to any IDE and run ClusterMain.java
