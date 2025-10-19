package com.gld.common;

public class CustomQueries {
    public static final String GET_TRANSACTIONS =
        "SELECT t FROM Transaction t WHERE t.party.id = :partyId ORDER BY t.txDate DESC";

    public static final String GET_TOTAL_AMOUNT =
        "SELECT SUM(t.amount) FROM Transaction t WHERE t.party.id = :partyId";
    
//    public static final String GET_TOTAL_AMOUNT =
//    	    "SELECT SUM( " +
//    	    "t.amount * CASE " +
//    	    "WHEN t.type = 'DEBIT' THEN 1 " +
//    	    "ELSE -1 END ) " +
//    	    "FROM Transaction t " +
//    	    "WHERE t.party.id = :partyId";
    
    public static final String GET_PREVIOUS_TXN = 
    		"""
    	    SELECT t
    	    FROM Transaction t
    	    WHERE t.party.id = :partyId
    	      AND t.id = (
    	          SELECT MAX(t2.id)
    	          FROM Transaction t2
    	          WHERE t2.party.id = :partyId
    	            AND t2.id < :currentId
    	      )
    	""";


}

