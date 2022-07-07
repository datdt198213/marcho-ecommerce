package com.ncc.service.product;

import com.ncc.contants.Constant;
import com.ncc.mapstruct.dto.color.ProductColorDto;
import com.ncc.mapstruct.dto.color.ProductColorWithoutSize;
import com.ncc.mapstruct.dto.inventory.InventorySaveDto;
import com.ncc.mapstruct.dto.inventory.InventoryWithSizeDto;
import com.ncc.mapstruct.dto.product.ProductDetailDto;
import com.ncc.mapstruct.dto.product.ProductDto;
import com.ncc.mapstruct.dto.product.ProductViewDto;
import com.ncc.mapstruct.dto.inventory.InventoryDto;
import com.ncc.mapstruct.dto.size.SizeDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Color;
import com.ncc.model.Inventory;
import com.ncc.model.Product;
import com.ncc.model.Size;
import com.ncc.repository.FeedbackRepository;
import com.ncc.repository.ProductColorRepository;
import com.ncc.repository.ProductRepository;
import com.ncc.repository.InventoryRepository;
import com.ncc.service.category.CategoryService;
import com.ncc.service.file.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private MapstructMapper mapstructMapper;
    private FileStorageService fileStorageService;
    private FeedbackRepository feedbackRepository;
    private ProductColorRepository productColorRepository;
    private InventoryRepository inventoryRepository;
    private CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              MapstructMapper mapstructMapper,
                              FileStorageService fileStorageService,
                              FeedbackRepository feedbackRepository,
                              ProductColorRepository productColorRepository,
                              InventoryRepository inventoryRepository,
                              CategoryService categoryService) {
        this.productRepository = productRepository;
        this.mapstructMapper = mapstructMapper;
        this.fileStorageService = fileStorageService;
        this.feedbackRepository = feedbackRepository;
        this.productColorRepository = productColorRepository;
        this.inventoryRepository = inventoryRepository;
        this.categoryService = categoryService;
    }

    private List<ProductViewDto> calStarAndConvertUri(List<ProductViewDto> dtos) {
        for (ProductViewDto dto : dtos) {
            Optional<Double> starAvg;

            starAvg = feedbackRepository.calculateAverageStar(dto.getId());
            dto.setStar(starAvg.isPresent() ? starAvg.get().doubleValue() : Constant.FIVE_STAR);
            for (ProductColorWithoutSize productColorDto : dto.getProductColorDtos()) {
                List<String> strings = Arrays.stream(productColorDto.getUrlImages()).map(u -> fileNameToURI(u)).collect(Collectors.toList());
                productColorDto.setUrlImages(strings.toArray(new String[0]));
            }
        }

        return dtos;
    }

    @Override
    public ProductDetailDto getProductById(String prodId) {
        Optional<Product> productOptional = productRepository.getProducts(prodId);

        Product product = null;
        ProductDetailDto dto = null;
        Optional<Double> starAvg;

        if (productOptional.isPresent())
            product = productOptional.get();

        dto = mapstructMapper.toDetailDto(product);
        starAvg = feedbackRepository.calculateAverageStar(dto.getId());

        dto.setStar(starAvg.isPresent() ? starAvg.get().doubleValue() : Constant.FIVE_STAR);


        for (ProductColorDto productColorDto : dto.getProductColorDtos()) {
            List<String> strings = Arrays.stream(productColorDto.getUrlImages()).map(u -> fileNameToURI(u)).collect(Collectors.toList());
            productColorDto.setUrlImages(strings.toArray(new String[0]));

            Color color = mapstructMapper.toEntity(productColorDto.getColorDto());
            List<InventoryWithSizeDto> inventoryWithSizeDtos = inventoryRepository.getInventoryByProductAndColor(product, color)
                    .stream().map(i -> mapstructMapper.toInventoryWithSizeDto(i))
                    .collect(Collectors.toList());

            productColorDto.setInventoryDtos(inventoryWithSizeDtos);
        }


        return dto;
    }

    @Override
    public Page<ProductViewDto> getProducts(Pageable pageable) {
        Optional<Page<Product>> products = productRepository.getProductView(pageable);

        Page<ProductViewDto> dtos = null;

        if (products.isPresent()) {
            dtos = products.get().map(mapstructMapper::toDto);
            calStarAndConvertUri(dtos.getContent());
        }

        return dtos;
    }

    @Override
    public Page<ProductViewDto> getProductsFromCategory(String cateId, Pageable pageable) {
        Optional<Page<Product>> products = productRepository.getProductFromCategory(cateId, pageable);
        Page<ProductViewDto> dtos = null;

        if (products.isPresent())
            dtos = products.get().map(mapstructMapper::toDto);

        return dtos;
    }

    public Page<ProductViewDto> filter(String cateId, Integer colorId, Double startPrice,
                                       Double endPrice,
                                       String name,
                                       Pageable pageable) {
        Page<ProductViewDto> dtos = null;
        List<String> cateIds = null;

        Page<Product> products = null;
        if (cateId == null) {
            cateId = "";
        }

        cateIds = categoryService.getAllCategoryHaveProducts(cateId)
                .stream().map(c -> c.getId())
                .collect(Collectors.toList());

        if (endPrice == null)
            endPrice = productRepository.findMaxPriceOut().get();

        if (colorId == null) {
            products = productRepository.filter(cateIds, startPrice, endPrice, name, pageable)
                    .get();

            dtos = products.map(mapstructMapper::toDto);

        } else {
            products = productRepository.filter(cateIds, colorId, startPrice, endPrice, name, pageable)
                    .get();

            dtos = products.map(mapstructMapper::toDto);
        }


//        double starAvg = feedbackRepository.calculateAverageStar(dto.getId(), productColorDto.getColorDto().getId());
//        productColorDto.setStar(starAvg.isPresent() ? starAvg.get().doubleValue() : Constant.FIVE_STAR);

        calStarAndConvertUri(dtos.getContent());

        return dtos;
    }

    @Transactional
    @Override
    public void save(ProductDto productDto) {
        List<String> imageNames = Arrays.asList(productDto.getMultipartFiles())
                .stream()
                .map(file -> fileStorageService.storeFile(Constant.PRODUCT_FOLDER, file))
                .collect(Collectors.toList());

        Product product = mapstructMapper.toEntity(productDto);
        product.setStatus(true);
        product.setCreatedAt(new Date());
        productRepository.save(product);

        InventorySaveDto inventorySaveDtoDto = mapstructMapper.toInventoryDto(productDto);
        Inventory inventory = mapstructMapper.toEntity(inventorySaveDtoDto);
        inventory.setCreatedAt(new Date());
        inventoryRepository.save(inventory);

        productColorRepository.insert(product.getId(), productDto.getColorId(), ProductColorDto.toStringUrlImage(imageNames));
    }

    @Transactional
    @Override
    public void merge(ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(productDto.getId());

        if (productOptional.isPresent()) {
            Product product = mapstructMapper.toEntity(productDto);
            product.setModifiedAt(new Date());

            productRepository.save(product);
        }
    }

    @Transactional
    @Override
    public void delete(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent())
            productRepository.disableProduct(id);
    }

    /*
    todo: Trong DB lưu tên ảnh theo kiểu abc.jpg,def.img,xyz.jpg
    --> Khi get product cần:
        + chuyển tên thành uri (Eg:http://localhost:8080/api/client/products/view/abc.jpg)
        --> dùng fileNameToURI method
        + sau đó chuyển thành chuỗi và set vào thuộc tính image của product
        --> dùng listURIToString method

        --> imageToUriString method để xử lý 2 công việc trên
    */
    private String fileNameToURI(String fileName) {
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath() // localhost:8080
                .path("/api/products/view/") // localhost:8080/api/products/view/
                .path(fileName) // localhost:8080/api/client/products/view/
                .toUriString();

        return fileUri;
    }

    private String listURIToString(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : strings) {
            stringBuilder.append(s + ",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}
