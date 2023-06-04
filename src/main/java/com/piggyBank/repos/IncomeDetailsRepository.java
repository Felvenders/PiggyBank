package com.piggyBank.repos;

import com.piggyBank.dao.CategoryIdCountDAO;
import com.piggyBank.entity.IncomeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
public interface IncomeDetailsRepository extends JpaRepository<IncomeDetails, Integer> {

    @Query("select incdet from IncomeDetails incdet where extract(year from incdet.date) = extract(year from current_date) " +
            "and extract(month from incdet.date) = extract(month from current_date) " +
            "and incdet.users.id = :id order by incdet.date desc")
    List<IncomeDetails> findIncomeInCurrentMonth(@Param("id") Integer id);

    @Query("select incdet from IncomeDetails incdet where incdet.id = :id")
    IncomeDetails findByIdIncomeDetail(int id);

    @Modifying
    @Query("update IncomeDetails incdet set incdet.date = :date, incdet.category_id = :category_id, " +
            "incdet.amount = :amount, incdet.note = :note where incdet.id = :id")
    void updateIncomeDetails(@Param("id") Integer id, @Param("date") Date date,
                             @Param("category_id") Integer category_id, @Param("amount") Double amount, @Param("note") String note);

    @Modifying
    @Query("delete from IncomeDetails incdet where incdet.id = :id")
    void deleteIncomeDetails(@Param("id") int id);

    @Query("select new com.piggyBank.entity.IncomeDetails(sum (incdet.amount)) from IncomeDetails incdet where extract(year from incdet.date) = extract(year from current_date) and extract(month from incdet.date) = extract(month from current_date) and incdet.users.id = :id")
    IncomeDetails inflowMonth(@Param("id") int id);

//    @Query("select new com.piggyBank.entity.IncomeDetails(incdet.id, incdet.date, incdet.amount, incdet.user_id, incdet.operation_id, incdet.category_id, incdet.note) from IncomeDetails incdet where incdet.date >= :min_date and incdet.date <= :max_date and incdet.users.id = :id")
    @Query("select incdet from IncomeDetails incdet where incdet.date >= :min_date and incdet.date <= :max_date and incdet.users.id = :id")
    List<IncomeDetails> findByDate(@Param("min_date") Date min_date, @Param("max_date") Date max_date, @Param("id") Integer id);

    @Query("select new com.piggyBank.dao.CategoryIdCountDAO(sum(incdet.amount), incdet.incomeCategories.name) " +
            "from IncomeDetails incdet where extract(year from incdet.date) = extract(year from current_date) and extract(month from incdet.date) = extract(month from current_date) " +
            "and incdet.users.id = :id group by incdet.incomeCategories.name")
    List<CategoryIdCountDAO> countIncomeByCategoryId(@Param("id") int id);

    @Query("select new com.piggyBank.dao.CategoryIdCountDAO(sum(incdet.amount), incdet.incomeCategories.name)" +
            " from IncomeDetails incdet where incdet.date between :min_date and :max_date and incdet.users.id = :id group by incdet.incomeCategories.name")
    List<CategoryIdCountDAO> countIncomeByCategoryIdPrevious(@Param("min_date") Date min_date, @Param("max_date") Date max_date, @Param("id") int id);

}
