package com.vironit.onlinepharmacy.controller;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.service.authentication.AuthenticationService;
import com.vironit.onlinepharmacy.service.creditcard.CreditCardService;
import com.vironit.onlinepharmacy.service.order.OrderService;
import com.vironit.onlinepharmacy.service.procurement.ProcurementService;
import com.vironit.onlinepharmacy.service.product.ProductCategoryService;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.recipe.RecipeService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan(basePackages = "com.vironit.onlinepharmacy.controller")
@Configuration
@EnableWebMvc
public class ApplicationConfigurationTest implements WebMvcConfigurer {

    @Bean
    @Primary
    public AuthenticationService<UserDto, UserPublicVo, UserLoginDto> getAuthenticationService(){
        return Mockito.mock(AuthenticationService.class);
    }

    @Bean
    @Primary
    public CreditCardService getCreditCardService(){
        return Mockito.mock(CreditCardService.class);
    }

    @Bean
    @Primary
    public OrderService getOrderService(){
        return Mockito.mock(OrderService.class);
    }

    @Bean
    @Primary
    public ProcurementService getProcurementService(){
        return Mockito.mock(ProcurementService.class);
    }

    @Bean
    @Primary
    public ProductService getProductService(){
        return Mockito.mock(ProductService.class);
    }

    @Bean
    @Primary
    public ProductCategoryService getProductCategoryService(){
        return Mockito.mock(ProductCategoryService.class);
    }

    @Bean
    @Primary
    public RecipeService getRecipeService(){
        return Mockito.mock(RecipeService.class);
    }

    @Bean
    @Primary
    public StockService getStockService(){
        return Mockito.mock(StockService.class);
    }

    @Bean
    @Primary
    public UserService getUserService(){
        return Mockito.mock(UserService.class);
    }
}
