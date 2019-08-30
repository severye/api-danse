package com.severine.api.danse.service;

import java.util.List;

import com.severine.api.danse.entities.Kind;

public interface KindService {
	public List<Kind> getAllKinds();
	public Kind getKind(Long idKind);
	public void deleteKind(Long id);
	public Kind addOrUpdateKind(Kind kind);
	
}
