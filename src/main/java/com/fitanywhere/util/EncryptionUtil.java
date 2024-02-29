package com.fitanywhere.util;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtil {
	
	
	/* 需要在pom.xml導入(尚未導入SPRING時)
	<!-- https://mvnrepository.com/artifact/org.mindrot/jbcrypt -->
	<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
	</dependency>
	 */
	
	
	 // 加密密碼
    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // 校驗密碼
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }


}
