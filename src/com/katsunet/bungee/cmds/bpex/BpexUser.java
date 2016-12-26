package com.katsunet.bungee.cmds.bpex;

import java.util.ArrayList;

import com.katsunet.classes.SpkPlayer;
import com.katsunet.common.Global;
import com.katsunet.spkproxysuite.bungee.Main;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class BpexUser {
	
	// TODO: THIS should be done differently...
	public static void processUser(Main plugin, CommandSender sender, String playername, String operation, String group){
		if (operation.equals("groupAdd")){
			if(plugin.getPlayerList().get(playername) != null){ // Jugador online
				if (plugin.getPlayerList().get(playername).getBungeeGroups().contains(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" ya existe para el jugador "+playername+"."));
				} else if (!plugin.getBungeeGroups().containsKey(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no existe."));
				} else {
					plugin.getPlayerList().get(playername).getBungeeGroups().add(group);
					BpexUser.saveFile(plugin, Global.CONFNODE_PLAYERGROUPS+"."+playername, plugin.getPlayerList().get(playername).getBungeeGroups());
					sender.sendMessage(new TextComponent("Se ha añadido el jugador "+playername+" al grupo "+group+"."));
				}
			} else { // Jugador offline
				SpkPlayer spkp = new SpkPlayer();
				for (String bgroup : plugin.getBungeeCnf().getYaml().getStringList(Global.CONFNODE_PLAYERGROUPS+"."+playername) ){
					if(!spkp.getBungeeGroups().contains(bgroup)){
						spkp.addBungeeGroup(bgroup);
					}
				}
				if (spkp.getBungeeGroups().contains(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" ya existe para el jugador "+playername+"."));
				} else if (!plugin.getBungeeGroups().containsKey(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no existe."));
				} else {
					spkp.addBungeeGroup(group);
					BpexUser.saveFile(plugin, Global.CONFNODE_PLAYERGROUPS+"."+playername, spkp.getBungeeGroups());
					sender.sendMessage(new TextComponent("Se ha añadido el jugador "+playername+" al grupo "+group+"."));
				}
				spkp=null;
			}
		} else if (operation.equals("groupDel")){
			if(plugin.getPlayerList().get(playername) != null){ // Jugador online
				if(group.equals(Global.BUNGEE_DEFAULT_GROUP_NAME)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no puede ser eliminado del usuario."));
				} else if (!plugin.getPlayerList().get(playername).getBungeeGroups().contains(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no está asignado al jugador."));
				} else {
					plugin.getPlayerList().get(playername).getBungeeGroups().remove(group);
					BpexUser.saveFile(plugin, Global.CONFNODE_PLAYERGROUPS+"."+playername, plugin.getPlayerList().get(playername).getBungeeGroups());
					sender.sendMessage(new TextComponent("Se ha retirado el grupo "+group+" del jugador "+playername+"."));
				}
			} else { // Jugador offline
				SpkPlayer spkp = new SpkPlayer();
				for (String bgroup : plugin.getBungeeCnf().getYaml().getStringList(Global.CONFNODE_PLAYERGROUPS+"."+playername) ){
					if(!spkp.getBungeeGroups().contains(bgroup)){
						spkp.addBungeeGroup(bgroup);
					}
				}
				
				if(group.equals(Global.BUNGEE_DEFAULT_GROUP_NAME)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no puede ser eliminado del usuario."));
				} else if (!spkp.getBungeeGroups().contains(group)){
					sender.sendMessage(new TextComponent("El grupo "+group+" no está asignado al jugador."));
				} else {
					spkp.removeBungeeGroup(group);
					BpexUser.saveFile(plugin, Global.CONFNODE_PLAYERGROUPS+"."+playername, spkp.getBungeeGroups());
					sender.sendMessage(new TextComponent("Se ha retirado el grupo "+group+" del jugador "+playername+"."));
				}
				spkp=null;
			}
		} else {
			sender.sendMessage(new TextComponent("La operación "+operation+" no es válida."));
		}
	}
	
	private static void saveFile(Main plugin, String node, ArrayList<String> arrayList){
		plugin.getBungeeCnf().getYaml().set(node,arrayList);
		plugin.getBungeeCnf().saveYamlFile();
	}
}
