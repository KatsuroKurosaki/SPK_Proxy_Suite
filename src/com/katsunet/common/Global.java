package com.katsunet.common;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {
	
	// Files and versions
	public static String	BUNGEE_MAIN_PLUGIN_CONFIG_FILE	= "proxysuite.yml";
	public static int 		BUNGEE_MAIN_CONFIG_VERSION		= 2;
	public static String	DATABASE_CONFIG_FILE			= "database.yml";
	public static int 		DATABASE_CONFIG_VERSION			= 1;
	public static String	BUNGEE_MAIN_CONFIG_FILE			= "../../config.yml";
	public static String	GEOIP_DATABASE_FILE				= "GeoIP.dat";
	
	// Global nodes
	public static String	CONFNODE_VERSION = "configversion";
	
	// Nodes for proxysuite.yml
	public static String CONFNODE_MSGCOOLDOWN				= "msgcooldown";
	public static String CONFNODE_NORCVLST					= "msgdisabledplayerlist";
	public static String CONFNODE_CHATSPYLST				= "chatspyenabledplayerlist";
	public static String CONFNODE_HELPOPCLDWN				= "helpopcooldown";
	public static String CONFNODE_LOGIN_GRACE_TIME			= "logingracetime";
	public static String CONFNODE_LOGIN_ATTEMPS 			= "loginattempts";
	public static String CONFNODE_GTITLE_FADE 				= "gtitle.fadetime";
	public static String CONFNODE_GTITLE_STAY 				= "gtitle.staytime";
	public static String CONFNODE_AUTHLOBBY_SERVER			= "lobby.authserver";
	public static String CONFNODE_LOBBY_SERVER				= "lobby.server";
	public static String CONFNODE_LOBBY_COMMAND				= "lobby.command";
	public static String CONFNODE_LOBBY_COMMANDALIAS		= "lobby.alias";
	public static String CONFNODE_ANN_DELAY					= "announcements.delay";
	public static String CONFNODE_ANN_PREFIX				= "announcements.prefix";
	public static String CONFNODE_ANN_MSGS					= "announcements.messages";
	public static String CONFNODE_CMDHIDE_UNKNOWN			= "commandhider.unknowncmdmsg";
	public static String CONFNODE_CMDHIDE_CMDLIST			= "commandhider.hiddencommands";
	// Nodes for database.yml
	public static String CONFNODE_DB_DRIVER					= "db.driver";
	public static String CONFNODE_DB_CONN					= "db.connection";
	public static String CONFNODE_DB_USER					= "db.user";
	public static String CONFNODE_DB_PASS					= "db.pass";
	
	// Permission nodes
	public static String PERM_CHATSPY						= "spkproxysuite.chatspy";
	public static String PERM_MSGTOGGLE						= "spkproxysuite.msgtoggle";
	public static String PERM_MSGTOGGLEIGN					= "spkproxysuite.msgtoggleignore";
	public static String PERM_COOLDOWNIGN					= "spkproxysuite.msgcooldownignore";
	public static String PERM_DEBUGGER						= "spkproxysuite.debug";
	public static String PERM_RECEIVEHELPOP					= "spkproxysuite.helpandreplyop";
	public static String PERM_BGCVIEW						= "spkproxysuite.bgcview";
	public static String PERM_BGCFREE						= "spkproxysuite.bgcfree";
	public static String PERM_ANNOUNCEMENTS					= "spkproxysuite.announcements";
	public static String PERM_STAFF_CMD						= "spkproxysuite.staffcommand";
	public static String PERM_STAFF_MEMBER					= "spkproxysuite.staffmember";
	public static String PERM_GTITLE_COMMAND				= "spkproxysuite.gtitle";
	public static String PERM_PLAYERINFO_COMMAND			= "spkproxysuite.playerinfo";
	
	// Other globals
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX	= Pattern.compile("^[_A-Z0-9-\\+]+(\\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\\.[A-Z0-9]+)*(\\.[A-Z]{2,})$",Pattern.CASE_INSENSITIVE);
	public static final String ANON_PROXY_STR				= "Anonymous Proxy";
	
	public static int getCurrentTimeSeconds(){
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static String generateRandomUuidString(){
		return UUID.randomUUID().toString();
	}
	
	public static String fixedLengthString(String string, int length) {
	    return String.format("%1$"+length+ "s", string);
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
	
	public static String getMinecraftVersion(int version){
		switch(version){
			case 316: return "1.11.1-2";
			case 315: return "1.11";
			case 210: return "1.10.0-2";
			case 110: return "1.9.4";
			case 109: return "1.9.2";
			case 108: return "1.9.1";
			case 107: return "1.9";
			case 47:  return "1.8.0-9";
			case 5:   return "1.7.6-10";
			case 4:   return "1.7.2-5";
			case 78:  return "1.6.4";
			case 77:  return "1.6.3";
			case 74:  return "1.6.2";
			case 73:  return "1.6.1";
			case 61:  return "1.5.2";
			case 60:  return "1.5.1";
			case 51:  return "1.4.6-7";
			default:  return "N/A";
		}
	}
}