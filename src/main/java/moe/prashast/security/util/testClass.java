package moe.prashast.security.util;

public class testClass {
public static void main(String[] args) throws Exception {
	String plainText = AESUtil.decrypt("RLDPZD8OF+d7pmpLV385wQ==:0gd7QvqeQ0Jtl/SGNXTTVQ==:sauoOT+NwYSTS9xxxpsB1lXuCEl8BYCxHaZcp60KjZU=");

	String encriptedText = AESUtil.encrypt("data");

   System.out.println("plainText---"+encriptedText);
}
}
