package com.severine.api.danse.service;

import java.util.List;
import java.util.Set;

import com.severine.api.danse.entities.Box;
import com.severine.api.danse.entities.Category;
import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.entities.Picture;
import com.severine.api.danse.entities.Product;
import com.severine.api.danse.entities.SizeQuantity;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;

public interface ProductsService {
	public List<Product> getAllProducts();
	public Product getProduct(long idProduct);
	public List<Product> getAllProductsWithQuantityBooked();
	public void deleteProduct(Long id);
	public void deleteAllSizeQuantitiesByIdProduct(Long id);
	public Product addOrUpdateProduct(Product product,Set<SizeQuantity> sizeQuantities,Set<Picture>pictures);
	public List<Product> getAllProductsByBox(Box box);
	public List<Product> searchByCriteria(List<Colour>colours, List<Category>categories, List<Kind>kinds, List<Type>types, List<Box>boxes,Integer quantity, String name, Boolean available, List<SuperType> superTypes);
}
