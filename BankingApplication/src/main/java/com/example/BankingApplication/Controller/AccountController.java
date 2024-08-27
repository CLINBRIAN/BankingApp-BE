package com.example.BankingApplication.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.BankingApplication.Dto.AccountDto;
import com.example.BankingApplication.Service.AccountService;


@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("http://localhost:4200")
public class AccountController {
	
	@Autowired
	private AccountService accountService; 
	
	//for creating acccount
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
	}
	
	//for getting account by Id
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
		AccountDto accountDDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDDto);
		
	}
	
	//for deposit amount in the account
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> request){
		
		Double amount  = request.get("amount");
		AccountDto accountDto = accountService.deposit(id, amount);
		
		return ResponseEntity.ok(accountDto);
		
	}
	
	//for withdraw amount from the account
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> request){
		
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount);
		 
		return ResponseEntity.ok(accountDto);
		
	}
	
	//for getting all the accounts
	@GetMapping()
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
    	
    	List<AccountDto> accounts = accountService.getAllAccounts();
    	
    	return ResponseEntity.ok(accounts);
    }
	
	
	//for deleting account
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		
		return ResponseEntity.ok("Account deleted successfully");
	}
	
	
	
	
	

}
