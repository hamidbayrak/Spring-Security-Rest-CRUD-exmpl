package com.works.sevendays.util;

import com.works.sevendays.entitiies.UserInfo;
import com.works.sevendays.repositories.UserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class Util {

	final UserInfoRepository iRepo;
	public Util (UserInfoRepository iRepo){
		this.iRepo=iRepo;
	}
	
	public String password(String pass, int count) {
		String c_pass = pass;
		for (int i = 0; i < count; i++) {
			c_pass = MD5(c_pass);
		}
		return c_pass;
	}

	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	public void userInfo(String url){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> cls = auth.getAuthorities();

		StringBuilder builder = new StringBuilder();
		for (GrantedAuthority item : cls){
			builder.append(item.getAuthority());
			builder.append(" ");
		}

		String name = auth.getName();
		String details = auth.getDetails().toString();
		String roles = builder.toString();

		UserInfo info = new UserInfo();
		info.setName(name);
		info.setUrl(url);
		info.setRoles(roles);
		info.setDetails(details);
		info.setDate(new Date());

		iRepo.saveAndFlush(info);
		long count = iRepo.countByName(name);
		System.out.println("Count : " + count);

		/*
		System.out.println(name);
		System.out.println(details);
		System.out.println(roles);
		System.out.println(url);
		*/
	}

}
