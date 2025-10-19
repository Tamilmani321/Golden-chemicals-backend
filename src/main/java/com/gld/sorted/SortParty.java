package com.gld.sorted;

import java.util.Comparator;

import com.gld.entity.Party;

public class SortParty implements Comparator<Party>{

	@Override
	public int compare(Party o1, Party o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
