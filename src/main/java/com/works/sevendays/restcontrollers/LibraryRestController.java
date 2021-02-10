package com.works.sevendays.restcontrollers;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.works.sevendays.entitiies.Address;
import com.works.sevendays.entitiies.Library;
import com.works.sevendays.repositories.AddressRepository;
import com.works.sevendays.repositories.LibraryRepository;
import com.works.sevendays.util.ERest;
import com.works.sevendays.util.R;



@RestController
@RequestMapping("/library")
public class LibraryRestController {
	
	final LibraryRepository lRepo;
	final AddressRepository aRepo;
	public LibraryRestController( LibraryRepository lRepo, AddressRepository aRepo ) {
		this.lRepo = lRepo;
		this.aRepo = aRepo;
	}
	
	
	@PostMapping("/insert")
	public Map<ERest, Object> insert( @RequestBody Library library ) {
		Map<ERest, Object> hm = new LinkedHashMap<>();

		Address adr = aRepo.saveAndFlush(library.getAddress());
		library.setAddress(adr);
		
		try {
			Library lib = lRepo.saveAndFlush(library);
			hm.put(ERest.status, true);
			hm.put(ERest.message, R.i_success);
			hm.put(ERest.result, lib);
		} catch (Exception e) {
			hm.put(ERest.status, false);
			hm.put(ERest.message, R.i_fail);
			hm.put(ERest.result, library);
		}
		
		return hm;
	}
	
	
	@GetMapping("/list")
	public Map<ERest, Object> list() {
		Map<ERest, Object> hm = new LinkedHashMap<>();
		
		hm.put(ERest.status, true);
		hm.put(ERest.message, R.list_success);
		hm.put(ERest.result, lRepo.findAll());
		
		return hm;
	}
	

}
