package com.healthylicous.util;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smackx.pubsub.ItemPublishEvent;
import org.jivesoftware.smackx.pubsub.PayloadItem;
import org.jivesoftware.smackx.pubsub.SimplePayload;

import com.healthylicous.connection.data.Vorschlag;


public class DataHandler {
	String id, user;
	
	/**
	 * 
	 * @return
	 */
	public String getid() {
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUser(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("user='[a-z]*@[a-z0-9-]*/Smack");
        Matcher ma = regex.matcher(items.getItems().toString());
        String user = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("user='");
            for (String r : result) {
            	user = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
		return user;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getid(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("id='[a-z0-9-]*");
        Matcher ma = regex.matcher(items.getItems().toString());
        String id = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("id='");
            for (String r : result) {
            	id = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		return id;
	}
	
	/**
	 * 
	 * @param lat
	 * @param lon
	 * @param elev
	 * @param user
	 * @return
	 */
	public PayloadItem<SimplePayload> setStrecke(String lat, String lon, String elev, String user) {
		SimplePayload payload = new SimplePayload("strecken","http://www.example.org/strecken", "<strecken xmlns:healthyns='http://www.example.org/strecken' id='' user='android@doro-f5sr/Smack'><strecke><wayPoints>"+point(lat,lon,elev)+"</wayPoints></strecke></strecken>");
		PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param lat
	 * @param lon
	 * @param elev
	 * @return
	 */
	public String point(String lat, String lon, String elev) {
		String p = "<point><latitude>"+lat+"</latitude><longitude>"+lon+"</longitude><elevation>"+elev+"</elevation></point>";
		return p;
	}
	
	/**
	 * Get list of each Waypoint of Strecke
	 * @param items
	 * @return
	 */
	public String[] getStrecke(String items) {
		Pattern lat, lon, evel;
		lat = Pattern.compile("[\\d]+.[\\d]+</latitude>");
	    lon = Pattern.compile("[\\d]+.[\\d]+</longitude>");       
        evel = Pattern.compile("[\\d]+.[\\d]+</elevation>");
        String[] regExp = {"latitude", "longitude", "elevation"};
        
        Hashtable<String, Pattern> table = new Hashtable<String, Pattern>();
		table.put("latitude", lat);
		table.put("longitude", lon);
		table.put("elevation", evel);
        
		String[] werteListe = new String[regExp.length];
		for (int i = 0; i <= regExp.length - 1; i++) {
			Matcher ma = table.get(regExp[i]).matcher(items);
			if (ma.find()) {
				String[] wert = ma.group().split("</" + regExp[i] + ">");
				for (String r : wert) {
					werteListe[i] = r;
				}
			}
		}

		return werteListe;
	}
	
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public PayloadItem<SimplePayload> setKalories(String input, String user) {
		SimplePayload payload = new SimplePayload("kcal","http://www.example.org/kalories", "<kalories xmlns='http://www.example.org/kalories' user='"+user+"'><base metric='kcal' >"+input+"</base></kalories>");
		PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getKalories(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("[\\d]{1,4}</base>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String kalories = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</base>");
            for (String r : result) {
            	kalories = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
        return kalories;
	}
	
	/**
	 * 
	 * @param alter
	 * @param gewicht
	 * @param groesse
	 * @param geschlecht
	 * @return
	 */
	public PayloadItem<SimplePayload> setProfile(String user, String alter, String gewicht, String groesse, String geschlecht) {
		this.user = user;
		SimplePayload payload = new SimplePayload("profil","http://www.example.org/profil", "<profil xmlns='http://www.example.org/profil' user='"+user+"'><alter>"+alter+"</alter><gewicht metric='g'>"+gewicht+"</gewicht><groesse metric='cm'>"+groesse+"</groesse><geschlecht>"+geschlecht+"</geschlecht></profil>");
		PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getProfileAlter(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("[\\d]{1,2}</alter>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String alter = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</alter>");
            for (String r : result) {
            	alter = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
        return alter;
	}
	
	public String getProfileGewicht(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("[\\d]{1,3}</gewicht>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String gewicht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</gewicht>");
            for (String r : result) {
            	gewicht = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
        return gewicht;
	}
	
	public String getProfileGroesse(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("[\\d]{1,3}</groesse>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String groesse = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</groesse>");
            for (String r : result) {
            	groesse = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
        return groesse;
	}
	
	public String getProfileGeschlecht(ItemPublishEvent<?> items) {
		Pattern regex = Pattern.compile("female|male</geschlecht>");
        Matcher ma = regex.matcher(items.getItems().toString());
        String geschlecht = null;
        
        if (ma.find()) {
            String[] result = ma.group().split("</geschlecht>");
            for (String r : result) {
            	geschlecht = r;
            }
        }
        else
        	System.out.println("Ung�ltig");
		
        return geschlecht;
	}
	
	public PayloadItem<SimplePayload> setResult() {
		String itemId = "2";
		SimplePayload payload = new SimplePayload("result","http://www.example.org/result", "<result xmlns:healthyns='http://www.example.org/result'>"+setVorschlag(itemId)+"</result>");
		PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @return
	 */
	public PayloadItem<SimplePayload> setResult(String itemId, String user) {
		this.id = itemId;
		this.user = user;
		SimplePayload payload = new SimplePayload("result","http://www.example.org/result", "<result xmlns:healthyns='http://www.example.org/result' itemid='"+itemId+"' user='"+user+"'>"+setVorschlag(id)+"</result>");
		PayloadItem<SimplePayload> payloaditem = new PayloadItem<SimplePayload>(null, payload);
		return payloaditem;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	private String setVorschlag(String id) {
		String s = "<vorschlag  id='"+id+"' tageszeit='"+Vorschlag.head(Vorschlag.Head.TAGESZEIT)+"'><name metric='g' gewicht='"+Vorschlag.head(Vorschlag.Head.GEWICHT)+"'>"+Vorschlag.head(Vorschlag.Head.NAME)+"</name><kalorien metric='kcal'>"+Vorschlag.head(Vorschlag.Head.KALORIEN)+"</kalorien><fluessigkeit metric='l'>"+Vorschlag.head(Vorschlag.Head.FLUESSIGKEIT)+"</fluessigkeit>"+naehrstoffe()+mselemente()+vitamine()+"</vorschlag>";
		return s;
	}
	
	private String vitamine() {
		String v = "<vitamine><vitamina metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_A)+"</vitamina><vitamind metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_D)+"</vitamind><vitamine metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_E)+"</vitamine><vitaminb1 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B1)+"</vitaminb1><vitaminb2 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B2)+"</vitaminb2><vitaminb6 metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B6)+"</vitaminb6><vitaminb12 metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_B12)+"</vitaminb12><vitaminc metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.VITAMIN_C)+"</vitaminc><niacin metric='mg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.NIACIN)+"</niacin><folsaeure metric='microg'>"+Vorschlag.vitamine(Vorschlag.Vitamine.FOLSAEURE)+"</folsaeure></vitamine>";
		return v;
	}

	private String mselemente() {
		String m = "<mselemente><magnesium metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.MAGNESIUM)+"</magnesium><calcium metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.CALCIUM)+"</calcium><eisen metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.EISEN)+"</eisen><jod metric='microg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.JOD)+"</jod><fluorid metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.FLUORID)+"</fluorid><zink metric='mg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.ZINK)+"</zink><selen metric='microg'>"+Vorschlag.mselemente(Vorschlag.Mselemente.SELEN)+"</selen></mselemente>";
		return m;
	}

	private String naehrstoffe() {
		String n = "<naehrstoffe><eiweiss metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.EIWEISS)+"</eiweiss><fette metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.FETTE)+"</fette><kohlenhydrate metric='g'>"+Vorschlag.neahrstoffe(Vorschlag.Naehrstoffe.KOHLEHYDRATE)+"</kohlenhydrate></naehrstoffe>";
		return n;
	}

	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getResultID(String items) {
		Pattern pat = Pattern.compile("id=\"[a-z0-9-]*");
		Matcher ma = pat.matcher(items);
		String result = null;
        
		while (ma.find()) {
            String[] ja = ma.group().split("id=\"");
            for (String r : ja) {
            	result = r;
            }
        }
        return result;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getResultTag(String items) {
		Pattern pat = Pattern.compile("tageszeit=\"[\\w]+");
		Matcher ma = pat.matcher(items);
		String result = null;
        
		if (ma.find()) {
            String[] ja = ma.group().split("tageszeit=\"");
            for (String r : ja) {
            	result = r;
            }
        }
        return result;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getResultGewicht(String items) {
		Pattern pat = Pattern.compile("gewicht=\"[\\d]+");
		Matcher ma = pat.matcher(items);
        String result = null;
        
        if (ma.find()) {
            String[] ja = ma.group().split("gewicht=\"");
            for (String r : ja) {
            	result = r;
            }
        }
        return result;
	}
	
	/**
	 * 
	 * @param items
	 * @return
	 */
	public String getResultName(String items) {
		Pattern pat = Pattern.compile("[\\w]+</name>");
		Matcher ma = pat.matcher(items);
        String result = null;
        
        if (ma.find()) {
            String[] ja = ma.group().split("</name>");
            for (String r : ja) {
            	result = r;
            }
        }
        return result;
	}
	
	public String getResultKalorien(String items) {
		Pattern pat = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</kalorien>");
		Matcher ma = pat.matcher(items);
        String result = null;
        
        if (ma.find()) {
            String[] ja = ma.group().split("</kalorien>");
            for (String r : ja) {
            	result = r;
            }
        }
        return result;
	}
	
	/**
	 * Get all Nutritions as List 
	 * @param items
	 * @return
	 */
	public String[] getResults(String items) {
		Pattern flussigkeit, vitamina, vitamind, vitamine, vitaminb1, vitaminb2, vitaminb6, vitaminb12, vitaminc, niacin, folsaure, magnesium, eisen, calcium, jod, fluorid, zink, selen, eiweiss, fette, kohlehydrate;

//		kalorien = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</kalorien>");
		flussigkeit = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</fluessigkeit>");

		vitamina = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitamina>");
		vitamind = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitamind>");
		vitamine = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitamine>");
		vitaminb1 = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitaminb1>");
		vitaminb2 = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitaminb2>");
		vitaminb6 = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitaminb6>");
		vitaminb12 = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitaminb12>");
		vitaminc = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</vitaminc>");
		niacin = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</niacin>");
		folsaure = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</folsaeure>");

		magnesium = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</magnesium>");
		eisen = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</eisen>");
		calcium = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</calcium>");
		jod = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</jod>");
		fluorid = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</fluorid>");
		zink = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</zink>");
		selen = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</selen>");

		eiweiss = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</eiweiss>");
		fette = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</fette>");
		kohlehydrate = Pattern.compile("[0-9]+[,]{0,1}[0-9]+</kohlenhydrate>");

		String[] regExp = { "fluessigkeit", "eiweiss", "fette",
				"kohlenhydrate", "magnesium", "eisen", "calcium", "jod",
				"fluorid", "zink", "selen", "vitamina", "vitamind", "vitamine",
				"vitaminb1", "vitaminb2", "vitaminb6", "vitaminb12",
				"vitaminc", "niacin", "folsaeure" };

		Hashtable<String, Pattern> table = new Hashtable<String, Pattern>();
//		table.put("kalorien", kalorien);
		table.put("fluessigkeit", flussigkeit);
		table.put("eiweiss", eiweiss);
		table.put("fette", fette);
		table.put("kohlenhydrate", kohlehydrate);
		table.put("magnesium", magnesium);
		table.put("eisen", eisen);
		table.put("calcium", calcium);
		table.put("jod", jod);
		table.put("fluorid", fluorid);
		table.put("zink", zink);
		table.put("selen", selen);
		table.put("vitamina", vitamina);
		table.put("vitamind", vitamind);
		table.put("vitamine", vitamine);
		table.put("vitaminb1", vitaminb1);
		table.put("vitaminb2", vitaminb2);
		table.put("vitaminb6", vitaminb6);
		table.put("vitaminb12", vitaminb12);
		table.put("vitaminc", vitaminc);
		table.put("niacin", niacin);
		table.put("folsaeure", folsaure);

		String[] werteListe = new String[regExp.length];
		
		for (int i = 0; i <= regExp.length - 1; i++) {
			Matcher ma = table.get(regExp[i]).matcher(items);
			if (ma.find()) {
				String[] wert = ma.group().split("</" + regExp[i] + ">");
				for (String r : wert) {
					werteListe[i] = r;
				}
			}
		}

		return werteListe;
	} 
	
}
