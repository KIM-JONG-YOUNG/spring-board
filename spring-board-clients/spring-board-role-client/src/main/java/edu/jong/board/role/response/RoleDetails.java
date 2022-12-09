package edu.jong.board.role.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import edu.jong.board.common.type.Method;
import edu.jong.board.common.type.State;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private Method accessMethod;

	private String accessUrlPattern;

	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;

	private State state;

}
