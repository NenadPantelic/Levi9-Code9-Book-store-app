package com.levi9.code9.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.levi9.code9.userservice.config.FeignConfig;

@FeignClient(name = "shopping-service", configuration = { FeignConfig.class })

public interface ShoppingServiceClient {
	@DeleteMapping(value = "/api/v1/shopping-cart/", params = "userId")
	public void deleteShoppingCartByUserId(@RequestParam("userId") Long userId);
}
