package com.chat.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class File {

	    @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String id;

	    @Column(nullable = false)
	    private String fileName;

	    @Column(nullable = false)
	    private String fileType;

	    @Column(nullable = false)
	    private String fileUrl;

	    @Column(nullable = false)
	    private Long fileSize;
	    
	    @ManyToOne
	    @JoinColumn(name = "message_id")
	    private Message message;

}
