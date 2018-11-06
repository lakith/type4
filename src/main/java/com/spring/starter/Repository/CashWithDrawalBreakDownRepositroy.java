package com.spring.starter.Repository;

import com.spring.starter.model.CashWithDrawalBreakDown;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashWithDrawalBreakDownRepositroy extends JpaRepository<CashWithDrawalBreakDown,Integer> {
}
