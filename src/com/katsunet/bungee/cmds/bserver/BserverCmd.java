package com.katsunet.bungee.cmds.bserver;

import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BserverCmd extends Command {

	//private Main plugin;
	
	public BserverCmd(Main plugin){
		super("bserver","spkproxysuite.bserver");
		//this.plugin=plugin;
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		arg0.sendMessage(new TextComponent("Not yet implemented."));
		/*if(arg1.length==0){
			this.info(arg0,arg1);
		} else if(arg1[0].equals("list")){
			this.list(arg0, arg1);
		} else if(arg1[0].equals("add")){
			this.add(arg0, arg1);
		} else if(arg1[0].equals("edit")){
			this.edit(arg0, arg1);
		} else if(arg1[0].equals("del")){
			this.del(arg0, arg1);
		} else {
			this.info(arg0,arg1);
		}*/
	}
	
	/*private void info(CommandSender arg0, String[] arg1){
		arg0.sendMessage(new TextComponent("**********************"));
		arg0.sendMessage(new TextComponent(" -=: Announcements :=-"));
		arg0.sendMessage(new TextComponent("/announcements list :: Listado de announcements"));
		arg0.sendMessage(new TextComponent("/announcements add <msg> :: Añade un announcement"));
		arg0.sendMessage(new TextComponent("/announcements edit <num> <msg> :: Edita un announcement"));
		arg0.sendMessage(new TextComponent("/announcements del <num> :: Borra un announcement"));
		arg0.sendMessage(new TextComponent("**********************"));
	}
	
	private void list(CommandSender arg0, String[] arg1){
		List<String> annlst = this.plugin.getCf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS);
		for(int i=0; i<annlst.size();i++){
			arg0.sendMessage(new TextComponent(i+" : "+annlst.get(i)));
		}
		annlst=null;
	}
	
	private void add(CommandSender arg0, String[] arg1){
		if(arg1.length==1){
			arg0.sendMessage(new TextComponent("[§bAnnouncements§f] Te has olvidado de escribir el announcement."));
		} else {
			StringBuilder strb = new StringBuilder();
			for(int i=1;i<arg1.length;i++) {
				strb.append(arg1[i]+" ");
			}
			List<String> ann = this.plugin.getCf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS);
			ann.add(strb.toString().trim());
			this.plugin.getCf().getYaml().set(Global.CONFNODE_ANN_MSGS, ann);
			this.plugin.getCf().saveYamlFile();
			ann=null;
			arg0.sendMessage(new TextComponent("[§bAnnouncements§f] Announcement añadido correctamente."));
		}
	}
	
	private void edit(CommandSender arg0, String[] arg1){
		if(arg1.length==1){
			arg0.sendMessage(new TextComponent("[§bAnnouncements§f] No has especificado un numero a editar."));
		} else {
			if(Global.isInt(arg1[1])){
				if(arg1.length==2){
					arg0.sendMessage(new TextComponent("[§bAnnouncements§f] El announcement a editar no puede quedar en blanco."));
				} else {
					try{
						StringBuilder strb = new StringBuilder();
						for(int i=2;i<arg1.length;i++) {
							strb.append(arg1[i]+" ");
						}
						List<String> ann = this.plugin.getCf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS);
						ann.set(Integer.parseInt(arg1[1]), strb.toString().trim());
						this.plugin.getCf().getYaml().set(Global.CONFNODE_ANN_MSGS, ann);
						this.plugin.getCf().saveYamlFile();
						ann=null;
						arg0.sendMessage(new TextComponent("[§bAnnouncements§f] Announcement editado correctamente."));
					} catch (IndexOutOfBoundsException ioobe){
						arg0.sendMessage(new TextComponent("[§bAnnouncements§f] El numero de announcement a editar no existe."));
					}
				}
			} else {
				arg0.sendMessage(new TextComponent("[§bAnnouncements§f] No has especificado un numero a editar."));
			}
		}
	}
	
	private void del(CommandSender arg0, String[] arg1){
		if(Global.isInt(arg1[1])){
			try{
				List<String> ann = this.plugin.getCf().getYaml().getStringList(Global.CONFNODE_ANN_MSGS);
				ann.remove(Integer.parseInt(arg1[1]));
				this.plugin.getCf().getYaml().set(Global.CONFNODE_ANN_MSGS, ann);
				this.plugin.getCf().saveYamlFile();
				ann=null;
				arg0.sendMessage(new TextComponent("[§bAnnouncements§f] Announcement eliminado correctamente."));
			} catch (IndexOutOfBoundsException ioobe){
				arg0.sendMessage(new TextComponent("[§bAnnouncements§f] El numero de announcement a eliminar no existe."));
			}
		} else {
			arg0.sendMessage(new TextComponent("[§bAnnouncements§f] No has especificado un numero a eliminar."));
		}
	}*/
	
	//the info of the test server that will be added dynamically
	/*	private ServerInfo info;

		public void onEnable() 
		{
			init();

			getLogger().info("------------[DynamicProxy]------------");

			logServers();

			//adds the test server to the list of active servers.
			// No waiting is implemented in this example and it is removed right
			// away, but I have tested it and you can actually connect to the
			// server added here.
			addTestServer();

			logServers();

			//removes the test server from the list of active servers.
			// The method and steps are inspired by the plugin DynamicBungee:
			// http://www.spigotmc.org/resources/dynamicbungee.451/
			removeTestServer();
			
			logServers();

			getLogger().info("------------[DynamicProxy]------------");
		}
		

		
		private void init() 
		{
			info = getProxy().constructServerInfo(
					"testserver",
					new InetSocketAddress("localhost", 25587),
					"This is the motd for the test server",
					false);
		}
		

		/**
		 * Adds the test server to BungeeCord.
		 *
		private void addTestServer() {

			getLogger().info("Adding the test server...");
			
			getProxy().getServers().put(info.getName(), info);

		}


		/**
		 * Removes the test server from BungeeCord.
		 *
		private void removeTestServer() {
			
			getLogger().info("Removing the test server...");
			
			TextComponent reason = new TextComponent("You were kicked because "
					+ "the test server was removed.");
			
			for (ProxiedPlayer player : info.getPlayers()) {
	            player.disconnect(reason);
	        }
			
			getProxy().getServers().remove(info.getName());
		}

		/**
		 * Logs the current servers in BungeeCord to the console.
		 *
		private void logServers() 
		{
			
			getLogger().info("Currently connected servers:");
			
			Map<String, ServerInfo> servers = getProxy().getServers();
			if(servers == null) 
			{
				return;
			}

			for(Map.Entry<String, ServerInfo> ent : servers.entrySet()) 
			{
				logServer(ent);
			}
		}


		private void logServer(Entry<String, ServerInfo> ent) 
		{
			ServerInfo si = ent.getValue();
			getLogger().info("(" + ent.getKey() + ") name: " + si.getName() 
					+ ", address: " + si.getAddress()
					+ ", motd: " + si.getMotd() 
					+ ", players: " + stringFromPlayers(si.getPlayers())
					);
		}

		private String stringFromPlayers(Collection<ProxiedPlayer> players) 
		{
			String ret = "";

			if(players != null) for(ProxiedPlayer pp : players) 
			{
				ret += pp.getName() + ", ";
			}

			if(ret.length() > 0) 
			{
				ret.substring(0, ret.length() - 2);
			}

			return ret;
		}*/
}
