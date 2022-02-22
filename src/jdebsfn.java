/*
 * Copyright (c) 2018 NOMANA-IT and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * @author fblettner
 */
import java.sql.*; 
import java.io.*; 
import java.util.*; 
import java.util.zip.*;
import oracle.jdbc.*;

public class jdebsfn {

 public static Blob  uncompress(Blob p_src,Connection conn) throws Exception {

    byte[] src = p_src.getBytes(1, (int) p_src.length());
    Inflater decompresser = new Inflater();
	ByteArrayOutputStream bos = new ByteArrayOutputStream(src.length);

	try {
	    decompresser.setInput(src);
	    final byte[] buf = new byte[1024];
	    while (!decompresser.finished()) {
		int count = decompresser.inflate(buf);
		 bos.write(buf, 0, count);
		}
	} finally {
		decompresser.end();
	}

    byte[] dst = bos.toByteArray();
    Blob p_dst = conn.createBlob();;
    p_dst.setBytes(1, dst, 0, dst.length);
	return p_dst;
    }


   public static void main(String[] args) throws SQLException,IOException {

	// Lecture du fichier de configuration
	Properties properties = new Properties();	
	FileInputStream configStream = new FileInputStream("jdebsfn.properties");   
	try {
      		properties.load(configStream);
		} finally {
      		configStream.close();
	}	

    // register oracle driver
    try {
        Class.forName("oracle.jdbc.OracleDriver");
    } catch ( Exception e ) {
        throw new SQLException(e);
    }

    // connect to oracle and login
    String url = properties.getProperty("URL");
    String user = properties.getProperty("USER");
    String password = properties.getProperty("PASSWORD");
//    String password = Tools.decodePasswd(properties.getProperty("PASSWORD"));
    Connection conn = DriverManager.getConnection(url,user,password);

    // create the SQL statement
    String mode = args[0];
    String column = "";
    String extension = "";
    switch (mode) {
        case "F98780R":
            column = "XMOMRBLOB";
            extension = ".zip";            
            break;
        default:
            break;
    }
    String sql = properties.getProperty(mode);
    sql = sql.replace("?","'" + args[1] + "'");
    PreparedStatement stmt = conn.prepareStatement(sql);
 
    // fetch and display the results
 	Blob outBlob=null;	
    ResultSet rs = stmt.executeQuery();	

    while( rs.next() ) {
    try {
    outBlob = ((OracleResultSet)rs).getBlob(column);
        } catch ( Exception e ) {
            throw new SQLException(e);
        }
    }

	// Creation dun fichier	
    try {
        String destination = properties.getProperty("OutputDirectory")+args[1]+extension;
        File file = new File(destination);
        FileOutputStream stream = new FileOutputStream(file);
	
        InputStream is = outBlob.getBinaryStream();
        final int bufSize = 1024;
        byte[] buffer = new byte[bufSize];
        int length;
                
        while ((length = is.read(buffer, 0, bufSize)) != -1) {
                    stream.write(buffer, 0, length);
                }

        stream.close();
        rs.close();
        stmt.close();
        conn.close();

      } catch (IOException e) {
            System.out.println("Caught I/O Exception: (Write BLOB value - Put Method).");
            e.printStackTrace();
            throw e;

        } catch (SQLException e) {
            System.out.println("Caught SQL Exception: (Write BLOB value to file - Get Method).");
            System.out.println("SQL:\n" + sql);
            e.printStackTrace();
            throw e;
        }

    }

}

