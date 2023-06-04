package com.piggyBank.controller;

import com.google.gson.Gson;
import com.piggyBank.dao.CategoryIdCountDAO;
import com.piggyBank.entity.*;
import com.piggyBank.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ExpenseCategoriesRepository expenseCategoriesRepository;

    @Autowired
    private IncomeCategoriesRepository incomeCategoriesRepository;

    @Autowired
    private ExpenseDetailsRepository expenseDetailsRepository;

    @Autowired
    private OperationsRepository operationsRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;


    @GetMapping("/expenseTransactions")
    public String getExpenseTransactions(Model model) {
        List<ExpenseCategories> expenseCategories = expenseCategoriesRepository.findAll();

        model.addAttribute("expenseCategories", expenseCategories);
        model.addAttribute("expenseDetails", new ExpenseDetails());
        model.addAttribute("operations", new Operations());

        return "expenseTransactions";
    }

    @PostMapping("/expenseTransactions")
    public String postExpenseTransactions(@ModelAttribute("expenseDetails") ExpenseDetails expenseDetails,
                                          @ModelAttribute("operations") Operations operations,
                                          @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                          @Valid Integer expense) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        expenseDetails.setDate(date);
        expenseDetails.setCategory_id(expense);
        expenseDetails.setUsers(users);
        expenseDetails.setOperations(operations);

        operations.setUsers(users);
        operations.setOperationType(operationTypeRepository.getOne(1));
        operations.setAmount(expenseDetails.getAmount());

        expenseDetailsRepository.save(expenseDetails);
        operationsRepository.save(operations);

        return "redirect:/expenseTransactions";
    }

    @GetMapping("/incomeTransactions")
    public String getIncomeTransactions(Model model) {
        List<IncomeCategories> incomeCategories = incomeCategoriesRepository.findAll();

        model.addAttribute("incomeCategories", incomeCategories);
        model.addAttribute("incomeDetails", new IncomeDetails());
        model.addAttribute("operations", new Operations());

        return "incomeTransactions";
    }

    @PostMapping("/incomeTransactions")
    public String postIncomeTransactions(@ModelAttribute("incomeDetails") IncomeDetails incomeDetails,
                                         @ModelAttribute("operations") Operations operations,
                                         @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                         @Valid Integer income) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        incomeDetails.setDate(date);
        incomeDetails.setCategory_id(income);
        incomeDetails.setUsers(users);
        incomeDetails.setOperations(operations);

        operations.setUsers(users);
        operations.setOperationType(operationTypeRepository.getOne(2));
        operations.setAmount(incomeDetails.getAmount());

        incomeDetailsRepository.save(incomeDetails);
        operationsRepository.save(operations);

        return "redirect:/incomeTransactions";
    }

    @GetMapping("transactionsMonth")
    public String monthTransactions(Model model) {
//        @RequestParam("min_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date min_date,
//        @RequestParam("max_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date max_date,

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        List<ExpenseDetails> expenseMonthOperations = expenseDetailsRepository.findExpenseInCurrentMonth(users.getId());
        List<IncomeDetails> incomeMonthOperations = incomeDetailsRepository.findIncomeInCurrentMonth(users.getId());
        IncomeDetails inflow = incomeDetailsRepository.inflowMonth(users.getId());
        ExpenseDetails outflow = expenseDetailsRepository.outflowMonth(users.getId());

        if (outflow.getAmount() == null) {
            outflow.setAmount(0.0);
        }
        if (inflow.getAmount() == null) {
            inflow.setAmount(0.0);
        }
        Double result = Math.abs(outflow.getAmount() - inflow.getAmount());

        model.addAttribute("countIncomeByCategoryId", incomeDetailsRepository.countIncomeByCategoryId(users.getId()));
        model.addAttribute("countExpenseByCategoryId", expenseDetailsRepository.countExpenseByCategoryId(users.getId()));

        model.addAttribute("incomeMonthOperations", incomeMonthOperations);
        model.addAttribute("expenseMonthOperations", expenseMonthOperations);
        model.addAttribute("inflow", inflow);
        model.addAttribute("outflow", outflow);
        model.addAttribute("result", result);

        return "/transactionsMonth";
    }

    @GetMapping("/{id}/editIncomeMonth")
    public String editIncomeMonth(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        List<IncomeCategories> incomeCategoriesList = incomeCategoriesRepository.findAll();
        IncomeDetails incomeMonth = incomeDetailsRepository.findByIdIncomeDetail(id);

        if (users.getId() == incomeMonth.getUser_id()) {
            model.addAttribute("incomeCategoriesList", incomeCategoriesList);
            model.addAttribute("incomeMonth", incomeMonth);

            return "editIncomeMonth";
        }
        else {
            model.addAttribute("message", "Не найдено");
            return "home";
        }
    }

    @PatchMapping("/{id}/editIncomeMonth")
    public String updateIncomeMonth(@ModelAttribute("incomeMonth") IncomeDetails incomeMonth,
                                    @PathVariable("id") int id) {

        incomeDetailsRepository.updateIncomeDetails(id, incomeMonth.getDate(), incomeMonth.getCategory_id(), incomeMonth.getAmount(), incomeMonth.getNote());

        return "redirect:/transactionsMonth";
    }

    @GetMapping("/{id}/editExpenseMonth")
    public String editExpenseMonth(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        List<ExpenseCategories> expenseCategoriesList = expenseCategoriesRepository.findAll();
        ExpenseDetails expenseMonth = expenseDetailsRepository.findByIdExpenseDetail(id);

        if (users.getId() == expenseMonth.getUser_id()) {
            model.addAttribute("expenseCategoriesList", expenseCategoriesList);
            model.addAttribute("expenseMonth", expenseMonth);

            return "editExpenseMonth";
        }
        else {
            model.addAttribute("message", "Не найдено");
            return "home";
        }
    }

    @PatchMapping("/{id}/editExpenseMonth")
    public String updateExpenseMonth(@ModelAttribute("expenseMonth") ExpenseDetails expenseMonth,
                                    @PathVariable("id") int id) {

        expenseDetailsRepository.updateExpenseDetails(id, expenseMonth.getDate(), expenseMonth.getCategory_id(), expenseMonth.getAmount(), expenseMonth.getNote());

        return "redirect:/transactionsMonth";
    }

    @DeleteMapping("/{id}/editExpenseMonth")
    public String deleteExpenseMonth(@PathVariable("id") int id) {

        expenseDetailsRepository.deleteExpenseDetails(id);

        return "redirect:/transactionsMonth";
    }

    @DeleteMapping("/{id}/editIncomeMonth")
    public String deleteIncomeMonth(@PathVariable("id") int id) {

        incomeDetailsRepository.deleteIncomeDetails(id);

        return "redirect:/transactionsMonth";
    }

    @GetMapping(value = "/previousTransactions", params = {"min_date", "max_date"})
    public String lastMonth(@RequestParam("min_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date min_date,
                            @RequestParam("max_date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date max_date,
                            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users users = usersRepository.findByEmail(auth.getName());

        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findByDate(min_date, max_date, users.getId());
        List<ExpenseDetails> expenseDetailsList = expenseDetailsRepository.findByDate(min_date, max_date, users.getId());

        double income_sum = 0;
        for (int i = 0; i < incomeDetailsList.size(); i++) {
            double temp = incomeDetailsList.get(i).getAmount();
            income_sum = income_sum + temp;
        }

        double expense_sum = 0;
        for (int i = 0; i < expenseDetailsList.size(); i++) {
            double temp = expenseDetailsList.get(i).getAmount();
            expense_sum = expense_sum + temp;
        }

        Double inflow = income_sum;
        Double outflow = expense_sum;

        Double result = Math.abs(outflow - inflow);

        model.addAttribute("countIncomeByCategoryIdPrevious", incomeDetailsRepository.countIncomeByCategoryIdPrevious(min_date, max_date, users.getId()));
        model.addAttribute("countExpenseByCategoryIdPrevious", expenseDetailsRepository.countExpenseByCategoryIdPrevious(min_date, max_date, users.getId()));

        if (!incomeDetailsList.isEmpty() || !expenseDetailsList.isEmpty()) {
            model.addAttribute("result", result);
            model.addAttribute("inflow", inflow);
            model.addAttribute("outflow", outflow);
            model.addAttribute("min_date", min_date);
            model.addAttribute("max_date", max_date);
            model.addAttribute("incomeDetailsList", incomeDetailsList);
            model.addAttribute("expenseDetailsList", expenseDetailsList);

            return "previousTransactions";
        }
        else {
            model.addAttribute("message", "Данных по месяцу не существует!");

            model.addAttribute("min_date", min_date);
            model.addAttribute("max_date", max_date);
            model.addAttribute("incomeDetailsList", incomeDetailsList);
            model.addAttribute("expenseDetailsList", expenseDetailsList);

            return "previousTransactions";
        }
    }

}