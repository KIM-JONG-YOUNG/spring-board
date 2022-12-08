package edu.jong.board.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import edu.jong.board.common.type.State;
import edu.jong.board.domain.converter.AbstractAttributeConverter;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	private LocalDateTime updatedDate;

	@Column(name = "state", nullable = false)
	@Convert(converter = StateAttributeConverter.class)
	private State state = State.ACTIVE;
	
	@Converter
	public static class StateAttributeConverter extends AbstractAttributeConverter<State, Integer> {

		public StateAttributeConverter() {
			super(State.class, false);
		}

	}
	
}
