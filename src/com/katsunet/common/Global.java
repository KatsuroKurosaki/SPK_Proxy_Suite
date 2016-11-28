package com.katsunet.common;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {
	
	public static String MAIN_CONFIG_FILE = "config.yml";
	public static int MAIN_CONFIG_VERSION = 2;
	public static String DATABASE_CONFIG_FILE = "database.yml";
	public static int DATABASE_CONFIG_VERSION = 1;
	public static String BUNGEE_CONFIG_FILE = "config.yml";
	public static String GEOIP_DATABASE_FILE = "GeoIP.dat";
	
	public static String CONFNODE_VERSION = "configversion";
	
	public static String CONFNODE_MSGCOOLDOWN = "msgcooldown";
	public static String CONFNODE_NORCVLST = "msgdisabledplayerlist";
	public static String CONFNODE_CHATSPYLST = "chatspyenabledplayerlist";
	public static String CONFNODE_HELPOPCLDWN = "helpopcooldown";
	public static String CONFNODE_LOBBY_SERVER = "lobby.server";
	public static String CONFNODE_LOBBY_COMMAND = "lobby.command";
	public static String CONFNODE_DB_DRIVER = "db.driver";
	public static String CONFNODE_DB_CONN = "db.connection";
	public static String CONFNODE_DB_USER = "db.user";
	public static String CONFNODE_DB_PASS = "db.pass";
	public static String CONFNODE_ANN_DELAY = "announcements.delay";
	public static String CONFNODE_ANN_PREFIX = "announcements.prefix";
	public static String CONFNODE_ANN_MSGS = "announcements.messages";
	
	public static String PERM_CHATSPY = "privatemessage.chatspy";
	public static String PERM_MSGTOGGLE = "privatemessage.msgtoggle";
	public static String PERM_MSGTOGGLEIGN = "privatemessage.msgtoggleignore";
	public static String PERM_COOLDOWNIGN = "privatemessage.cooldownignore";
	public static String PERM_DEBUGGER = "spkproxysuite.debug";
	public static String PERM_HELPOP = "helpop.staff";
	public static String PERM_BGCVIEW = "bgc.view";
	public static String PERM_BGCFREE = "bgc.free";
	public static String PERM_ANNOUNCEMENTS = "announcements.admin";
	public static String PERM_STAFF_CMD = "staff.command";
	public static String PERM_STAFF_MEMBER = "staff.member";
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[_A-Z0-9-\\+]+(\\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\\.[A-Z0-9]+)*(\\.[A-Z]{2,})$",Pattern.CASE_INSENSITIVE);
	
	public static int getCurrentTimeSeconds(){
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static String generateRandomUuidString(){
		return UUID.randomUUID().toString();
	}
	
	public static String whirlpoolEncode(String input){
		Whirlpool w = new Whirlpool();
        byte[] digest = new byte[Whirlpool.DIGESTBYTES];
        w.NESSIEinit();
        w.NESSIEadd(input);
        w.NESSIEfinalize(digest);
        return Whirlpool.display(digest);
	}
	
	public static boolean emailValidate(String email){
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
	}
	
	public static boolean containsNoCase(List<String> list, String search){
		for (String string : list){
	        if (string.equalsIgnoreCase(search)){
	            return true;
	        }
		}
	    return false;
	}
	
	public static boolean isInt(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}