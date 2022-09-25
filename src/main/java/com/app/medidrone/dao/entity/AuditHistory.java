package com.app.medidrone.dao.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "audithistory")
@Getter @Setter @NoArgsConstructor
@ToString
public class AuditHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
	private String message;

    @Column(nullable = false)
	private Timestamp timestamp;

	public AuditHistory(String message, Timestamp timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

}
