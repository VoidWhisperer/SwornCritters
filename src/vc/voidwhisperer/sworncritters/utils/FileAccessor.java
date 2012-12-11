/* Thanks to SagaciousZed for  helping me with some config issues..
 * https://github.com/SagaciousZed/SampleBukkitPlugin/blob/quoted-string/src/main/java/com/example/groupid/samplebukkitplugin/CustomConfigAccessor.java
 */
package vc.voidwhisperer.sworncritters.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
public class FileAccessor {
	private String filename;
	private File file;
	private FileConfiguration fileConfiguration;
	private JavaPlugin javaplug;
	public FileAccessor(JavaPlugin p,String FName)
	{
		this.javaplug = p;
		this.filename = FName;
	}
	public void reloadFile()
	{
		if(file == null)
		{
			File dataFolder = javaplug.getDataFolder();
			if(dataFolder == null) throw new IllegalStateException();
			file = new File(dataFolder,filename);
		}
		fileConfiguration = YamlConfiguration.loadConfiguration(file);
		
		//Looking for defaults in the .jar
		InputStream defConfigStream = javaplug.getResource(filename);
		if(defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			fileConfiguration.setDefaults(defConfig);
		}
		try
		{
		if(filename.equals("config.yml"))
		{
			File f = new File(javaplug.getDataFolder() + "/" + "config.yml");
			if(!f.exists())
			{
				javaplug.saveDefaultConfig();
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public FileConfiguration getFile()
	{
		if(fileConfiguration == null)
		{
			this.reloadFile();
		}
		return fileConfiguration;
	}
	
	public void saveFile()
	{
		if(fileConfiguration == null || file == null)
		{
			return;
		} else {
			try {
				getFile().save(file);
			}catch(IOException ex)
			{
				
				final Logger logger = javaplug.getLogger();
				if(logger == null) throw new IllegalArgumentException();
				logger.log(Level.SEVERE, "Could not save config to " + file, ex);
			}
		}
	}
}
