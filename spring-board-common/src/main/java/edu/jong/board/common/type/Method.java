package edu.jong.board.common.type;

public enum Method implements CodeEnum<String> {

	ALL, GET, POST, PUT, DELETE;

	@Override
	public String getCode() {
		return name();
	}
	
}
