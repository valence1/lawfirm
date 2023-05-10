package com.law.Model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class LawFirm {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	private String lawyerName;

	@NotNull(message = "Date is required")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@NotNull(message = "End time is required")
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime time;
	private String description;

	private String pdf_name;

	private String email;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "pdf_data", length = 100000)
	private byte[] pdf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLawyerName() {
		return lawyerName;
	}

	public void setLawyerName(String lawyerName) {
		this.lawyerName = lawyerName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPdf_name() {
		return pdf_name;
	}

	public void setPdf_name(String pdf_name) {
		this.pdf_name = pdf_name;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}
}