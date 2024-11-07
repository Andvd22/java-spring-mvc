package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;

@Controller

public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model) {
        List<Product> prs = this.productService.fetchProducts();
        model.addAttribute("products", prs);
        return "admin/product/show";
    }

    // create
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String handleCreateProduct(Model model,
            @ModelAttribute("newProduct") @Valid Product dovanan,
            BindingResult newProductbindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        List<FieldError> errors = newProductbindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductbindingResult.hasErrors()) {
            return "admin/product/create";
        }
        // neu hop le
        String product = this.uploadService.handelSaveUploadFile(file, "product");

        dovanan.setImage(product);
        // System.out.println("run here" + dovanan);
        this.productService.handelSaveProduct(dovanan);
        return "redirect:/admin/product";// redirect ve link url
    }

    // view
    @GetMapping("/admin/product/{id}")
    public String getProductDetailPage(
            Model model,
            @PathVariable long id) {
        Product product = this.productService.fetchProductById(id).get();
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    // update
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currProduct.get());
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product pr, BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handelSaveUploadFile(file, "product");
                currProduct.setImage(img);
            }
            currProduct.setDetailDesc(pr.getDetailDesc());
            currProduct.setFactory(pr.getFactory());
            currProduct.setName(pr.getName());
            currProduct.setShortDesc(pr.getShortDesc());
            currProduct.setQuantity(pr.getQuantity());
            currProduct.setPrice(pr.getPrice());
            currProduct.setTarget(pr.getTarget());
            this.productService.handelSaveProduct(currProduct);
        }

        return "redirect:/admin/product";
    }

    // delete
    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product pr) {
        this.productService.deleteProduct(pr.getId());
        return "redirect:/admin/product";
    }

}
