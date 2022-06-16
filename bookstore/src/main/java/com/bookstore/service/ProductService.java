package com.bookstore.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookstore.exception.CustomeException;
import com.bookstore.model.Product;
import com.bookstore.repository.ProductRepo;


@Service
public class ProductService {

	@Autowired
	private	ProductRepo productRepo;
	@Autowired
	private	SequenceGeneratorService sequenceGeneratorService;
	// View All Products
	public List<Product> getAllProducts() throws CustomeException {
		try {
			return  productRepo.findAll();
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  getAllProducts", e);
		}
	}

	//View Single Products
	public Product showProductById(int id) throws CustomeException
	{
		try {
			Product pr=productRepo.findById(id).get();
			if(pr!=null){
				return pr;
			}		
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  showProductById", e);
		}
	}

	//Add Products
	public String addProduct(@Valid Product product) throws CustomeException
	{
		try {
			//			Product pro= productRepo.findByIsbnNo(product.getIsbnNo());
			Product pro =	productRepo.findProductByIsbn(product.getIsbnNo());
//			List<Product> pro =	productRepo.findByProductName(product.getName());
//			if(pro.size()>0){
			if(pro!=null) {
				return null;
			}else {
				product.setId((int) sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
				productRepo.save(product);
				return "Successfully Added";		
			}
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  while Adding Products", e);
		}
	}
	//		Edit (Update)Products
	public String updateProduct(int id, Product product) throws CustomeException 
	{
		try {
			if(id>0 && product!=null) {
				Product cat = productRepo.findById(id).get();
				if (cat!=null) {	
					cat.setName(product.getName());
					cat.setPrice(product.getPrice());
					cat.setPages(product.getPages());
					cat.setIsbnNo(product.getIsbnNo());
					cat.setCategory(product.getCategory());
					cat.setAuthor(product.getAuthor());
					cat.setDescription(product.getDescription());
					cat.setLanguage(product.getLanguage());
					productRepo.save(cat);
					return product.getName()+ " Book  is Successfully Updated";
				}
				else {
					return null ;
				}
			}else {
				return "Id Shouldn't be zero or Product Shouldn't be empty";
			}
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService updateProduct", e);
		}

	}
	//		Delete Products
	public String deleteProduct(int id) throws CustomeException
	{	
		try {		
			if(productRepo.existsById(id))
			{
				productRepo.deleteById(id);
				return id+ " Succefully deleted";	
			}
			return null;
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  showProductByCategoty", e);
		}
	}

	// ShowByCategory(Aggregation)
	public List<Product> showProductByCategoty(String name) throws CustomeException {
		try {		
			return productRepo.findProductByCategory(name);
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  showProductByCategoty", e);
		}
	}

	// FilterProduct by Price max &  min(Aggregation)
	public List<Product> filterByPrice(int minprice, int maxprice) throws CustomeException {
		try {
			return	productRepo.filterByPrice(minprice,maxprice);
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  FilterProduct", e);
		}
	}

	public List<Product> searchByName(String name) throws CustomeException {
		try {	
			return productRepo.findByProductName(name);
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  searchByName", e);
		}
	}

	public List<Product> sortByPrice(int sortType) throws CustomeException {
		try {
			return productRepo.sortByPrice(sortType);
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService sortByPrice", e);
		}
	}

	public String deleteAllProduct() throws CustomeException {
		try {
			productRepo.deleteAll();
			return "All Products Deleted By Admin";
		}
		catch(Exception e){
			throw new CustomeException("Exception occurred  in ProductService  deleteAllProduct", e);
		}
	}


}
