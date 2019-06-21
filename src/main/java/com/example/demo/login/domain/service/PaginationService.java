package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PaginationService {

	public List<Integer> pagination(long count){
		long totalPages = totalPages(count);
		List<Integer> pages = new ArrayList<Integer>();
		for (int i = 0; i < totalPages; i++) {
			pages.add(i + 1);
		}
		if (pages.size() >=2) {
			Collections.reverse(pages);
		}
		return pages;
	}
	
	public long totalPages(long count) {
		long totalPages = ((count - 1) / 6) + 1; 
		return totalPages;
	}
}
