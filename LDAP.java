import java.util.Hashtable;
import java.util.Properties;

import java.io.InputStream;
import java.io.FileNotFoundException;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;


// Compile ==> javac LDAP.java
// Run     ==> java LDAP

public class LDAP {
	
	String result = "";
	Hashtable env;
	DirContext ctx;
	Attributes answer;
	
	@SuppressWarnings("unchecked")
	public LDAP() throws Exception {
		String uri = "ldap://ldap.test.com:389/dc=test,dc=com";
		InputStream inputStream = null;
		
		// If you want to use config file rather than just hardcoded LDAP uri, please uncomment codes below

		/*try {
			Properties prop = new Properties();
			String propFileName = "config_ldap.properties"; //you need to create this file under your resources directory
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			uri = prop.getProperty("ldap_uri"); //get this element's value
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}*/
		
		env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, uri);
	}
	
	public String getAttr(String user, String findAtt) {
		try {
			ctx = new InitialDirContext(env);
	        answer = ctx.getAttributes("uid="+user+", ou=Users");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (answer != null) {
	        try {
	          for (NamingEnumeration ae = answer.getAll(); ae.hasMore();) {

	            Attribute attr = (Attribute) ae.next();
	            
	            if (attr.getID().equals((findAtt.toLowerCase().equals("uid") ? "uidNumber" : findAtt.toLowerCase().equals("gid") ? "gidNumber" : ""))) {
	            	for (NamingEnumeration e = attr.getAll(); e.hasMore(); result = String.valueOf(e.next()));
	            }
	          }
	        } catch (NamingException e) {
	          e.printStackTrace();
	        }
	    }
		return result;
	}

	public static void main (String[] args) {

		try {
			LDAP ldap = new LDAP();

			String user = "gama";

			String uid = ldap.getAttr(user,"uid");

			String gid = ldap.getAttr(user,"gid");

			System.out.println("User : " + user + " , uidNumber : " + uid + " , gidNumber : " + gid);

		} catch (Exception e)  {
			e.printStackTrace();
		}

	}
	
}