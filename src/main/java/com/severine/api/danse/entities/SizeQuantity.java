package com.severine.api.danse.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name="size_quantity")
@IdClass(value = SizeQuantityPk.class)
public class SizeQuantity{
	@Id
    @Column(name = "id_size")
    private Long idSize;

    @Id
    @Column(name = "id_product")
    private Long idProduct;
	
	private Integer quantity;
	
	@JoinColumn(name = "id_product", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;
	
	public SizeQuantity() {
		
	}	

	

	public Long getIdSize() {
		return idSize;
	}



	public void setIdSize(Long idSize) {
		this.idSize = idSize;
	}



	public Long getIdProduct() {
		return idProduct;
	}



	public void setIdProduct(Long idProduct) {
		this.idProduct = idProduct;
	}



	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
	    return String.format("SizeQuantity[idSize='%s', quantity='%s']", idSize,quantity);
	}

	
}
