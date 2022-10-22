package pes98.game;

import java.awt.Font;
import java.util.List;
import java.awt.Graphics;
import java.util.ArrayList;

import pes98.Main;

public class Console {
	private Font font;
	private List<Log> logs;
	private int sideGap = 12, sizeChar = 20, timeChar = 800;
	
	public Console() {
		logs = new ArrayList<Log>();
		font = new Font("Arial", 0, sizeChar);
	}
	
	public void update() {
		for(int i = 0; i < logs.size(); i++) {
			Log log = logs.get(i);
			if(log.updateLog()) logs.remove(i);
		}
	}
	
	public void render (Graphics g) {
		g.setFont(font);
		for(Log l : logs) {
			g.drawString(l.msg, sideGap, Main.HEIGHT - ((logs.size() - 1) - logs.indexOf(l)) * sizeChar - sideGap);
		}
	}
	
	public void addToLog(String msg) {
		logs.add(new Log(msg));
	}
	
	class Log {
		int time;
		String msg;
		Log(String msg){
			time = 0;
			this.msg = msg;
		}
		boolean updateLog() {
			time++;
			if(time % timeChar == 0) return true;
			return false;
		}
	}
}
