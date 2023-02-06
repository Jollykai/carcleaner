package com.jollykai.carcleaner.controller;

import com.jollykai.carcleaner.entity.Order;
import com.jollykai.carcleaner.repository.OrderRepository;
import com.jollykai.carcleaner.repository.ProductRepository;
import com.jollykai.carcleaner.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@RestController
@RequestMapping()
public class OrderController {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @ApiOperation(value = "Show list of all existed orders", notes = "Available only for authenticated manager")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
    @GetMapping("/orders")
    public Iterable<Order> showAllOrders() {
        return orderRepository.findAll();
    }

    @ApiOperation(value = "Uses productId, time in millis (optional) and customer info to create order",
            notes = "Request without time creates an order to closest free time")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 204, message = "Product with this id not existed"),
            @ApiResponse(code = 406, message = "Time already pass"),
            @ApiResponse(code = 409, message = "Time already booked")
    })
    @PostMapping("/order")
    public ResponseEntity<?> addOrder(
            @RequestParam @ApiParam(
                    name = "productId",
                    value = "Product id",
                    example = "1",
                    required = true) Long productId,
            @RequestParam(required = false) @ApiParam(
                    name = "orderTime",
                    value = "Time in millis (optional)",
                    example = "1652173200000") Optional<Long> orderTime,
            @RequestParam @ApiParam(
                    name = "customerData",
                    value = "Customer info",
                    example = "John Smith",
                    required = true) String customerData) {

        if (!productRepository.existsById(productId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Date date = OrderService.getDate(orderTime);

        if (OrderService.isPastDate(date)) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        if (orderRepository.existsByDate(date)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Order createdOrder = orderRepository.save(new Order(productId, date, customerData));

        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Show order info by order Id")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 204, message = "Order with this id not existed")
    })
    @GetMapping("/order/{id}")
    public ResponseEntity<?> showOrder(
            @PathVariable @ApiParam(name = "id", value = "Order id", example = "1", required = true) Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "Show time left before order by Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "Order with this id not existed"),
            @ApiResponse(code = 406, message = "Time already pass"),
    })
    @GetMapping("/order/{id}/wait-time")
    public ResponseEntity<?> waitTime(
            @PathVariable @ApiParam(name = "id", value = "Order id", example = "1", required = true) Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Date orderDate = order.get().getDate();

        if (orderDate.before(new Date())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        String waitingTime = OrderService.getWaitingTime(orderDate);

        return new ResponseEntity<>(JSONObject.quote("Time until order: " + waitingTime), HttpStatus.OK);
    }
}