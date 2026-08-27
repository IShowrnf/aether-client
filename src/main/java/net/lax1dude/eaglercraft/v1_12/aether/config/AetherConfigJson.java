package net.lax1dude.eaglercraft.v1_12.aether.config;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal JSON-backed config for Eaglercraft 1.12 port.
 */
public final class AetherConfigJson {
    private static final File JSON_FILE = new File("aether-config.json");

    public static class PanelEntry { public String title; public int x,y,w,h; public PanelEntry() {} public PanelEntry(String t,int x,int y,int w,int h){this.title=t;this.x=x;this.y=y;this.w=w;this.h=h;} }
    public static class Config { public String theme = "AETHER"; public String guiKey = "RSHIFT"; public List<PanelEntry> panels = new ArrayList<>(); }

    public static synchronized void save(Config cfg) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(JSON_FILE))) {
            StringBuilder sb = new StringBuilder(); sb.append('{'); sb.append("\n  \"theme\": \"").append(escape(cfg.theme)).append('\"').append(','); sb.append("\n  \"guiKey\": \"").append(escape(cfg.guiKey)).append('\"').append(','); sb.append("\n  \"panels\": [");
            for (int i=0;i<cfg.panels.size();++i){ PanelEntry p=cfg.panels.get(i); sb.append("\n    {"); sb.append("\"title\": \"").append(escape(p.title)).append('\"').append(','); sb.append("\"x\": ").append(p.x).append(','); sb.append("\"y\": ").append(p.y).append(','); sb.append("\"w\": ").append(p.w).append(','); sb.append("\"h\": ").append(p.h); sb.append("}"); if(i<cfg.panels.size()-1) sb.append(','); }
            sb.append("\n  ]\n}"); w.write(sb.toString());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static synchronized Config load() {
        Config cfg = new Config(); if (!JSON_FILE.exists()) return cfg; StringBuilder sb = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new FileReader(JSON_FILE))) { String line; while ((line = r.readLine()) != null) sb.append(line).append('\n'); String s = sb.toString(); cfg.theme = extractString(s,"theme",cfg.theme); cfg.guiKey = extractString(s,"guiKey",cfg.guiKey);
            int idx = s.indexOf("\"panels\""); if (idx >= 0) { int start = s.indexOf('[', idx); int end = s.indexOf(']', start); if (start >= 0 && end >= 0) { String arr = s.substring(start+1,end); String[] items = arr.split("\},"); List<PanelEntry> panels = new ArrayList<>(); for (String item : items) { String it = item.replace('{',' ').replace('}',' ').trim(); if (it.isEmpty()) continue; String title = extractString(it,"title",""); int x = extractInt(it,"x",0); int y = extractInt(it,"y",0); int wv = extractInt(it,"w",220); int hv = extractInt(it,"h",120); panels.add(new PanelEntry(title,x,y,wv,hv)); } if (!panels.isEmpty()) cfg.panels = panels; } }
        } catch (IOException e) { e.printStackTrace(); }
        return cfg;
    }

    private static String extractString(String s, String key, String def) { int k = s.indexOf('"'+key+'"'); if (k < 0) return def; int colon = s.indexOf(':', k); if (colon < 0) return def; int firstQuote = s.indexOf('"', colon); if (firstQuote < 0) return def; int secondQuote = s.indexOf('"', firstQuote+1); if (secondQuote < 0) return def; return unescape(s.substring(firstQuote+1, secondQuote)); }
    private static int extractInt(String s, String key, int def) { int k = s.indexOf('"'+key+'"'); if (k < 0) return def; int colon = s.indexOf(':', k); if (colon < 0) return def; int i=colon+1; while (i<s.length() && (s.charAt(i)==' '||s.charAt(i)=='\n')) i++; int j=i; while (j<s.length() && (Character.isDigit(s.charAt(j))||s.charAt(j)=='-')) j++; try { return Integer.parseInt(s.substring(i,j)); } catch (Exception e) { return def; } }
    private static String escape(String s) { return s.replace("\\","\\\\").replace("\"","\\\""); }
    private static String unescape(String s) { return s.replace("\\\"","\"").replace("\\\\","\\"); }
}
