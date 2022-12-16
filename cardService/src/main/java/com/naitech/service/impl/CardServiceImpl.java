package com.naitech.service.impl;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naitech.dao.CardDao;
import com.naitech.model.Card;
import com.naitech.model.VaultResponseDecryption;
import com.naitech.model.VaultResponseEncryption;
import com.naitech.service.CardService;

@Service
public class CardServiceImpl implements CardService {
	
	@Autowired
	private CardDao cardDao;
	
	@Value( "${vault.encrypt.url}" )
	private String urlEncryption;
	@Value( "${vault.decrypt.url}" )
	private String urlDecryption;
	@Value( "${vault.token}" )
	private String token;

	@Override
	public void saveCard(Card card) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("X-Vault-Token",token);

		Map<String, Object> map = new HashMap<String, Object>();
		String cardNumberBase64 = Base64.getEncoder().encodeToString(card.getNumber().getBytes());
		map.put("plaintext", cardNumberBase64);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(urlEncryption, entity, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		VaultResponseEncryption vaultResponse = null;
		try {
			vaultResponse = objectMapper.readValue(response.getBody().toString(), VaultResponseEncryption.class);
			if(vaultResponse != null && card != null) {
				card.setNumber(vaultResponse.getData().getCiphertext());
				cardDao.save(card);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public Card getCard(int id) {
		Optional<Card> o = cardDao.findById(id);
		Card card = o.get();
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("X-Vault-Token",token);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ciphertext", card.getNumber());

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(urlDecryption, entity, String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		VaultResponseDecryption vaultResponse = null;
		
		try {
			vaultResponse = objectMapper.readValue(response.getBody().toString(), VaultResponseDecryption.class);
			if(vaultResponse != null) {
				card.setNumber(vaultResponse.getData().getPlaintext());
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return card;
	}
	
	

}
