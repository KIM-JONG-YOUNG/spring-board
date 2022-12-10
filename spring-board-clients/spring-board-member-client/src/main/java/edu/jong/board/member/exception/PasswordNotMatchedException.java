package edu.jong.board.member.exception;

public class PasswordNotMatchedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordNotMatchedException(String message) {
		super(message);
	}

}
