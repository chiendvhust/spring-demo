package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractBaseEntity implements Serializable {
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedBy
	@Column(name = "create_by", updatable = false)
	protected String createBy;

	@LastModifiedBy
	@Column(name = "update_by")
	protected String updateBy;

	@CreationTimestamp
	@Column(name = "create_time", updatable = false)
	protected Timestamp createTime;

	@UpdateTimestamp
	@Column(name = "update_time")
	protected Timestamp updateTime;
}