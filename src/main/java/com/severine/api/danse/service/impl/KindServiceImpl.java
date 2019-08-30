package com.severine.api.danse.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.repositories.KindRepository;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.service.KindService;
import com.severine.api.shared.Utils;
@Service
public class KindServiceImpl implements KindService {

	@Autowired
	private KindRepository kindRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Kind> getAllKinds() {
		return Lists.newArrayList(kindRepository.findAll(Utils.getSort()));
	}
	
	@Override
	public Kind getKind(Long idKind) {
		return kindRepository.findById(idKind).get();
	}
	
	@Override
	public void deleteKind(Long id) {
		productRepository.deleteKindOfProducts(id);
		kindRepository.deleteById(id);	
	}
	@Override
	public Kind addOrUpdateKind(Kind kind) {
		return kindRepository.save(kind);
	}
}
