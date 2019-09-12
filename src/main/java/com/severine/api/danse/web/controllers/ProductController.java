package com.severine.api.danse.web.controllers;

import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.severine.api.danse.entities.Box;
import com.severine.api.danse.entities.Category;
import com.severine.api.danse.entities.Colour;
import com.severine.api.danse.entities.Kind;
import com.severine.api.danse.entities.Picture;
import com.severine.api.danse.entities.Product;
import com.severine.api.danse.entities.SizeQuantity;
import com.severine.api.danse.entities.SuperType;
import com.severine.api.danse.entities.Type;
import com.severine.api.danse.service.BoxService;
import com.severine.api.danse.service.CategoryService;
import com.severine.api.danse.service.ColourService;
import com.severine.api.danse.service.KindService;
import com.severine.api.danse.service.ProductsService;
import com.severine.api.danse.service.TypeService;
import com.severine.api.danse.web.helpers.Static;
import com.severine.api.danse.web.models.PostAddProduct;
import com.severine.api.danse.web.models.PostAddSizeQuantity;
import com.severine.api.danse.web.models.PostPicture;
import com.severine.api.danse.web.models.PostSearchProducts;
import com.severine.api.danse.web.models.Reponse;
import com.severine.api.shared.S3Constants;
import com.severine.api.shared.Utils;

import com.google.common.base.Strings;

import antlr.StringUtils;

@RestController
@CrossOrigin
public class ProductController {
	@Autowired
	private ProductsService productsMetier;
	@Autowired
	private KindService kindService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private BoxService boxService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ColourService colourService;
	
	
	
	public Product getProduct(Long idProduct) {
		Product product = null;
		try {
			product = productsMetier.getProduct(idProduct);
		} catch (Exception e1) {
			System.err.println(e1);
			return null;
		}
		return product;
	}
	
	@RequestMapping(value = "/products/search", method = RequestMethod.POST)
	public Reponse getProductByCriteria(@RequestBody PostSearchProducts post) {
		String name = post.getName();
		Integer quantity = post.getQuantity();
		List<Colour> colours = post.getColours();
		List<Category> categories = post.getCategories();
		List<Kind> kinds = post.getKinds();
		List<Box> boxes = post.getBoxes();
		List<Type> types = post.getTypes();
		List<SuperType> superTypes = post.getSuperTypes();
		Boolean available = post.getAvailable();
		
		try {
			return new Reponse(0, productsMetier.searchByCriteria(colours, categories, kinds, types, boxes, quantity, name, available, superTypes));
		} catch (Exception e) {
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public Reponse getAllProducts(HttpServletResponse response) {
		try {
			return new Reponse(0, productsMetier.getAllProducts());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/products", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse addProduct(@RequestBody PostAddProduct post) throws MalformedURLException, IOException {
		Long id = post.getId();
		String name = post.getName();
		String comment = post.getComment();
		String picture = post.getPicture();
		Integer totalQuantity = post.getTotalQuantity();
		Long idType = null;
		
		if(post.getType()!=null) {
			idType = post.getType().getId();
		}
		Long idKind = null;
		if(post.getKind()!=null) {
			idKind = post.getKind().getId();
		}
		Long idCategory = null;
		if(post.getCategory()!=null) {
			idCategory = post.getCategory().getId();
		}
		Long idColour = null;
		if(post.getColour()!=null) {
			idColour = post.getColour().getId();
		}
		Long idBox = null;
		if(post.getBox()!=null) {
			idBox = post.getBox().getId();
		}
		List<PostAddSizeQuantity> postSizeQuantities = post.getSizeQuantities();
		Set<SizeQuantity> sizeQuantities = new HashSet<SizeQuantity>();
		if (picture!=null && !picture.contains("https://") && !picture.equals("")) {
			try {
				File destination = new File("picture");
				String pictureUTF8 = URLEncoder.encode(picture, "UTF-8");
				String urlString = S3Constants.BUCKET_DOMAIN+S3Constants.BUCKET_TMP+"/"+pictureUTF8;
				FileUtils.copyURLToFile(new URL(urlString), destination);
				long result = System.currentTimeMillis();
				picture = Utils.savePicture(S3Constants.BUCKET_FINAL, S3Constants.BUCKET_DOMAIN, result+"_"+picture, destination);
			}catch (Exception e) {
				System.err.println(e);
				return new Reponse(1, "Un problème est survenu lors de la récupération de l'image");
			}
		}
		Type type = null;
		if (idType != null) {
			type = typeService.getType(idType);
		}
		Kind kind = null;
		if (idKind != null) {
			kind = kindService.getKind(idKind);
		}
		Category category = null;
		if (idCategory != null) {
			category = categoryService.getCategory(idCategory);
		}
		Colour colour = null;
		if (idColour != null) {
			colour = colourService.getColour(idColour);
		}
		Box box = null;
		if (idBox != null) {
			box = boxService.getBox(idBox);
		}
		Product product = new Product(name, picture, comment, totalQuantity, category, type, colour, box, kind);
		product.setId(id);
		
		List<PostPicture>picturesString = post.getPictures();
		Set<Picture>pictures = new HashSet<Picture>();
		if(null!=picturesString && picturesString.size()!=0) {
			String link;
			Picture pct;
			for (PostPicture picture2 : picturesString) {
				if(picture2.getId()==null) {
					File destination = new File("picture");
					String pictureUTF8 = URLEncoder.encode(picture2.getLink(), "UTF-8");
					String urlString = S3Constants.BUCKET_DOMAIN+S3Constants.BUCKET_TMP+"/"+pictureUTF8;
					FileUtils.copyURLToFile(new URL(urlString), destination);
					long result = System.currentTimeMillis();
					link = Utils.savePicture(S3Constants.BUCKET_FINAL, S3Constants.BUCKET_DOMAIN, result+"_"+picture2.getLink(), destination);
					pct = new Picture();
					pct.setLink(link);
					pictures.add(pct);						
				}		
			}
		}
		
		List<Long> sizes = new ArrayList<>();
		if (postSizeQuantities != null && postSizeQuantities.size()!=0) {
			productsMetier.deleteAllSizeQuantitiesByIdProduct(id);
			SizeQuantity sizeQuantity;
			for (PostAddSizeQuantity postAddSizeQuantity : postSizeQuantities) {
				if (sizes.contains(postAddSizeQuantity.getIdSize())) {
					return new Reponse(1, "Size duplicate in sizeQuantities");
				} else {
					sizes.add(postAddSizeQuantity.getIdSize());
					if (postAddSizeQuantity.getQuantity() != 0) {
						sizeQuantity = new SizeQuantity();
						sizeQuantity.setIdSize(postAddSizeQuantity.getIdSize());
						sizeQuantity.setQuantity(postAddSizeQuantity.getQuantity());
						sizeQuantities.add(sizeQuantity);
					}

				}
			}
		}
		try {
			return new Reponse(0, productsMetier.addOrUpdateProduct(product, sizeQuantities,pictures));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE, consumes = "application/json; charset=UTF-8")
	public Reponse deleteProduct(@PathVariable Long id) {
		Product product = getProduct(id);
		if (product == null) {
			return new Reponse(0, product);
		}
		try {
			productsMetier.deleteProduct(id);
		} catch (Exception e1) {
			System.err.println(e1);
			return new Reponse(3, Static.getErreursForException(e1));
		}
		return new Reponse(0, null);
	}

	@RequestMapping(value = "/products/quantitybooked", method = RequestMethod.GET)
	public Reponse getAllProductWithQuantityBooked() {
		try {
			return new Reponse(0, productsMetier.getAllProductsWithQuantityBooked());
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public Reponse getProductById(@PathVariable Long id) {
		try {
			return new Reponse(0, productsMetier.getProduct(id));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	@RequestMapping(value = "/products/boxes/{id}", method = RequestMethod.GET)
	public Reponse getProductByBox(@PathVariable Long id) {
		try {
			Box box = null;
			if(id!=0) {
				box = boxService.getBox(id);
			}
			return new Reponse(0, productsMetier.getAllProductsByBox(box));
		} catch (Exception e) {
			System.err.println(e);
			return new Reponse(1, Static.getErreursForException(e));
		}
	}

	

}
