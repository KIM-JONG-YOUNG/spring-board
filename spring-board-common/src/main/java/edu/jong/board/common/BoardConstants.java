package edu.jong.board.common;

public final class BoardConstants {

	public static final String ROOT_PACKAGE = "edu.jong.board";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final class TableNames {
		public static final String TB_ROLE = "tb_role";
		public static final String TB_MEMBER = "tb_member";
		public static final String TB_MEMBER_ROLE = "tb_member_role";
	}

	public static final class CacheKeys {
		public static final String ROLE_KEY = "role::";
		public static final String MEMBER_KEY = "member::";
		public static final String MEMBER_ROLE_LIST_KEY = "memberRoleList::";
	}

}
