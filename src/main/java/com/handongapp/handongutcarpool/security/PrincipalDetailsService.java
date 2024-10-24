package com.handongapp.handongutcarpool.security;

import com.handongapp.handongutcarpool.domain.Tbuser;
import com.handongapp.handongutcarpool.exception.NoMatchingDataException;
import com.handongapp.handongutcarpool.repository.TbuserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {
	
	private final TbuserRepository tbuserRepository;
	
	public PrincipalDetailsService(TbuserRepository tbuserRepository) {
		this.tbuserRepository = tbuserRepository;
	}
	
    /**
	 *  principalDetails 생성을 위한 함수.
	 *  학번으로 tbuser 조회, principalDetails 생성.
	 */
	@Override
	public UserDetails loadUserByUsername(String hakbun) throws UsernameNotFoundException {
		Tbuser tbuser = tbuserRepository.findByHakbun(hakbun).orElseThrow(() -> new NoMatchingDataException("hakbun : " + hakbun));
		return new PrincipalDetails(tbuser);
	}
	
}