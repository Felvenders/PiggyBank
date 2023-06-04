package com.piggyBank.repos;

import com.piggyBank.dao.CategoryIdCountDAO;
import com.piggyBank.entity.ExpenseDetails;
import com.piggyBank.entity.IncomeDetails;
import com.piggyBank.entity.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface ExpenseDetailsRepository extends JpaRepository<ExpenseDetails, Integer> {

    @Query("select expdet from ExpenseDetails expdet where extract(year from expdet.date) = extract(year from current_date) and extract(month from expdet.date) = extract(month from current_date) and expdet.users.id = :id")
    List<ExpenseDetails> findExpenseInCurrentMonth(Integer id);

    @Query("select expdet from ExpenseDetails expdet where expdet.id = :id")
    ExpenseDetails findByIdExpenseDetail(int id);

    @Modifying
    @Query("update ExpenseDetails expdet set expdet.date = :date, expdet.category_id = :category_id, " +
            "expdet.amount = :amount, expdet.note = :note where expdet.id = :id")
    void updateExpenseDetails(@Param("id") Integer id, @Param("date") Date date, @Param("category_id") Integer category_id,
                              @Param("amount") Double amount, @Param("note") String note);

    @Modifying
    @Query("delete from ExpenseDetails expdet where expdet.id = :id")
    void deleteExpenseDetails(@Param("id") int id);

//    group by expdet.date order by expdet.date desc
    @Query("select new com.piggyBank.entity.ExpenseDetails(sum (expdet.amount)) from ExpenseDetails expdet where extract(year from expdet.date) = extract(year from current_date) and extract(month from expdet.date) = extract(month from current_date) and expdet.users.id = :id")
    ExpenseDetails outflowMonth(@Param("id") int id);

    @Query("select new com.piggyBank.dao.CategoryIdCountDAO(sum(expdet.amount), expdet.expenseCategories.name) from ExpenseDetails expdet where extract(year from expdet.date) = extract(year from current_date) and extract(month from expdet.date) = extract(month from current_date) and expdet.users.id = :id group by expdet.expenseCategories.name")
    List<CategoryIdCountDAO> countExpenseByCategoryId(@Param("id") int id);

    @Query("select expdet from ExpenseDetails expdet where expdet.date >= :min_date and expdet.date <= :max_date and expdet.users.id = :id")
    List<ExpenseDetails> findByDate(@Param("min_date") Date min_date, @Param("max_date") Date max_date, @Param("id") Integer id);

    @Query("select new com.piggyBank.dao.CategoryIdCountDAO(sum(expdet.amount), expdet.expenseCategories.name)" +
            " from ExpenseDetails expdet where expdet.date between :min_date and :max_date and expdet.users.id = :id group by expdet.expenseCategories.name")
    List<CategoryIdCountDAO> countExpenseByCategoryIdPrevious(@Param("min_date") Date min_date, @Param("max_date") Date max_date, @Param("id") int id);

}
