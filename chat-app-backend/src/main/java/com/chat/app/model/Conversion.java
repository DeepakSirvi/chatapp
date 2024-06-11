package com.chat.app.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conversion extends Audit {

	public Conversion(String conversionId) {
		this.id = conversionId;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private User sender;
	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@OneToMany
	@JoinColumn(name = "conversion_id")
	private List<Message> message;

}
