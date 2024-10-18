package com.ebookeria.ecommerce.service.transaction;

import com.ebookeria.ecommerce.dto.transaction.TransactionDTO;
import com.ebookeria.ecommerce.dto.transaction.TransactionItemDTO;
import com.ebookeria.ecommerce.dto.transaction.TransactionResponse;
import com.ebookeria.ecommerce.entity.*;
import com.ebookeria.ecommerce.enums.TransactionStatus;
import com.ebookeria.ecommerce.exception.InvalidTransactionException;
import com.ebookeria.ecommerce.repository.CartRepository;
import com.ebookeria.ecommerce.repository.TransactionRepository;
import com.ebookeria.ecommerce.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(CartRepository cartRepository, UserService userService, TransactionRepository transactionRepository) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void createTransaction() {
        User user = userService.getCurrentUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        if (cart.getCartItems().isEmpty()) {
            throw new InvalidTransactionException("Cannot create transaction with an empty cart.");
        }


        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setUser(user);
        transaction.setTotalSales(0.0);
        transaction.setStatus(TransactionStatus.PENDING);

        List<TransactionItem> transactionItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setTransaction(transaction);
            transactionItem.setEbook(cartItem.getEbook());
            transactionItem.setUnitPrice(cartItem.getUnit_price() * cartItem.getQuantity());
            transactionItem.setQuantity(cartItem.getQuantity());
            transactionItems.add(transactionItem);
            transaction.setTotalSales(transaction.getTotalSales() + transactionItem.getUnitPrice());
        }
        transaction.setTransactionItems(transactionItems);

        transactionRepository.save(transaction);

        cart.getCartItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public TransactionResponse getUserTransactions(int pageNo,int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        User user = userService.getCurrentUser();
        Page<Transaction> transactions = transactionRepository.findTransactionByUserId(user.getId(), pageable);
        List<TransactionDTO> listOfTransactions = transactions.getContent().stream().map(this::mapTransactionToDTO).toList();
        return new TransactionResponse(listOfTransactions, transactions.getNumber(), transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
    }

    //TODO Implement this class
    @Override
    public TransactionResponse getAllTransactions() {
        return null;
    }

    private TransactionDTO mapTransactionToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getUser().getEmail(),
                transaction.getTransactionDate(),
                transaction.getTotalSales(),
                transaction.getStatus().name(),
                transaction.getTransactionItems().stream().map(this::mapTransactionItemToDTO).toList()
        );


    }

    private TransactionItemDTO mapTransactionItemToDTO(TransactionItem transactionItem) {
        return new TransactionItemDTO(
                transactionItem.getEbook().getTitle(),
                transactionItem.getUnitPrice(),
                transactionItem.getQuantity()
        );
    }
}
