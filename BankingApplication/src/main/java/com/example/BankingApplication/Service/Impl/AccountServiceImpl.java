package com.example.BankingApplication.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.BankingApplication.Dto.AccountDto;
import com.example.BankingApplication.Entity.Account;
import com.example.BankingApplication.Mapper.AccountMapper;
import com.example.BankingApplication.Repository.AccountRepository;
import com.example.BankingApplication.Service.AccountService;

@Service
public class AccountServiceImpl  implements AccountService{
	@Autowired
	private AccountRepository accountRepository;
	

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		
		Account account  = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = 	accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(savedAccount);
			
			
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account  = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("User does not exist"));
		
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account  = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("User does not exist"));

		double totalBalance = account.getBalance()+amount;
		account.setBalance(totalBalance);
		
		Account savedAccount = accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account  = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("User does not exist"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient Balance");
		}
		
		Double totalBalance = account.getBalance() - amount;
		account.setBalance(totalBalance);
		Account savedAccount = accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(savedAccount);
		
	}

	@Override
	public List<AccountDto> getAllAccounts() {
		return accountRepository.findAll().stream().map((account)-> AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		Account account  = accountRepository.findById(id).orElseThrow(()-> new RuntimeException("User does not exist"));
		
		accountRepository.delete(account);
		
		
	}
	
	
	

}
