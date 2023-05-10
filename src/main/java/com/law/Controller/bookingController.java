package com.law.Controller;


import com.law.Model.LawFirm;
import com.law.Repository.LawFirmRepository;
import com.law.Service.LawFirmService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
    public class bookingController {


    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private LawFirmRepository lawFirmRepository;

    @Autowired
    LawFirmService lawFirmService;

    @RequestMapping(value = {"/bookings"}, method = RequestMethod.GET)
        public String showBookingPage(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
            return "user/booking";
        }

    @GetMapping("/booking1")
    public String showBookingPage1(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
        return "user/booking1";
    }

    @GetMapping("/booking2")
    public String showBookingPage2(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
        return "user/booking2";
    }

    @GetMapping("/booking3")
    public String showBookingPage3(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
        return "user/booking3";
    }

    @GetMapping("/booking4")
    public String showBookingPage4(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
        return "user/booking4";
    }

    @GetMapping("/booking5")
    public String showBookingPage5(Model model) {
        LawFirm booking = new LawFirm();
        model.addAttribute("booking", booking);
        return "user/booking5";
    }

    @PostMapping("/saveBooking")
    public String submitBookingForm(@Valid LawFirm booking, BindingResult bindingResult, @RequestParam("pdfFile") MultipartFile pdfFile) throws IOException {

        if (bindingResult.hasErrors()) {
            return "user/dashboard";
        }

        if (!pdfFile.isEmpty()) {
            String contentType = pdfFile.getContentType();
            if (contentType.equals("application/pdf")) {
                if (pdfFile.getSize() <= 1_000_000) {
                    byte[] pdfBytes = pdfFile.getBytes();
                    booking.setPdf_name(pdfFile.getOriginalFilename());
                    booking.setPdf(pdfBytes);
                } else {
                    bindingResult.rejectValue("pdfFile", "error.pdfFile", "File size should be less than or equal to 1MB");
                    return "user/dashboard";
                }
            } else {
                bindingResult.rejectValue("pdfFile", "error.pdfFile", "Invalid file type");
                return "user/dashboard";
            }
        }

        // Save the booking
        lawFirmService.save(booking);


        return "user/booking-success";
    }


    @GetMapping("/viewClients")
    public String viewClients(Model model) {
        List<LawFirm> booking = lawFirmRepository.findAll();
        model.addAttribute("booking", booking);
        return "view-client";
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPDF(@PathVariable Long id) {
        Optional<LawFirm> optionalMeeting = lawFirmService.findClientById(id);
        if (optionalMeeting.isPresent()) {
            LawFirm booking = optionalMeeting.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", booking.getPdf_name());
            return new ResponseEntity<>(booking.getPdf(), headers, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/editBooking/{id}")
    public String editBooking(@PathVariable(value = "id") long id, Model model) {
        LawFirm booking = lawFirmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        model.addAttribute("booking", booking);
        return "admin/editBooking";
    }


    @PostMapping("/updateBooking/{id}")
    public String updateBooking(@PathVariable(value = "id") long id, @Valid LawFirm booking, BindingResult bindingResult, @RequestParam("pdfFile") MultipartFile pdfFile) throws IOException {
        if (bindingResult.hasErrors()) {
            booking.setId(id);
            return "admin/dashboard";
        } else {
            if (!pdfFile.isEmpty()) {
                String contentType = pdfFile.getContentType();
                if (contentType.equals("application/pdf")) {
                    if (pdfFile.getSize() <= 1_000_000) {
                        byte[] pdfBytes = pdfFile.getBytes();
                        booking.setPdf_name(pdfFile.getOriginalFilename());
                        booking.setPdf(pdfBytes);
                    } else {
                        bindingResult.rejectValue("pdfFile", "error.pdfFile", "File size should be less than or equal to 1MB");
                        booking.setId(id);
                        return "admin/dashboard";
                    }
                } else {
                    bindingResult.rejectValue("pdfFile", "error.pdfFile", "Invalid file type");
                    booking.setId(id);
                    return "admin/dashboard";
                }
            }

            lawFirmRepository.save(booking);
            return "redirect:/viewClients";
        }
    }


    @GetMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable(value = "id") long id) {
        LawFirm lawFirm = lawFirmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        lawFirmRepository.delete(lawFirm);
        return "redirect:/viewClients";
    }


    @GetMapping("/mail")
    public String showMail(Model model) {
        List<LawFirm> booking = lawFirmRepository.findAll();
        model.addAttribute("booking", booking);
        return "admin/mail";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String email, @RequestParam String message, Model model) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Meeting Request");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        model.addAttribute("successMessage", "Message sent successfully!");
        return "admin/dashboard";
    }

}



