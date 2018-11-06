package com.spring.starter.Repository;

import com.spring.starter.model.CreditCardPaymentBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CreditCardPaymentBreakDownRepository extends JpaRepository<CreditCardPaymentBreakDown,Integer> {

    @Query("SELECT b FROM CreditCardPaymentBreakDown b WHERE b.crediitCardPeyment.crediitCardPeymentId =:crediitCardPeymentId")
    public Optional<CreditCardPaymentBreakDown> findBreakDown(@Param("crediitCardPeymentId") int crediitCardPeymentId);

}
