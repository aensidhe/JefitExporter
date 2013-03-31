package ru.AenSidhe.jefit.exporter.xlsx;

import android.content.*;
import java.io.*;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.*;

public abstract class DataFormatter
{
	protected DataFormatter(Context context)
	{
		_context = context;
	}

	public String FormatSets(Iterator<Set> sets) throws IOException
	{
		Init();
		WriteData(sets);
		File file = Utils.GetFileOnSdCard(GetFileName(), true);
		FileOutputStream fileOut = new FileOutputStream(file);
		Save(fileOut);
		fileOut.close();
		return file.getAbsolutePath();
	}
	
	protected abstract String GetFileName();
	
	protected abstract void Init();
	
	protected abstract void Save(FileOutputStream stream) throws IOException;

	protected abstract void WriteExerciseHeader(String name);
	
	protected abstract void WriteLogEntry(LocalDate date, ExerciseData entry);
	
	protected abstract void WriteExerciseFooter(String name);
	
	private void WriteData(Iterator<Set> sets)
	{
		SortedMap<String, List<Set>> data = new TreeMap<String, List<Set>>();
		while (sets.hasNext())
		{
			Set set = sets.next();
			List<Set> list;
			if (!data.containsKey(set.getName()))
			{
				list = new ArrayList<Set>();
				data.put(set.getName(), list);
			}
			else
				list = data.get(set.getName());

			list.add(set);
		}

		Iterator<String> keyIterator = data.keySet().iterator();

		while (keyIterator.hasNext())
		{
			String key = keyIterator.next();

			WriteExerciseHeader(key);

			Iterator<Set> valueIterator = data.get(key).iterator();

			while (valueIterator.hasNext())
			{
				Set set = valueIterator.next();
				Iterator<ExerciseData> it = set.getExercises();

				while (it.hasNext())
					WriteLogEntry(set.getDate(), it.next());
			}
			
			WriteExerciseFooter(key);
		}
	}

	protected final DateTime _now = new DateTime();

	private final Context _context;
}
