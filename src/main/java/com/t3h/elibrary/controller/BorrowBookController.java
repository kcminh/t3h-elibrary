package com.t3h.elibrary.controller;

import com.t3h.elibrary.entity.Book;
import com.t3h.elibrary.entity.BorrowBook;
import com.t3h.elibrary.entity.model.BorrowForm;
import com.t3h.elibrary.repository.BookRepository;
import com.t3h.elibrary.service.BorrowBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowBookController {
    @Autowired
    private BorrowBookService borrowBookService;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/list")
    public String listIssuedBook(Model model) {
        List<Book> books =  borrowBookService.listIssuedBook();
        model.addAttribute("books", books);
        return "borrow/list";
    }

    @GetMapping("/addBorrowForm/{id}")
    public String addBorrowBook(@PathVariable("id") int bookId,
                                @ModelAttribute("books") Book books,
                                @ModelAttribute("borrows") BorrowBook borrowBook,
                                ModelMap modelMap) {
        Book book = borrowBookService.getBorrowBookById(bookId);
        modelMap.addAttribute("book", book);
        return "borrow/form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
        public String addBorrow(@ModelAttribute("borrows") BorrowForm borrowForm, ModelMap modelMap, BorrowBook borrowBook) {
            borrowBook.setBook(bookRepository.findById(borrowForm.getBookId()).orElse(null));
            borrowBook.setStudentId(borrowForm.getStudentId());
            borrowBook.setExpiredDate(borrowForm.getExpiredDate());

            BorrowBook borrowBook1 = borrowBookService.addBorrow(borrowBook);

            if (borrowBook1 != null) {
                modelMap.addAttribute("borrows", borrowBook1);
                return "/borrowed/list-borrowed";
            } else {
                modelMap.addAttribute("errorMsg", "Add book unsuccessful!");
                return "borrowed/exception";
            }
        }
}
