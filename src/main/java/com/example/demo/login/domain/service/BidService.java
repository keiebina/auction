package com.example.demo.login.domain.service;

import org.springframework.stereotype.Service;

@Service
public class BidService {

	public boolean bidPriceValidator(Integer currentPrice, Integer bidPrice) {
		boolean validation = false;
		if (bidPrice > currentPrice) {
			validation = true;
		}
		return validation;
	}
}
