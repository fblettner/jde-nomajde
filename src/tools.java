/*
 * Copyright (c) 2018 NOMANA-IT and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * @author fblettner
 */
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author fblettner
 */
public class tools {
   
    
    public static String encodePasswd(String str) {
        Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
        str = new String(encoder.encodeToString(str.getBytes()));
        return str;
    }

    public static String decodePasswd(String str) throws IOException {
        str = new String(Base64.getDecoder().decode(str));       
        return str;
    }
    
    public static void main(String[] args) throws IOException{
        String mode = args[0];
        String passwd = args[1];
        switch (mode) {
            case "encode":
                System.out.println(encodePasswd(passwd));       
                break;
            case "decode":
                System.out.println(decodePasswd(passwd));        
                break;
            default:
                break;
        }

    }
}
