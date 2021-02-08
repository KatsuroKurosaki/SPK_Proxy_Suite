package com.katsunet.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Global {

	// Files and versions
	public static String PLUGIN_CONFIG_FILE				= "proxysuite.yml";
	public static String DATABASE_CONFIG_FILE			= "database.yml";
	public static String MOTD_CONFIG_FILE				= "motd.txt";
	public static String BUNGEECORD_CONFIG_FILE			= "../../config.yml";

	// Nodes for config.yml, Bungeecord's main config file
	public static String CONFNODE_GROUPS				= "permissions";
	public static String CONFNODE_PLAYERGROUPS			= "groups";

	// Nodes for proxysuite.yml
	public static String CONFNODE_MSGCOOLDOWN			= "msgcooldown";
	public static String CONFNODE_NORCVLST				= "msgdisabledplayerlist";
	public static String CONFNODE_CHATSPYLST			= "chatspyenabledplayerlist";
	public static String CONFNODE_HELPOPCLDWN			= "helpopcooldown";
	public static String CONFNODE_LOGIN_GRACE_TIME		= "logingracetime";
	public static String CONFNODE_LOGIN_ATTEMPS			= "loginattempts";
	public static String CONFNODE_GTITLE_FADE			= "gtitle.fadetime";
	public static String CONFNODE_GTITLE_STAY			= "gtitle.staytime";
	public static String CONFNODE_AUTHLOBBY_SERVER		= "lobby.authserver";
	public static String CONFNODE_LOBBY_SERVER			= "lobby.server";
	public static String CONFNODE_LOBBY_COMMAND			= "lobby.command";
	public static String CONFNODE_LOBBY_COMMANDALIAS	= "lobby.alias";
	public static String CONFNODE_ANN_DELAY				= "announcements.delay";
	public static String CONFNODE_ANN_PREFIX			= "announcements.prefix";
	public static String CONFNODE_ANN_MSGS				= "announcements.messages";
	public static String CONFNODE_CMDHIDE_UNKNOWN		= "commandhider.unknowncmdmsg";
	public static String CONFNODE_CMDHIDE_CMDLIST		= "commandhider.hiddencommands";

	// Nodes for database.yml
	public static String CONFNODE_DB_DRIVER				= "db.driver";
	public static String CONFNODE_DB_CONN				= "db.connection";
	public static String CONFNODE_DB_USER				= "db.user";
	public static String CONFNODE_DB_PASS				= "db.pass";

	// Permission nodes
	public static String PERM_DEBUGGER					= "spkproxysuite.debug";
	public static String PERM_CHATSPY					= "spkproxysuite.chatspy";
	public static String PERM_MSGTOGGLE					= "spkproxysuite.msgtoggle";
	public static String PERM_MSGTOGGLEIGN				= "spkproxysuite.msgtoggleignore";
	public static String PERM_COOLDOWNIGN				= "spkproxysuite.msgcooldownignore";
	public static String PERM_RECEIVEHELPOP				= "spkproxysuite.helpandreplyop";
	public static String PERM_BGCVIEW					= "spkproxysuite.bgcview";
	public static String PERM_BGCFREE					= "spkproxysuite.bgcfree";
	public static String PERM_ANNOUNCEMENTS				= "spkproxysuite.announcements";
	public static String PERM_STAFF_CMD					= "spkproxysuite.staffcommand";
	public static String PERM_STAFF_MEMBER				= "spkproxysuite.staffmember";
	public static String PERM_GTITLE_COMMAND			= "spkproxysuite.gtitle";
	public static String PERM_PLAYERINFO_COMMAND		= "spkproxysuite.playerinfo";
	public static String PERM_BPERMISSIONS_COMMAND		= "spkproxysuite.bpex";
	public static String PERM_MOTDPERMISSION_COMMAND	= "spkproxysuite.gmotd";

	// Other globals
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^[_A-Z0-9-\\+]+(\\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\\.[A-Z0-9]+)*(\\.[A-Z]{2,})$", Pattern.CASE_INSENSITIVE);
	public static final Pattern VALID_IPV4_REGEX =
			Pattern.compile("(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");
	public static final String BUNGEE_DEFAULT_GROUP_NAME = "default";

	// Validates email
	public static boolean emailValidate(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	// Extracts a valid IPv4 from the string
	public static String extractIpAddress(String text) {
		Matcher matcher = VALID_IPV4_REGEX.matcher(text);
		if (matcher.find()) {
			return matcher.group();
		}
		return "0.0.0.0";
	}

	// Returns current UNIX timestamp with seconds granularity
	public static int getCurrentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static String secondsToDhms(int seconds) {
		int sec = seconds % 60;
		int minutes = seconds % 3600 / 60;
		int hours = seconds % 86400 / 3600;
		int days = seconds / 86400;
		
		return String.format("%01d days %02d:%02d:%02d",days, hours, minutes, sec);
	}

	/*
	 * public static String generateRandomUuidString(){ return
	 * UUID.randomUUID().toString(); }
	 * 
	 * public static String fixedLengthString(String string, int length) { return
	 * String.format("%1$"+length+ "s", string); }
	 * 
	 * public static String whirlpoolEncode(String input){ Whirlpool w = new
	 * Whirlpool(); byte[] digest = new byte[Whirlpool.DIGESTBYTES]; w.NESSIEinit();
	 * w.NESSIEadd(input); w.NESSIEfinalize(digest); return
	 * Whirlpool.display(digest); }
	 */

	/*
	 * public static boolean containsNoCase(List<String> list, String search){ for
	 * (String string : list){ if (string.equalsIgnoreCase(search)){ return true; }
	 * } return false; }
	 */

	/*
	 * public static boolean isInt(String s) { try { Integer.parseInt(s); }
	 * catch(NumberFormatException e) { return false; } catch(NullPointerException
	 * e) { return false; } // only got here if we didn't return false return true;
	 * }
	 */

	public static String getMinecraftVersion(int version) {
		switch (version) {
			case 754: return "1.16.4";
			case 753: return "1.16.3";
			case 751: return "1.16.2";
			case 736: return "1.16.1";
			case 735: return "1.16";
			case 578: return "1.15.2";
			case 575: return "1.15.1";
			case 573: return "1.15";
			case 498: return "1.14.4";
			case 490: return "1.14.3";
			case 485: return "1.14.2";
			case 480: return "1.14.1";
			case 477: return "1.14";
			case 404: return "1.13.2";
			case 401: return "1.13.1";
			case 393: return "1.13";
			case 340: return "1.12.2";
			case 338: return "1.12.1";
			case 335: return "1.12";
			case 316: return "1.11.1-2";
			case 315: return "1.11";
			case 210: return "1.10.0-2";
			case 110: return "1.9.3-4";
			case 109: return "1.9.2";
			case 108: return "1.9.1";
			case 107: return "1.9";
			case 47: return "1.8.0-9";
			case 5: return "1.7.6-10";
			case 4: return "1.7.2-5";
			case 78: return "1.6.4";
			case 77: return "1.6.3";
			case 74: return "1.6.2";
			case 73: return "1.6.1";
			case 61: return "1.5.2";
			case 60: return "1.5.0-1";
			case 51: return "1.4.6-7";
			default: return "N/A";
		}
	}
}