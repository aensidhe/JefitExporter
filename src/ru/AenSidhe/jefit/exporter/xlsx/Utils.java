package ru.AenSidhe.jefit.exporter.xlsx;

import android.os.*;
import android.widget.*;
import java.io.*;

public final class Utils
{
	public static final File GetFileOnSdCard(String file, boolean createDir) throws IOException
	{
		return GetFileOnSdCard(_packageName, file, createDir);
	}

	public static final File GetFileOnSdCard(String dir, String file, boolean createDir) throws IOException
	{
		String state = Environment.getExternalStorageState();

		if (!Environment.MEDIA_MOUNTED.equals(state) && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
		{
			throw new IllegalStateException("This Application needs a writable external storage (sdcard).");
		}

		File folder = new File(String.format(
			"%s%s%s", 
			Environment.getExternalStorageDirectory(),
			File.separator,
			dir != null ? dir : _packageName
		));
		
		if(!folder.exists())
		{
			if (!createDir)
				throw new IOException(String.format("Folder %s doesn't exists", folder.getAbsolutePath()));

			if (!folder.mkdir())
				throw new IOException(String.format("Can't create folder %s", folder.getAbsolutePath()));
		}

		return new File(folder, file);
	}
	
	public static final void SetPackageName(String packageName)
	{
		_packageName = packageName;
	}
	
	private static String _packageName;
}
