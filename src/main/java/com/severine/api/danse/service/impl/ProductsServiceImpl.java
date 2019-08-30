package com.severine.api.danse.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.severine.api.danse.entities.Book;
import com.severine.api.danse.entities.Box;
import com.severine.api.danse.entities.Category;
import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.entities.Picture;
import com.severine.api.danse.entities.Product;
import com.severine.api.danse.entities.SizeQuantity;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;
import com.severine.api.danse.repositories.BookRepository;
import com.severine.api.danse.repositories.PictureRepository;
import com.severine.api.danse.repositories.ProductRepository;
import com.severine.api.danse.repositories.SizeQuantityRepository;
import com.severine.api.danse.repositories.TypeRepository;
import com.severine.api.danse.service.ProductsService;
import com.severine.api.shared.Utils;

@Service("ProductsService")
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	EntityManager em;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SizeQuantityRepository sizeQuantityRepository;
	@Autowired
	private PictureRepository pictureRepository;
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public List<Product> getAllProducts() {
		return Lists.newArrayList(productRepository.findAll(Utils.getSort()));
	}

	@Override
	public Product getProduct(long idProduct) {
		return productRepository.findById(idProduct).get();
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
		
	}
	
	@Override
	public List<Product> getAllProductsWithQuantityBooked() {
		List<Product> products = getAllProducts();
		for (Product product : products) {
			product.setQuantityBooked(bookRepository.getQuantityBookedAndEndDateIsNull(product.getId()));
		}
		Collections.sort(products);
		return products;
	}
	
	@Override
	public Product addOrUpdateProduct(Product product, Set<SizeQuantity> sizeQuantities,Set<Picture>pictures) {
		Product p = productRepository.save(product);
		if(sizeQuantities!=null) {
			for (SizeQuantity sizeQuantity : sizeQuantities) {
				sizeQuantity.setIdProduct(p.getId());
				try {
					sizeQuantityRepository.save(sizeQuantity);
				}catch(Exception e) {System.out.println(e);}
			}
			p.setSizeQuantities(sizeQuantities);			
		}
		if(pictures!=null) {
			for (Picture picture : pictures) {
				picture.setIdProduct(p.getId());
				try {
				pictureRepository.save(picture);
				}catch(Exception e) {System.out.println(e);}
			}
			p.setPictures(pictures);			
		}
		return p;
	}

	@Override
	public void deleteAllSizeQuantitiesByIdProduct(Long id) {
		sizeQuantityRepository.deleteAllSizeQuantityByIdProduct(id);
	}
	
	public List<Product> searchByCriteria(List<Colour>colours, List<Category>categories, List<Kind>kinds, List<Type>types, List<Box>boxes,Integer quantity, String name, Boolean available, List<SuperType> superTypes) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
	    Root<Product> productRoot = criteria.from(Product.class);
		Predicate where = builder.conjunction();
		
		if(colours!=null && colours.size()>0) {
			where = builder.and(where,productRoot.get("colour").in(colours));			
		}
		if(categories!=null && categories.size()>0){
			where = builder.and(where,productRoot.get("category").in(categories));			
		}
		if(kinds!=null && kinds.size()>0){
			where = builder.and(where,productRoot.get("kind").in(kinds));			
		}

		if(types!=null && types.size()>0){
			where = builder.and(where,productRoot.get("type").in(types));			
		}else {
			if(superTypes!=null && superTypes.size()>0){
				types = typeRepository.findAllTypeByIdSuperType(superTypes);
				where = builder.and(where,productRoot.get("type").in(types));					
			}
		}
		if(boxes!=null && boxes.size()>0){
			where = builder.and(where,productRoot.get("box").in(boxes));			
		}
		if(available) {
			if(quantity!=null){
				Subquery<Long> sub = criteria.subquery(Long.class);
				Root<Book> subRoot = sub.from(Book.class);
				Join<Book, Product> join = subRoot.join("productBook");
				sub.select(subRoot.get("productBook"));
				sub.where(builder.isNull(subRoot.get("endDate")));
				sub.groupBy(subRoot.get("productBook"),join.get("totalQuantity"));
				Expression<Number> diff = builder.diff(join.get("totalQuantity"),builder.sum(subRoot.get("quantity")));
				sub.having(builder.lt(diff, quantity));
				
				Predicate notIn = builder.not(productRoot.get("id").in(sub));
				
				where = builder.and(where, notIn);	
				where = builder.and(where,builder.ge(productRoot.get("totalQuantity"), quantity));			
			}else {
				Subquery<Long> sub = criteria.subquery(Long.class);
				Root<Book> subRoot = sub.from(Book.class);
				Join<Book, Product> join = subRoot.join("productBook");
				sub.select(subRoot.get("productBook"));
				sub.where(builder.isNull(subRoot.get("endDate")));
				sub.groupBy(subRoot.get("productBook"),join.get("totalQuantity"));
				Expression<Number> diff = builder.diff(join.get("totalQuantity"),builder.sum(subRoot.get("quantity")));
				sub.having(builder.le(diff, 0));
				
				Predicate notIn = builder.not(productRoot.get("id").in(sub));
				
				where = builder.and(where, notIn);	
				where = builder.and(where,builder.ge(productRoot.get("totalQuantity"), 0));
			}
			
		}else {
			if(quantity!=null){
				where = builder.and(where,builder.ge(productRoot.get("totalQuantity"), quantity));			
			}
		}
		if(name!=null){
			where = builder.and(where,builder.like(productRoot.get("name"), "%" + name + "%"));			
		}
		criteria.where(where);
	    List<Product> resultList = em.createQuery(criteria).getResultList();
	    for (Product product : resultList) {
			product.setQuantityBooked(bookRepository.getQuantityBookedAndEndDateIsNull(product.getId()));
		}
	    Collections.sort(resultList);
	    return resultList;
	}

	@Override
	public List<Product> getAllProductsByBox(Box box) {
		return (List<Product>) productRepository.findAllByBox(box);
	}
	
	
}
