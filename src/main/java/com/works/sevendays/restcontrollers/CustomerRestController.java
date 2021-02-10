package com.works.sevendays.restcontrollers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.works.sevendays.entitiies.Customer;
import com.works.sevendays.repositories.CustomerRepository;
import com.works.sevendays.util.ERest;
import com.works.sevendays.util.R;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

	final CustomerRepository cRepo;
	final HttpServletResponse res;

	public CustomerRestController(CustomerRepository cRepo, HttpServletResponse res) {
		this.cRepo = cRepo;
		this.res = res;
	}
	
	@PostMapping("/insert")
	public Map<ERest, Object> insert(@Valid @RequestBody Customer customer) {
		Map<ERest, Object> hm = new LinkedHashMap<>();

		try {
			Customer cust = cRepo.saveAndFlush(customer);
			hm.put(ERest.status, true);
			hm.put(ERest.message, R.i_success);
			hm.put(ERest.result, cust);
			res.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			hm.put(ERest.status, false);
			hm.put(ERest.message, R.i_fail);
			hm.put(ERest.result, customer);
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
		}
		return hm;
	}
	
	@GetMapping("/list")
	public Map<ERest, Object> list() {
		Map<ERest, Object> hm = new LinkedHashMap<>();
		hm.put(ERest.status, true);
		hm.put(ERest.message, R.list_success);
		hm.put(ERest.result, cRepo.findAll());
		return hm;
	}
	
	@DeleteMapping("/deleteSingle")
	public ResponseEntity<Map<ERest, Object>> deleteSingle( @RequestBody Customer customer ) {
		Map<ERest, Object> hm = new LinkedHashMap<>();
		ResponseEntity<Map<ERest, Object>> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			cRepo.deleteById(customer.getUid());
			hm.put(ERest.status, true);
			hm.put(ERest.message, R.d_success);
			hm.put(ERest.result, customer.getUid());
			responseEntity =  new ResponseEntity<>(hm,HttpStatus.OK);
		} catch (Exception e) {
			hm.put(ERest.status, false);
			hm.put(ERest.message, R.d_fail);
			hm.put(ERest.result, customer.getUid());
			responseEntity =  new ResponseEntity<>(hm,HttpStatus.BAD_REQUEST);
		}
		
		return responseEntity;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<ERest, Object> errorHandler( MethodArgumentNotValidException ex ) {
		Map<ERest, Object> hm = new LinkedHashMap<>();
		
		List<ObjectError> els = ex.getBindingResult().getAllErrors();
		List<Map<String, String>> ls = new ArrayList<>();
		for (ObjectError item : els) {
			String fieldName = (( FieldError ) item).getField();
			String fieldMessage = item.getDefaultMessage();
			Map<String, String> ehm = new HashMap<>();
			ehm.put("fieldName", fieldName);
			ehm.put("fieldMessage", fieldMessage);
			ls.add(ehm);
		}
		hm.put(ERest.status, false);
		hm.put(ERest.message, R.errors);
		hm.put(ERest.result, ls);
		return hm;
	}

	
}
