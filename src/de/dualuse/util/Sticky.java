package de.dualuse.util;
import java.io.*;
import java.util.*;

//XXX TODO make it work on Android!
@SuppressWarnings("unchecked")
public class Sticky<T> implements Serializable {
	static public interface Getter<Q> {
		public Q get();
	}

	static private class Passthrough<R> implements Getter<R> {
		Sticky<R> reference;
		
		public Passthrough(Sticky<R> reference) {
			this.reference = reference;
		}
		
		@Override
		public R get() {
			return reference.value;
		}
	}
	
	static private boolean resetAll = false; 
	public static void resetAll() {
		resetAll = true;
	}
	
	public static void clear() {
		resetAll();
	}
	
	private static final long serialVersionUID = 1L;

	static String APP = new LinkedList<Class<?>>(Arrays.asList(new SecurityManager() { protected Class<?>[] getClassContext() { return super.getClassContext(); } }.getClassContext())).getLast().getName();
	
	static LinkedHashMap<String, List<Sticky<?>>> restored = new LinkedHashMap<String, List<Sticky<?>>>();
	static final LinkedHashMap<String, List<Sticky<?>>> registered = new LinkedHashMap<String, List<Sticky<?>>>();
	
	static final String STORE_NAME = APP+".sticky.blob", LAUNCH_PREFIX  = System.currentTimeMillis()+"-";
	public static final File originalStore = new File(System.getProperty("java.io.tmpdir"),STORE_NAME);
	public static final File persistentStore = new File(System.getProperty("java.io.tmpdir"),LAUNCH_PREFIX+STORE_NAME); 
	static {
		if (originalStore.exists()) try {
			InputStream in = new FileInputStream(originalStore);
			OutputStream out = new FileOutputStream(persistentStore);
			byte[] buffer = new byte[64*1024];
			for (int bytesRead = in.read(buffer); bytesRead>=0; bytesRead = in.read(buffer))
				out.write(buffer, 0, bytesRead);
			out.close();
			in.close();
			originalStore.delete();
		} catch (Exception ex) {
			System.err.println("Failed to swap sticky storage: "+ex);
		}
	}
	
	static final Thread saver = new Thread("Sticky-Thread") {
		public void run() {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(originalStore));
				
				for (Collection<Sticky<?>> cs: registered.values())
					for (Sticky<?> s: cs)
						s.update();

				oos.writeObject(registered);
				oos.close();
			} catch (Exception ex) {
				System.err.println("Error: couldn't write "+STORE_NAME+" to "+originalStore+": "+ex);
			}
		}
	};
	
	static {
		if (persistentStore.exists())
			try {
				ObjectInputStream oos = new ObjectInputStream(new FileInputStream(persistentStore));
				
				restored = (LinkedHashMap<String, List<Sticky<?>>>) oos.readObject();
				oos.close();
			} catch (Exception ex) {
				System.err.println("Error: couldn't read "+STORE_NAME+" from "+persistentStore+": "+ex);
			}
			Runtime.getRuntime().addShutdownHook(saver); 
	}
	

	public Sticky(Getter<T> ret) { this(ret.get(), ret); }
	public Sticky(T defaultValue) { this(identifierForStackTrace(), defaultValue); }
	public Sticky(T defaultValue, Getter<T> ret) { this(defaultValue); this.ret = ret; }
	public Sticky(String identifier, Getter<T> ret) { this(identifier, ret.get(), ret);  }
	public Sticky(String identifier, T defaultValue, Getter<T> ret) { this(identifier, defaultValue); this.ret = ret; }	
	public Sticky(String identifier, T defaultValue) {
		List<Sticky<?>> dupes = registered.get(identifier);
		if (dupes == null)
			dupes = new LinkedList<Sticky<?>>();
		
		dupes.add(this);
		registered.put(identifier,dupes);

		try {
			value = restore(identifier, value = initial = defaultValue);
		} catch (Exception ex) { } 
	}

	T restore(String identifier, T defaultValue) throws Exception {
		Class<T> type = (Class<T>)defaultValue.getClass();
		value = defaultValue;
		
		if (resetAll)
			return defaultValue;

		List<Sticky<?>> sources = restored.get(identifier);
		if (sources!=null) 
			value = type.cast(sources.get(registered.get(identifier).size()-1).value);
		
		return value; 
	}

	private T value = null;
	private T initial = null;
	private transient Getter<T> ret = new Passthrough<T>(this);

	public Sticky<T> update() { value = ret.get(); return this; }
	public Sticky<T> reset() { value = initial; return this; }
	public Sticky<T> set(T value) { this.value = value; return this; }
	public T get() { return this.value; }
	
	public static<T extends Serializable> T property(Getter<T> v) { return new Sticky<T>(v).get(); }
	public static<T extends Serializable> T value(T v) { return new Sticky<T>(v).get(); }
	public static<T extends Serializable> T value(String identifier, T v) { return new Sticky<T>(identifier, v).get(); }
	public static<T extends Serializable> T property(String identifier, Getter<T> v) { return new Sticky<T>(identifier, v).get(); }
	
	static public String identifierForStackTrace() {
		String identifier = "";
		for (StackTraceElement ste: new Exception().getStackTrace())
			identifier += ste.getClassName()+".";
		
		return identifier;
	}

}
